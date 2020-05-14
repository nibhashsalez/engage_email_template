package com.salezshark.emailtemplate.bean.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.util.CollectionUtils;

import com.salezshark.emailtemplate.bean.SubscriberManagementBean;
import com.salezshark.emailtemplate.bean.TemplateDetailBean;
import com.salezshark.emailtemplate.bean.UserBean;
import com.salezshark.emailtemplate.constants.ServiceConstants;
import com.salezshark.emailtemplate.entity.EmailTemplateDetailsEntity;
import com.salezshark.emailtemplate.entity.FolderMasterEntity;
import com.salezshark.emailtemplate.entity.TemplateFavouriteEntity;
import com.salezshark.emailtemplate.entity.TemplateFolderEntity;
import com.salezshark.emailtemplate.entity.TemplateUserActivityEntity;
import com.salezshark.emailtemplate.request.EmailTemplateRequest;
import com.salezshark.emailtemplate.response.GenericResponse;
import com.salezshark.emailtemplate.utility.UserMasterCacheUtility;

/**
 * 
 * @author Amit Gera
 *
 */

public class BeanMapper {

	/**
	 * mapTemplateBeanToEntity
	 * @param emailTemplateRequest
	 * @param subscriberManagementBean
	 * @return
	 */
	public static EmailTemplateDetailsEntity mapTemplateRequestToEntity(EmailTemplateRequest emailTemplateRequest,
			SubscriberManagementBean subscriberManagementBean, FolderMasterEntity folderEntity) {

		List<TemplateUserActivityEntity> templateUserActivityEntities = new ArrayList<>();
		EmailTemplateDetailsEntity emailTemplateEntity = new EmailTemplateDetailsEntity();
		emailTemplateEntity.setTemplateName(emailTemplateRequest.getTemplateName());
		emailTemplateEntity.setTemplateSubject(emailTemplateRequest.getSubject());
		emailTemplateEntity.setTemplateModuleId(emailTemplateRequest.getModuleId());
		emailTemplateEntity.setTemplateData(null != emailTemplateRequest.getData() ? emailTemplateRequest.getData() : null);
		emailTemplateEntity.setTemplateAttachments(null != emailTemplateRequest.getAttachments() ? emailTemplateRequest.getAttachments() : null);
		emailTemplateEntity.setIsActive(true);
		if (null != subscriberManagementBean && null != subscriberManagementBean.getUserMasterEntity()) {
			emailTemplateEntity.setCreatedBy(subscriberManagementBean.getUserMasterEntity().getUserId());
			emailTemplateEntity.setUpdatedBy(subscriberManagementBean.getUserMasterEntity().getUserId());
			emailTemplateEntity.setDsmId(subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
		}
		emailTemplateEntity.setCreatedDate(LocalDateTime.now());
		emailTemplateEntity.setUpdatedDate(LocalDateTime.now());

		if (StringUtils.isNoneBlank(emailTemplateRequest.getFilter()) 
				&& emailTemplateRequest.getFilter().equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PUBLIC)) {
			emailTemplateEntity.setTemplateType(ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_PUBLIC);
			templateUserActivityEntities.add(new TemplateUserActivityEntity(
					ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PUBLIC, subscriberManagementBean, emailTemplateEntity));
		} else {
			emailTemplateEntity.setTemplateType(ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_PRIVATE);
			templateUserActivityEntities.add(new TemplateUserActivityEntity(
					ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PRIVATE, subscriberManagementBean, emailTemplateEntity));
		}
		if (!CollectionUtils.isEmpty(templateUserActivityEntities)) {
			emailTemplateEntity.setTemplateUserActivities(templateUserActivityEntities);
		}
		if (null != folderEntity) {
			List<TemplateFolderEntity> templateFolderEntities = new ArrayList<>();
			templateFolderEntities.add(new TemplateFolderEntity(emailTemplateEntity, folderEntity, subscriberManagementBean));
			if (!CollectionUtils.isEmpty(templateFolderEntities)) {
				emailTemplateEntity.setTemplateFolderEntities(templateFolderEntities);
			}
		}
		/*
		 * if(StringUtils.isNoneBlank(emailTemplateRequest.getFilter())) {
		 * TemplateUserActivityEntity userActivityEntity = null;
		 * if(emailTemplateRequest.getFilter().equalsIgnoreCase(ServiceConstants.
		 * EMAIL_TEMPLATE_FILTER_TYPE_PUBLIC)) { templateUserActivityEntities.add(new
		 * TemplateUserActivityEntity(ServiceConstants.
		 * EMAIL_TEMPLATE_FILTER_VALUE_PUBLIC, subscriberManagementBean,
		 * emailTemplateEntity)); } else
		 * if(emailTemplateRequest.getFilter().equalsIgnoreCase(ServiceConstants.
		 * EMAIL_TEMPLATE_FILTER_TYPE_SHARE)) {
		 * if(!CollectionUtils.isEmpty(emailTemplateRequest.getSharedUserIds())) { for
		 * (Long userId : emailTemplateRequest.getSharedUserIds()) {
		 * templateUserActivityEntities.add(new
		 * TemplateUserActivityEntity(ServiceConstants.
		 * EMAIL_TEMPLATE_FILTER_VALUE_SHARE, userId, subscriberManagementBean,
		 * emailTemplateEntity)); } } } else if(null !=
		 * emailTemplateRequest.getFolderId() ||
		 * emailTemplateRequest.getFilter().equalsIgnoreCase(ServiceConstants.
		 * EMAIL_TEMPLATE_FILTER_VALUE_PRIVATE)) { if(null != folderEntity) {
		 * List<TemplateFolderEntity> templateFolderEntities = new ArrayList<>();
		 * templateFolderEntities.add(new
		 * TemplateFolderEntity(emailTemplateEntity,folderEntity,
		 * subscriberManagementBean));
		 * emailTemplateEntity.setTemplateFolderEntities(templateFolderEntities); }
		 * userActivityEntity = new TemplateUserActivityEntity();
		 * userActivityEntity.setTemplateFilter(ServiceConstants.
		 * EMAIL_TEMPLATE_FILTER_VALUE_PRIVATE); templateUserActivityEntities.add(new
		 * TemplateUserActivityEntity(ServiceConstants.
		 * EMAIL_TEMPLATE_FILTER_VALUE_PRIVATE, subscriberManagementBean,
		 * emailTemplateEntity)); }
		 * 
		 * else { userActivityEntity = new TemplateUserActivityEntity();
		 * userActivityEntity.setTemplateFilter(ServiceConstants.
		 * EMAIL_TEMPLATE_FILTER_VALUE_PRIVATE); templateUserActivityEntities.add(new
		 * TemplateUserActivityEntity(ServiceConstants.
		 * EMAIL_TEMPLATE_FILTER_VALUE_PRIVATE, subscriberManagementBean,
		 * emailTemplateEntity)); }
		 * 
		 * }
		 */
		return emailTemplateEntity;
	}

