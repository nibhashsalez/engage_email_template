package com.salezshark.emailtemplate.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.salezshark.emailtemplate.bean.SubscriberManagementBean;
import com.salezshark.emailtemplate.bean.TemplateDetailBean;
import com.salezshark.emailtemplate.bean.UserBean;
import com.salezshark.emailtemplate.bean.UtilityBean;
import com.salezshark.emailtemplate.bean.mapper.BeanMapper;
import com.salezshark.emailtemplate.constants.ServiceConstants;
import com.salezshark.emailtemplate.entity.EmailTemplateDetailsEntity;
import com.salezshark.emailtemplate.entity.FolderMasterEntity;
import com.salezshark.emailtemplate.entity.TemplateFavouriteEntity;
import com.salezshark.emailtemplate.entity.TemplateFolderEntity;
import com.salezshark.emailtemplate.entity.TemplateUserActivityEntity;
import com.salezshark.emailtemplate.exception.EmailTemplateException;
import com.salezshark.emailtemplate.repository.EmailTemplateRepository;
import com.salezshark.emailtemplate.repository.FavouriteTemplateRepository;
import com.salezshark.emailtemplate.repository.FolderMasterRepository;
import com.salezshark.emailtemplate.repository.TemplateFolderRepository;
import com.salezshark.emailtemplate.repository.TemplateUserActivityRepository;
import com.salezshark.emailtemplate.request.EmailTemplateRequest;
import com.salezshark.emailtemplate.response.GenericResponse;
import com.salezshark.emailtemplate.response.TemplateResponse;
import com.salezshark.emailtemplate.service.IEmailTemplateService;
import com.salezshark.emailtemplate.service.IEmailTemplateUtilityService;
import com.salezshark.emailtemplate.utility.CommonUtils;
import com.salezshark.emailtemplate.utility.RepositoryUtils;
import com.salezshark.emailtemplate.utility.SpecificationUtility;
import com.salezshark.emailtemplate.utility.UserMasterCacheUtility;