	/**
	 * mapTemplateUserActivityToBeanResponse
	 * 
	 * @param content
	 * @param isNext 
	 * @param genericResponse
	 * @param userBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static GenericResponse mapTemplateUserActivityToBeanResponse(
			List<TemplateUserActivityEntity> content, Boolean isNext, GenericResponse genericResponse, UserBean userBean,
			SubscriberManagementBean subscriberManagementBean, EhCacheCacheManager cacheManager,
			UserMasterCacheUtility userMasterCacheUtility) {
		List<TemplateDetailBean> templateDetailBeans = new ArrayList<>();
		if (!CollectionUtils.isEmpty(content)) {
			content.forEach(userActivityEntity -> {
				templateDetailBeans
						.add(new TemplateDetailBean(userActivityEntity, (Map<Long, String>) genericResponse.getData(),
								userBean, subscriberManagementBean, cacheManager, userMasterCacheUtility));
			});
		}
		return new GenericResponse(templateDetailBeans,isNext);
	}

	/**
	 * mapFavTemplateToBeanResponse
	 * 
	 * @param content
	 * @param isNext 
	 * @param genericResponse
	 * @param userBean
	 * @param subscriberManagementBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static GenericResponse mapFavTemplateToBeanResponse(List<TemplateFavouriteEntity> content,
			Boolean isNext, GenericResponse genericResponse, UserBean userBean, SubscriberManagementBean subscriberManagementBean,
			EhCacheCacheManager cacheManager, UserMasterCacheUtility userMasterCacheUtility) {
		List<TemplateDetailBean> templateDetailBeans = new ArrayList<>();
		if (!CollectionUtils.isEmpty(content)) {
			content.forEach(favTemplateEntity -> {
				templateDetailBeans
						.add(new TemplateDetailBean(favTemplateEntity, (Map<Long, String>) genericResponse.getData(),
								userBean, subscriberManagementBean, cacheManager, userMasterCacheUtility));
			});
		}
		return new GenericResponse(templateDetailBeans,isNext);
	}

	/**
	 * mapTempDetailActivityToBeanResponse
	 * @param content
	 * @param isNext 
	 * @param genericResponse
	 * @param userBean
	 * @param subscriberManagementBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static GenericResponse mapTempDetailActivityToBeanResponse(List<EmailTemplateDetailsEntity> content,
			Boolean isNext, GenericResponse genericResponse, UserBean userBean, SubscriberManagementBean subscriberManagementBean,
			EhCacheCacheManager cacheManager, UserMasterCacheUtility userMasterCacheUtility) {
		List<TemplateDetailBean> templateDetailBeans = new ArrayList<>();
		if (!CollectionUtils.isEmpty(content)) {
			content.forEach(templateDetailEntity -> {
				templateDetailBeans
						.add(new TemplateDetailBean(templateDetailEntity, (Map<Long, String>) genericResponse.getData(),
								userBean, subscriberManagementBean, cacheManager, userMasterCacheUtility));
			});
		}
		return new GenericResponse(templateDetailBeans,isNext);
	}
	/**
	 * mapEmailTemplateDetailActivityToBeanResponse
	 * @param templateDetailEntity
	 * @param genericResponse
	 * @param userBean
	 * @param subscriberManagementBean
	 * @param cacheManager
	 * @param userMasterCacheUtility
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static TemplateDetailBean mapEmailTemplateDetailActivityToBeanResponse(EmailTemplateDetailsEntity templateDetailEntity,
			GenericResponse genericResponse, UserBean userBean, SubscriberManagementBean subscriberManagementBean,
			EhCacheCacheManager cacheManager, UserMasterCacheUtility userMasterCacheUtility) {
		TemplateDetailBean templateDetailBean = null;
		if (null != templateDetailEntity) {
			templateDetailBean = new TemplateDetailBean(templateDetailEntity, (Map<Long, String>) genericResponse.getData(),
					userBean, subscriberManagementBean, cacheManager, userMasterCacheUtility);
		}
		return templateDetailBean;
	}
	/**
	 * mapTemplateFolderEntityToBeanResponse
	 * @param isNext 
	 * @param content
	 * @param genericResponse
	 * @param userBean
	 * @param subscriberManagementBean
	 * @param cacheManager
	 * @param userMasterCacheUtility
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static GenericResponse mapTemplateFolderEntityToBeanResponse(List<TemplateFolderEntity> templateFolderEntities,
			Boolean isNext, GenericResponse genericResponse, UserBean userBean, SubscriberManagementBean subscriberManagementBean,
			EhCacheCacheManager cacheManager, UserMasterCacheUtility userMasterCacheUtility) {
		
		List<TemplateDetailBean> templateDetailBeans = new ArrayList<>();
		if (!CollectionUtils.isEmpty(templateFolderEntities)) {
			templateFolderEntities.forEach(templateFolderEntity -> {
				templateDetailBeans
						.add(new TemplateDetailBean(templateFolderEntity, (Map<Long, String>) genericResponse.getData(),
								userBean, subscriberManagementBean, cacheManager, userMasterCacheUtility));
			});
		}
		return new GenericResponse(templateDetailBeans,isNext);
	}
	/**
	 * mapTemplateRequestToEntityForEdit
	 * @param emailTemplateEntity
	 * @param emailTemplateRequest
	 * @param subscriberManagementBean
	 * @return
	 */
	public static EmailTemplateDetailsEntity mapTemplateRequestToEntityForEdit(EmailTemplateDetailsEntity emailTemplateEntity, 
			EmailTemplateRequest emailTemplateRequest, SubscriberManagementBean subscriberManagementBean) {

		if(StringUtils.isNoneBlank(emailTemplateRequest.getTemplateName())){
			emailTemplateEntity.setTemplateName(emailTemplateRequest.getTemplateName());
		}
		if(StringUtils.isNoneBlank(emailTemplateRequest.getSubject())){
			emailTemplateEntity.setTemplateSubject(emailTemplateRequest.getSubject());
		}
		if(null != emailTemplateRequest.getModuleId()){
			emailTemplateEntity.setTemplateModuleId(emailTemplateRequest.getModuleId());
		}
		if(StringUtils.isNoneBlank(emailTemplateRequest.getData())){
			emailTemplateEntity.setTemplateData(emailTemplateRequest.getData());
		}
		if(StringUtils.isNoneBlank(emailTemplateRequest.getAttachments())){
			emailTemplateEntity.setTemplateAttachments(emailTemplateRequest.getAttachments());
		}
		if (null != subscriberManagementBean && null != subscriberManagementBean.getUserMasterEntity()) {
			emailTemplateEntity.setUpdatedBy(subscriberManagementBean.getUserMasterEntity().getUserId());
		}
		emailTemplateEntity.setUpdatedDate(LocalDateTime.now());
		return emailTemplateEntity;
	}
}