@Service("emailTemplateService")
public class EmailTemplateServiceImpl implements IEmailTemplateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailTemplateServiceImpl.class);
	private static final String classname = EmailTemplateServiceImpl.class.getName();

	@Autowired
	private EmailTemplateRepository emailTemplateRepository;

	@Autowired
	private SubscriberManagementBean subscriberManagementBean;

	@Autowired
	private TemplateUserActivityRepository templateUserActivityRepository;

	@Autowired
	private FavouriteTemplateRepository favouriteEmailTemplateRepository;

	@Autowired
	private FolderMasterRepository folderMasterRepository;

	@Autowired
	private TemplateFolderRepository templateFolderRepository;

	@Autowired
	private RepositoryUtils repositoryUtils;

	@Autowired
	private IEmailTemplateUtilityService emailTemplateUtilityService;

	@Autowired
	private UserMasterCacheUtility userMasterCacheUtility;

	/** Cache Manager */
	@Autowired
	private EhCacheCacheManager cacheManager;

	@Override
	@Transactional
	public GenericResponse saveTemplate(EmailTemplateRequest emailTemplateRequest) throws EmailTemplateException {
		LOGGER.info("Executing " + classname + ".saveTemplate");
		try {
			FolderMasterEntity folderEntity = null;
			if (null != emailTemplateRequest.getFolderId()) {
				folderEntity = folderMasterRepository
						.findByIdAndIsActiveTrueAndDsmId(emailTemplateRequest.getFolderId(), subscriberManagementBean
								.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
			}
			EmailTemplateDetailsEntity emailTemplateEntity = BeanMapper.mapTemplateRequestToEntity(emailTemplateRequest,
					subscriberManagementBean, folderEntity);
			if (null != emailTemplateEntity) {
				emailTemplateEntity = emailTemplateRepository.save(emailTemplateEntity);
				return new GenericResponse("email template added successfully", HttpStatus.OK.value());
			} else {
				return new GenericResponse("error in saving template", HttpStatus.OK.value());
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".EmailTemplateServiceImpl(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional
	public GenericResponse favouriteTemplate(EmailTemplateRequest emailTemplateRequest) throws EmailTemplateException {
		LOGGER.info("Executing " + classname + ".favouriteTemplate");
		try {
			// unfavourite template....
			if (emailTemplateRequest.getUnfavorite()) {
				List<Long> templateIds = new ArrayList<>();
				templateIds.add(emailTemplateRequest.getTemplateId());
				int unFavCount = favouriteEmailTemplateRepository.delelteFavoriteTemplate(templateIds,
						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId()
								.getDataSourceMappingId());
				if (unFavCount > 0) {
					// update user master cache utility
					userMasterCacheUtility.updateUserMasteCacheAfterFavourite(cacheManager, 
							subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(), 
							subscriberManagementBean.getLoginUserId(), templateIds.get(0), emailTemplateRequest.getUnfavorite());
					return new GenericResponse(
							"template id : " + emailTemplateRequest.getTemplateId() + " unfavourite successfully",
							HttpStatus.OK.value());
				} else {
					return new GenericResponse("error in unfavourite email template", HttpStatus.OK.value());
				}
			} else {
				List<TemplateFavouriteEntity> templateFavouriteEntities = favouriteEmailTemplateRepository
						.findAllByDsmIdAndTemplateDetailsEntityAndUserId(
								subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId()
										.getDataSourceMappingId(),
								new EmailTemplateDetailsEntity(emailTemplateRequest.getTemplateId()),
								subscriberManagementBean.getUserMasterEntity().getUserId());
				if (CollectionUtils.isEmpty(templateFavouriteEntities)) {
					TemplateFavouriteEntity templateFavouriteEntity = new TemplateFavouriteEntity(emailTemplateRequest,
							subscriberManagementBean);
					templateFavouriteEntity = favouriteEmailTemplateRepository.save(templateFavouriteEntity);
					if (null != templateFavouriteEntity && null != templateFavouriteEntity.getId()) {
						// update user master cache utility
						userMasterCacheUtility.updateUserMasteCacheAfterFavourite(cacheManager, 
								subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(), 
								subscriberManagementBean.getLoginUserId(), templateFavouriteEntity.getTemplateDetailsEntity().getTemplateId(), 
								emailTemplateRequest.getUnfavorite());
						return new GenericResponse("favourite email template added successfully",
								HttpStatus.OK.value());
					} else {
						return new GenericResponse("error in saving favourite email template", HttpStatus.OK.value());
					}
				} else {
					return new GenericResponse("user already made template as favourite", HttpStatus.OK.value());
				}
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".EmailTemplateServiceImpl(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public GenericResponse shareEmailTemplate(EmailTemplateRequest emailTemplateRequest) throws EmailTemplateException {
		LOGGER.info("Executing " + classname + ".shareEmailTemplate");
		try {
			// Calling utility method for splitting string array and converting into long
			emailTemplateRequest.setTemplateIds(CommonUtils.getListInLong(emailTemplateRequest.getTemplateIdList()));
			emailTemplateRequest.setSharedUserIds(CommonUtils.getListInLong(emailTemplateRequest.getUsersIdList()));
			
			List<TemplateUserActivityEntity> templateUserActivityEntities = new ArrayList<>();
			for (Long templateId : emailTemplateRequest.getTemplateIds()) {
				for (Long userId : emailTemplateRequest.getSharedUserIds()) {
//					EmailTemplateDetailsEntity emailTemplateDetailsEntity = new EmailTemplateDetailsEntity(templateId);
					EmailTemplateDetailsEntity emailTemplateDetailsEntity = emailTemplateRepository.findByTemplateIdAndIsActiveTrueAndDsmId(templateId, 
							subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
					// validation : public template cannot be shared
					if(null != emailTemplateDetailsEntity && !emailTemplateDetailsEntity.getTemplateType().equalsIgnoreCase(
							ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_PUBLIC)) {
						// validation : if template already shared, cannot be shared again
						List<TemplateUserActivityEntity> userActivityEntities = templateUserActivityRepository
								.findAllBySharedWithUserIdAndTemplateDetailsEntityAndDsmId(userId, emailTemplateDetailsEntity, 
										subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
						if (CollectionUtils.isEmpty(userActivityEntities)) {
							templateUserActivityEntities.add(new TemplateUserActivityEntity(
									ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_SHARE, userId, subscriberManagementBean, emailTemplateDetailsEntity));
						}
					}
				}
			}

			if (!CollectionUtils.isEmpty(templateUserActivityEntities)) {
				Iterable<TemplateUserActivityEntity> iterableUserActivityEntities = templateUserActivityRepository
						.saveAll(templateUserActivityEntities);
				if (iterableUserActivityEntities.spliterator().getExactSizeIfKnown() > 0) {
					return new GenericResponse("template shared successfully", HttpStatus.OK.value());
				} else {
					return new GenericResponse("error in sharing template", HttpStatus.OK.value());
				}
			} else {
				return new GenericResponse("template already shared with users", HttpStatus.OK.value());
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".EmailTemplateServiceImpl(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
	}
//	@Deprecated
//	private List<TemplateDetailBean> getAllTemplatesByFolder(EmailTemplateRequest emailTemplateRequest, List<Predicate> predicates,
//			HttpServletRequest request, int size, int page) throws EmailTemplateException {
//		
//		List<TemplateDetailBean> templateDetailBeans = new ArrayList<>();
//		try {
//			/*
//			 * List<TemplateFolderEntity> folderEntities =
//			 * templateFolderRepository.findAllByFolderMasterEntityAndDsmId( new
//			 * FolderMasterEntity(emailTemplateRequest.getFolderId()),
//			 * subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().
//			 * getDataSourceMappingId(), repositoryUtils.getPageable(page, size, null));
//			 */
//			Specification<TemplateFolderEntity> templateFolderSpecification = SpecificationUtility.folderTemplateSpecification(
//					emailTemplateRequest, predicates, subscriberManagementBean);
//			Page<TemplateFolderEntity> folderTemplatePages = templateFolderRepository.findAll(templateFolderSpecification, repositoryUtils.getPageable(page, size));
//			if(null != folderTemplatePages && folderTemplatePages.hasContent()) {
//				UserBean userBean = userMasterCacheUtility.getUserByIdFromCacheOrDB(cacheManager, 
//						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(),
//						subscriberManagementBean.getUserMasterEntity().getUserId());
//				
//				templateDetailBeans = BeanMapper.mapTemplateFolderEntityToBeanResponse(
//						folderTemplatePages.getContent(), emailTemplateUtilityService.getAllModuleMapByDsmId(request), 
//						userBean, subscriberManagementBean, cacheManager, userMasterCacheUtility);
//			}
//		} catch (Exception e) {
//			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".getAllTemplatesByFolder(); " + e);
//			e.printStackTrace();
//			return templateDetailBeans;
//		}
//		return templateDetailBeans;
//	}

	@Override
	public GenericResponse getAllEmailTemplates(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException {
		LOGGER.info("Executing " + classname + ".getAllEmailTemplates");
		GenericResponse genericResponse = new GenericResponse();
		TemplateResponse templateResponse = new TemplateResponse();
		try {
			List<Predicate> predicates = new ArrayList<>();
			Specification<TemplateUserActivityEntity> totalTemplateSpecification = null;
			Specification<TemplateUserActivityEntity>  userActivitySpecification = null;
			Specification<TemplateFavouriteEntity>  favTemplateSpecification = null;
			Specification<EmailTemplateDetailsEntity> createdByMeTemplateSpecification = null;
			Specification<EmailTemplateDetailsEntity> publicTemplateSpecification = null;
			
			int size = emailTemplateRequest.getSize() > 0 ? emailTemplateRequest.getSize() : ServiceConstants.RECORD_SIZE;
			int page = emailTemplateRequest.getPage() > 0 ? emailTemplateRequest.getPage() : 0;
			List<TemplateDetailBean> templateDetailBeans = new ArrayList<>();

			// when folder is selected.....
			if (null != emailTemplateRequest.getFolderId()) {
				genericResponse = getAllTemplatesByFolder(emailTemplateRequest, predicates, request, size, page);
			}
			// when no filter is applied is applied.....
			else if (StringUtils.isBlank(emailTemplateRequest.getFilter())) {
				userActivitySpecification = SpecificationUtility.getTemplateUserSpecification(emailTemplateRequest, predicates, subscriberManagementBean, Boolean.TRUE);
				genericResponse = getAllUserActivityBeans(request, size, page, templateDetailBeans, userActivitySpecification);
			} else if(StringUtils.isNoneBlank(emailTemplateRequest.getFilter())) {
				// when share filter is applied.....
				if(emailTemplateRequest.getFilter().equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_SHARE)) {
					userActivitySpecification = SpecificationUtility.getTemplateUserSpecification(emailTemplateRequest, predicates, 
							subscriberManagementBean, Boolean.TRUE);
					genericResponse = getAllUserActivityBeans(request, size, page, templateDetailBeans, userActivitySpecification);
				}
				// when favourite filter is applied.....
				else if (emailTemplateRequest.getFilter().equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_FAVOURITE)) {
					favTemplateSpecification = SpecificationUtility.getFavTemplateSpecification(emailTemplateRequest, predicates, subscriberManagementBean);
					genericResponse = getAllFavTemplates(request, size, page, templateDetailBeans, favTemplateSpecification);
				}
				// when created by me filter is applied.....
				else if (emailTemplateRequest.getFilter().equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_BY_ME)) {
					createdByMeTemplateSpecification = SpecificationUtility.getCreatedByMeSpecification(emailTemplateRequest, predicates, subscriberManagementBean);
					genericResponse = getAllCreatedbyMeTemplateBeans(request, size, page, templateDetailBeans, createdByMeTemplateSpecification);
				}
				// when public filter is applied.....
				else if(emailTemplateRequest.getFilter().equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PUBLIC)) { 
					publicTemplateSpecification = SpecificationUtility.getPublicTemplateSpecification(emailTemplateRequest, predicates, subscriberManagementBean);
					genericResponse = getAllPublicTemplateBeans(request, size, page, templateDetailBeans, publicTemplateSpecification);
				}
			}
			// total count
			totalTemplateSpecification = SpecificationUtility.getTemplateUserSpecification(
					emailTemplateRequest, new ArrayList<Predicate>(), subscriberManagementBean, Boolean.FALSE);
			Long totalTemplateCount = templateUserActivityRepository.count(totalTemplateSpecification);
			// share count
			emailTemplateRequest.setFilter(ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_SHARE);
			userActivitySpecification = SpecificationUtility.getTemplateUserSpecification(
					emailTemplateRequest, new ArrayList<Predicate>(), subscriberManagementBean, Boolean.TRUE);
			Long sharedCount = getUserActivityCount(userActivitySpecification);

			// favourite count
			favTemplateSpecification = SpecificationUtility.getFavTemplateSpecification(
					emailTemplateRequest, new ArrayList<Predicate>(), subscriberManagementBean);
			Long favCount = getFavTemplateCount(favTemplateSpecification);
			
			// created by me count
			createdByMeTemplateSpecification = SpecificationUtility.getCreatedByMeSpecification(
					emailTemplateRequest, new ArrayList<Predicate>(), subscriberManagementBean);
			Long createdByMeCount = getCreatedbyMeTemplateCount(createdByMeTemplateSpecification);
			
			// created by me count
			publicTemplateSpecification = SpecificationUtility.getPublicTemplateSpecification(
					emailTemplateRequest, new ArrayList<Predicate>(), subscriberManagementBean);
			Long publicCount = getPublicTemplateCount(publicTemplateSpecification);
			
			if (genericResponse!=null) {
//				genericResponse.setData(new TemplateResponse(genericResponse.getData(), totalTemplateCount, sharedCount,
//						favCount, createdByMeCount, publicCount));
				List<TemplateDetailBean> templateDetailBeanList = (List<TemplateDetailBean>) genericResponse.getData();
				 templateResponse = new TemplateResponse(templateDetailBeanList, totalTemplateCount, sharedCount,
						favCount, createdByMeCount, publicCount);
//				 templateResponse.setData(templateDetailBeanList);
				 templateResponse.setNext(genericResponse.isNext());
				templateResponse.setStatusCode(HttpStatus.OK.value());
				
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".getAllEmailTemplates(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
		return templateResponse;
	}

	@Override
	public GenericResponse createFolder(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException {
		GenericResponse genericResponse = null;
		try {
			genericResponse = new GenericResponse(
					folderMasterRepository.save(new FolderMasterEntity(emailTemplateRequest, subscriberManagementBean)),
					"template folder created successfully", ServiceConstants.RESPONSE_STATUS_SUCCESS,
					HttpStatus.OK.value());
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".createFolder(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
		return genericResponse;
	}

	@Override
	@Transactional
	public GenericResponse moveToFolder(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException {
		GenericResponse genericResponse = null;
		try {
			emailTemplateRequest.setTemplateIds(CommonUtils.getListInLong(emailTemplateRequest.getTemplateIdList()));
			// delete existing template ids from template folder
				templateFolderRepository.deleteTemplateIdsFromUserFolder(emailTemplateRequest.getTemplateIds(), emailTemplateRequest.getFolderId(), 
						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
				
			FolderMasterEntity folderEntity = folderMasterRepository.findByIdAndIsActiveTrueAndDsmId(emailTemplateRequest.getFolderId(),
					subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
			List<EmailTemplateDetailsEntity> templateDetailsEntities = emailTemplateRepository.findByTemplateIdInAndIsActiveTrueAndDsmId(
					emailTemplateRequest.getTemplateIds(), subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
			if (null != folderEntity && !CollectionUtils.isEmpty(templateDetailsEntities)) {
				List<TemplateFolderEntity> templateFolderEntities = new ArrayList<>();
				for (EmailTemplateDetailsEntity emailTemplateDetailsEntity : templateDetailsEntities) {
					templateFolderEntities.add(new TemplateFolderEntity(emailTemplateDetailsEntity, folderEntity, subscriberManagementBean));
				}
				Iterable<TemplateFolderEntity> iterable = templateFolderRepository.saveAll(templateFolderEntities);
				if (iterable.spliterator().getExactSizeIfKnown() > 0) {
					genericResponse = new GenericResponse(null, iterable.spliterator().getExactSizeIfKnown() + " templates moved to folder : "
									+ folderEntity.getFolderName() + " successfully", ServiceConstants.RESPONSE_STATUS_SUCCESS, HttpStatus.OK.value());
				}
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".moveToFolder(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
		return genericResponse;
	}

	@Override
	public GenericResponse getTemplateDetailById(Long templateId, HttpServletRequest request)
			throws EmailTemplateException {
		GenericResponse genericResponse = null;
		try {
			List<Long> templateIds = new ArrayList<>();
			TemplateDetailBean templateDetailBean = null;
			templateIds.add(templateId);
			List<EmailTemplateDetailsEntity> templateDetailsEntities = emailTemplateRepository.findByTemplateIdInAndIsActiveTrueAndDsmId(templateIds, 
					subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
			UserBean userBean = userMasterCacheUtility.getUserByIdFromCacheOrDB(cacheManager,
					subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(),
					subscriberManagementBean.getUserMasterEntity().getUserId());
			if (!CollectionUtils.isEmpty(templateDetailsEntities)) {
				templateDetailBean = BeanMapper.mapEmailTemplateDetailActivityToBeanResponse(
						templateDetailsEntities.get(0), emailTemplateUtilityService.getAllModuleMapByDsmId(request),
						userBean, subscriberManagementBean, cacheManager, userMasterCacheUtility);

//				List<TemplateUserActivityEntity> userActivityEntities = templateUserActivityRepository.getAllSahredUsers(
//						templateDetailBean.getTemplateId(), subscriberManagementBean.getLoginUserId(), 
//						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
				List<TemplateUserActivityEntity> userActivityEntities = templateUserActivityRepository.getAllSahredUsers(
						templateDetailBean.getTemplateId(), subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
				if (!CollectionUtils.isEmpty(userActivityEntities)) {
					Map<Long, String> sahredWithMap = new HashMap<Long, String>();
					for (TemplateUserActivityEntity templateUserActivityEntity : userActivityEntities) {
						UserBean sharedUserBean = userMasterCacheUtility.getUserByIdFromCacheOrDB(cacheManager,
										subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(),
										templateUserActivityEntity.getSharedWithUserId());
						sahredWithMap.put(templateUserActivityEntity.getSharedWithUserId(), sharedUserBean.getUserName());
					}
					if (null != sahredWithMap) {
						templateDetailBean.setSahredWithMap(sahredWithMap);
					}
				}
				List<TemplateFavouriteEntity> favouriteEntities = favouriteEmailTemplateRepository.findAllByDsmIdAndTemplateDetailsEntityAndUserId(
								subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(),
								new EmailTemplateDetailsEntity(templateDetailBean.getTemplateId()), subscriberManagementBean.getLoginUserId());

				if (!CollectionUtils.isEmpty(favouriteEntities)) {
					templateDetailBean.setIsFavourite(Boolean.TRUE);
				}
				/**
				 * List<TemplateFolderEntity> templateFolderEntity = templateFolderRepository.findByTemplateDetailsEntityAndCreatedByAndDsmId(
						templateDetailsEntities.get(0), subscriberManagementBean.getLoginUserId(),
						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
				if(!CollectionUtils.isEmpty(templateFolderEntity)) {
					Map<Long, String> folderMap = new HashMap<>();
					for (TemplateFolderEntity temFolderEntity : templateFolderEntity) {
						templateDetailBean.setFolderId(temFolderEntity.getFolderMasterEntity().getId());
						folderMap.put(temFolderEntity.getFolderMasterEntity().getId(), temFolderEntity.getFolderMasterEntity().getFolderName());
					}
					templateDetailBean.setFolder(!folderMap.isEmpty() ? folderMap : null);
				}
				 */
				List<TemplateFolderEntity> templateFolderEntities = templateFolderRepository.findByTemplateDetailsEntityAndCreatedByAndDsmId(
						templateDetailsEntities.get(0), subscriberManagementBean.getLoginUserId(),
						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
				if(!CollectionUtils.isEmpty(templateFolderEntities)) {
					Map<Long, String> folderMap = new HashMap<>();
					for (TemplateFolderEntity templateFolderEntity : templateFolderEntities) {
//						templateDetailBean.setFolderId(templateFolderEntity.getFolderMasterEntity().getId());
						folderMap.put(templateFolderEntity.getFolderMasterEntity().getId(), templateFolderEntity.getFolderMasterEntity().getFolderName());
					}
					templateDetailBean.setFolder(!folderMap.isEmpty() ? folderMap : null);
				}
			}
			if (null != templateDetailBean) {
				genericResponse = new GenericResponse(templateDetailBean, "success", ServiceConstants.RESPONSE_STATUS_SUCCESS, HttpStatus.OK.value());
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".getTemplateDetailById(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
		return genericResponse;
	}

	@Override
	@Transactional
	public GenericResponse deleteTemplate(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException {
		GenericResponse genericResponse = new GenericResponse();;
		try {
			int deltedTemplateCount = 0;
			emailTemplateRequest.setTemplateIds(CommonUtils.getListInLong(emailTemplateRequest.getTemplateIdList()));
			// delete template available in desired folder
			if(null != emailTemplateRequest.getFolderId()) {
				deltedTemplateCount = templateFolderRepository.deleteTemplateFromUserFolder(emailTemplateRequest.getTemplateIds(), emailTemplateRequest.getFolderId(),
						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
				if (deltedTemplateCount > 0) {
					genericResponse = new GenericResponse(null, deltedTemplateCount + " templates deleted from folder successfully ",
							ServiceConstants.RESPONSE_STATUS_SUCCESS, HttpStatus.OK.value());
				}
			}else {
				for (Long templateId : emailTemplateRequest.getTemplateIds()) {
					EmailTemplateDetailsEntity emailTemplateDetailsEntity = emailTemplateRepository.findByTemplateIdAndIsActiveTrueAndDsmId(templateId, 
							subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
					// validation template created by logged in user can only be deleted
					if(null != emailTemplateDetailsEntity && emailTemplateDetailsEntity.getCreatedBy().equals(subscriberManagementBean.getLoginUserId())) {
						// delete shared template detail
						templateUserActivityRepository.deleteUserAvtivityTemplate(emailTemplateRequest.getTemplateIds(),
								subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
						
						// delete template detail marked as favourite
						favouriteEmailTemplateRepository.delelteFavoriteTemplate(emailTemplateRequest.getTemplateIds(),
								subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
						
						// delete template available in any folder
						templateFolderRepository.deleteTemplateFromFolder(emailTemplateRequest.getTemplateIds(),
								subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
						
						// finally delete template detail
						deltedTemplateCount = emailTemplateRepository.deleteTemplatesById(emailTemplateRequest.getTemplateIds(),
								subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
						
						if (deltedTemplateCount > 0) {
							genericResponse = new GenericResponse(null, deltedTemplateCount + " templates deleted successfully ",
									ServiceConstants.RESPONSE_STATUS_SUCCESS, HttpStatus.OK.value());
						}
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".deleteTemplate(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
		return genericResponse;
	}

	/**
	 * getAllUserActivityTemplates
	 * @param request
	 * @param size
	 * @param page
	 * @param templateDetailBeans
	 * @param userActivitySpecification
	 * @return
	 * @throws EmailTemplateException
	 */
	private GenericResponse getAllUserActivityBeans(HttpServletRequest request, int size, int page,
			List<TemplateDetailBean> templateDetailBeans, Specification<TemplateUserActivityEntity> userActivitySpecification) throws EmailTemplateException {
		GenericResponse genericResponse=new GenericResponse();
		Page<TemplateUserActivityEntity> templateUserActivities = templateUserActivityRepository.findAll(userActivitySpecification, repositoryUtils.getPageable(page, size));
		if (null != templateUserActivities && templateUserActivities.hasContent()) {
			UserBean userBean = userMasterCacheUtility.getUserByIdFromCacheOrDB(cacheManager,
					subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(),
					subscriberManagementBean.getUserMasterEntity().getUserId());
	
			genericResponse = BeanMapper.mapTemplateUserActivityToBeanResponse(templateUserActivities.getContent(), templateUserActivities.hasNext(),
					emailTemplateUtilityService.getAllModuleMapByDsmId(request), userBean, subscriberManagementBean, cacheManager, userMasterCacheUtility);
		}
		return genericResponse;
	}
	/**
	 * getAllUserActivityTemplateCount
	 * @param request
	 * @param userActivitySpecification
	 * @return
	 * @throws EmailTemplateException
	 */
	private Long getUserActivityCount(Specification<TemplateUserActivityEntity> userActivitySpecification) throws EmailTemplateException {
		return templateUserActivityRepository.count(userActivitySpecification);
	}
	/**
	 * getAllFavTemplates
	 * @param request
	 * @param size
	 * @param page
	 * @param templateDetailBeans
	 * @param favTemplateSpecification
	 * @return
	 * @throws EmailTemplateException
	 */
	private GenericResponse getAllFavTemplates(HttpServletRequest request, int size, int page,
			List<TemplateDetailBean> templateDetailBeans, Specification<TemplateFavouriteEntity> favTemplateSpecification) throws EmailTemplateException {
		GenericResponse genericResponse=new GenericResponse();
		Page<TemplateFavouriteEntity> favTemplatePages = favouriteEmailTemplateRepository.findAll(favTemplateSpecification, repositoryUtils.getPageable(page, size));
		if (null != favTemplatePages && favTemplatePages.hasContent()) {
			UserBean userBean = userMasterCacheUtility.getUserByIdFromCacheOrDB(cacheManager,
					subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(),
					subscriberManagementBean.getUserMasterEntity().getUserId());

			genericResponse = BeanMapper.mapFavTemplateToBeanResponse(favTemplatePages.getContent(),favTemplatePages.hasNext(),
					emailTemplateUtilityService.getAllModuleMapByDsmId(request), userBean, subscriberManagementBean, cacheManager, userMasterCacheUtility);
		}
		return genericResponse;
	}
	/**
	 * getFavTemplateCount
	 * @param favTemplateSpecification
	 * @return
	 * @throws EmailTemplateException
	 */
	private Long getFavTemplateCount(Specification<TemplateFavouriteEntity> favTemplateSpecification) throws EmailTemplateException {
		return favouriteEmailTemplateRepository.count(favTemplateSpecification);
	}
	/**
	 * getAllCreatedbyMeTemplateBeans
	 * @param request
	 * @param size
	 * @param page
	 * @param templateDetailBeans
	 * @param createdByMeTemplateSpecification
	 * @return
	 * @throws EmailTemplateException
	 */
	private GenericResponse getAllCreatedbyMeTemplateBeans(HttpServletRequest request, int size, int page,
			List<TemplateDetailBean> templateDetailBeans, Specification<EmailTemplateDetailsEntity> createdByMeTemplateSpecification) throws EmailTemplateException {
		GenericResponse generResponse=new GenericResponse();
		Page<EmailTemplateDetailsEntity> createdyMeTemplatePages = emailTemplateRepository.findAll(createdByMeTemplateSpecification, repositoryUtils.getPageable(page, size));
		if (null != createdyMeTemplatePages && createdyMeTemplatePages.hasContent()) {
			UserBean userBean = userMasterCacheUtility.getUserByIdFromCacheOrDB(cacheManager,
					subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(),
					subscriberManagementBean.getUserMasterEntity().getUserId());

			generResponse = BeanMapper.mapTempDetailActivityToBeanResponse(createdyMeTemplatePages.getContent(),createdyMeTemplatePages.hasNext(),
					emailTemplateUtilityService.getAllModuleMapByDsmId(request), userBean, subscriberManagementBean, cacheManager, userMasterCacheUtility);
		}
		return generResponse;
	}
	/**
	 * getCreatedbyMeTemplateCount
	 * @param favTemplateSpecification
	 * @return
	 * @throws EmailTemplateException
	 */
	private Long getCreatedbyMeTemplateCount(Specification<EmailTemplateDetailsEntity> createdByMeTemplateSpecification) throws EmailTemplateException {
		return emailTemplateRepository.count(createdByMeTemplateSpecification);
	}
	/**
	 * getAllPublicTemplateBeans
	 * @param request
	 * @param size
	 * @param page
	 * @param templateDetailBeans
	 * @param publicTemplateSpecification
	 * @return
	 * @throws EmailTemplateException
	 */
	private GenericResponse getAllPublicTemplateBeans(HttpServletRequest request, int size, int page,
			List<TemplateDetailBean> templateDetailBeans, Specification<EmailTemplateDetailsEntity> publicTemplateSpecification) throws EmailTemplateException {
        GenericResponse genericResponse= new GenericResponse();
		Page<EmailTemplateDetailsEntity> publicTemplatePages = emailTemplateRepository.findAll(publicTemplateSpecification, repositoryUtils.getPageable(page, size));
		if(null != publicTemplatePages && publicTemplatePages.hasContent()) {
			UserBean userBean = userMasterCacheUtility.getUserByIdFromCacheOrDB(cacheManager, 
					subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(),
					subscriberManagementBean.getUserMasterEntity().getUserId());
			genericResponse = BeanMapper.mapTempDetailActivityToBeanResponse(publicTemplatePages.getContent(), publicTemplatePages.hasNext(),
					emailTemplateUtilityService.getAllModuleMapByDsmId(request), userBean, subscriberManagementBean, cacheManager, userMasterCacheUtility);
		}
		return genericResponse;
	}
	/**
	 * getPublicTemplateCount
	 * @param publicTemplateSpecification
	 * @return
	 * @throws EmailTemplateException
	 */
	private Long getPublicTemplateCount(Specification<EmailTemplateDetailsEntity> publicTemplateSpecification) throws EmailTemplateException {
		return emailTemplateRepository.count(publicTemplateSpecification);
	}
	/**
	 * 
	 * @param emailTemplateRequest
	 * @param predicates
	 * @param request
	 * @param size
	 * @param page
	 * @return
	 * @throws EmailTemplateException
	 */
	private GenericResponse getAllTemplatesByFolder(EmailTemplateRequest emailTemplateRequest, List<Predicate> predicates,
			HttpServletRequest request, int size, int page) throws EmailTemplateException {
		GenericResponse genericResponse = new GenericResponse();
		List<TemplateDetailBean> templateDetailBeans = new ArrayList<>();
		try {
			/*
			 * List<TemplateFolderEntity> folderEntities =
			 * templateFolderRepository.findAllByFolderMasterEntityAndDsmId( new
			 * FolderMasterEntity(emailTemplateRequest.getFolderId()),
			 * subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().
			 * getDataSourceMappingId(), repositoryUtils.getPageable(page, size, null));
			 */
			Specification<TemplateFolderEntity> templateFolderSpecification = SpecificationUtility.folderTemplateSpecification(
					emailTemplateRequest, predicates, subscriberManagementBean);
			Page<TemplateFolderEntity> folderTemplatePages = templateFolderRepository.findAll(templateFolderSpecification, repositoryUtils.getPageable(page, size));
			if(null != folderTemplatePages && folderTemplatePages.hasContent()) {
				UserBean userBean = userMasterCacheUtility.getUserByIdFromCacheOrDB(cacheManager, 
						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(),
						subscriberManagementBean.getUserMasterEntity().getUserId());
				
				genericResponse = BeanMapper.mapTemplateFolderEntityToBeanResponse(
						folderTemplatePages.getContent(),folderTemplatePages.hasNext(), emailTemplateUtilityService.getAllModuleMapByDsmId(request), 
						userBean, subscriberManagementBean, cacheManager, userMasterCacheUtility);
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".getAllTemplatesByFolder(); " + e);
			e.printStackTrace();
			return genericResponse;
		}
		return genericResponse;
	}

	@Override
	@Transactional
	public GenericResponse editTemplate(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException {
		GenericResponse response = null;
		try {
			EmailTemplateDetailsEntity templateDetailsEntity = emailTemplateRepository.findByTemplateIdAndIsActiveTrueAndDsmId(emailTemplateRequest.getTemplateId(),
							subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
			if (null != templateDetailsEntity) {
				templateDetailsEntity = BeanMapper.mapTemplateRequestToEntityForEdit(templateDetailsEntity, emailTemplateRequest, subscriberManagementBean);
				// update folder
				if(null != emailTemplateRequest.getFolderId()) {
					List<TemplateFolderEntity> tempFolderEntities = templateFolderRepository.findByTemplateDetailsEntityAndCreatedByAndDsmId(
							templateDetailsEntity, subscriberManagementBean.getLoginUserId(), 
							subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
					if(!CollectionUtils.isEmpty(tempFolderEntities)) {
						TemplateFolderEntity templateFolderEntity = tempFolderEntities.get(0);
						List<Long> templateIds = new ArrayList<>();
						templateIds.add(templateDetailsEntity.getTemplateId());
						templateFolderRepository.deleteTemplateFromUserFolder(templateIds, templateFolderEntity.getFolderMasterEntity().getId(), 
								subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
					}
					FolderMasterEntity folderMasterEntity = folderMasterRepository.findByIdAndIsActiveTrueAndDsmId(
							emailTemplateRequest.getFolderId(), subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
					if (null != folderMasterEntity) {
						List<TemplateFolderEntity> templateFolderEntities = new ArrayList<>();
						templateFolderEntities.add(new TemplateFolderEntity(templateDetailsEntity, folderMasterEntity, subscriberManagementBean));
						if (!CollectionUtils.isEmpty(templateFolderEntities)) {
							templateDetailsEntity.setTemplateFolderEntities(templateFolderEntities);
						}
					}
				}
				templateDetailsEntity = emailTemplateRepository.save(templateDetailsEntity);
				return new GenericResponse("email template added successfully", HttpStatus.OK.value());
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".editTemplate(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public GenericResponse editFolder(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException {
		GenericResponse genericResponse = null;
		try {
			FolderMasterEntity folderMasterEntity = folderMasterRepository.findByIdAndIsActiveTrueAndDsmId(
					emailTemplateRequest.getFolderId(),
					subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
			if (null != folderMasterEntity) {
				folderMasterEntity.setFolderName(emailTemplateRequest.getName());
				folderMasterEntity = folderMasterRepository.save(folderMasterEntity);
				genericResponse = new GenericResponse(
						new UtilityBean(folderMasterEntity.getId(), folderMasterEntity.getFolderName()), "success",
						ServiceConstants.RESPONSE_STATUS_SUCCESS, HttpStatus.OK.value());
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".editFolder(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
		return genericResponse;
	}

	@Override
	public GenericResponse deleteFolder(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException {
		GenericResponse genericResponse = null;
		try {
			FolderMasterEntity folderMasterEntity = folderMasterRepository.findByIdAndIsActiveTrueAndDsmId(
					emailTemplateRequest.getFolderId(),
					subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
			if (null != folderMasterEntity) {
				// delete template assigned available in folder (template_folder)
				List<TemplateFolderEntity> templateFolderEntities = templateFolderRepository.findAllByFolderMasterEntityAndDsmId(folderMasterEntity, 
						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
				if(!CollectionUtils.isEmpty(templateFolderEntities)) {
					templateFolderRepository.deleteAll(templateFolderEntities);
				}
				folderMasterRepository.delete(folderMasterEntity);
				genericResponse = new GenericResponse(null, folderMasterEntity.getFolderName() + " folder deleted successfully",
						ServiceConstants.RESPONSE_STATUS_SUCCESS, HttpStatus.OK.value());
			} else {
				genericResponse = new GenericResponse(null, "folder not found",
						ServiceConstants.RESPONSE_STATUS_SUCCESS, HttpStatus.OK.value());
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".deleteFolder(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
		return genericResponse;
	}
}
