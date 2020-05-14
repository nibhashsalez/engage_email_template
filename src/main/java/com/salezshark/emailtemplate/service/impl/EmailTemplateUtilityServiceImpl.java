package com.salezshark.emailtemplate.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.salezshark.emailtemplate.bean.SubscriberManagementBean;
import com.salezshark.emailtemplate.bean.UtilityBean;
import com.salezshark.emailtemplate.constants.ServiceConstants;
import com.salezshark.emailtemplate.entity.TemplateFiltersEntity;
import com.salezshark.emailtemplate.entity.FolderMasterEntity;
import com.salezshark.emailtemplate.exception.EmailTemplateException;
import com.salezshark.emailtemplate.master.entity.MasterDataEntity;
import com.salezshark.emailtemplate.master.entity.UserMasterEntity;
import com.salezshark.emailtemplate.repository.FolderMasterRepository;
import com.salezshark.emailtemplate.repository.TemplateFolderRepository;
import com.salezshark.emailtemplate.response.GenericResponse;
import com.salezshark.emailtemplate.service.IEmailTemplateUtilityService;
import com.salezshark.emailtemplate.service.IMasterDataService;
import com.salezshark.emailtemplate.utility.RepositoryUtils;

@Service
public class EmailTemplateUtilityServiceImpl implements IEmailTemplateUtilityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailTemplateUtilityServiceImpl.class);
	private static final String classname = EmailTemplateUtilityServiceImpl.class.getName();

	@Autowired
	private IMasterDataService masterDataService;

	@Autowired
	private FolderMasterRepository folderRepository;

	@Autowired
	private SubscriberManagementBean subscriberManagementBean;

	@Autowired
	private RepositoryUtils repositoryUtils;

	@Autowired
	private TemplateFolderRepository templateFolderRepository;

	@Override
	public GenericResponse getAllModules(HttpServletRequest request) throws EmailTemplateException {
		GenericResponse genericResponse = new GenericResponse();
		try {
			List<String> dataTypes = new ArrayList<>();
			dataTypes.add(ServiceConstants.DATA_TYPE_ACTIVITY_TYPE.toLowerCase());
			dataTypes.add(ServiceConstants.DATA_TYPE_CONTACT_TYPE.toLowerCase());
			List<MasterDataEntity> masterDataEntities=masterDataService.findByDataTypeInIgnoreCase(dataTypes, subscriberManagementBean);
			if(!CollectionUtils.isEmpty(masterDataEntities)) {
				List<MasterDataEntity> relatedModuleMasterDataEntities = masterDataEntities.stream().filter(
						masterData -> masterData.getDataValue().equalsIgnoreCase(ServiceConstants.DATA_VALUE_LEAD)
						|| masterData.getDataValue().equalsIgnoreCase(ServiceConstants.DATA_VALUE_CONTACT)
						|| masterData.getDataValue().equalsIgnoreCase(ServiceConstants.DATA_VALUE_ACCOUNT)
						|| masterData.getDataValue().equalsIgnoreCase(ServiceConstants.DATA_VALUE_OPPORTUNITY)).collect(Collectors.toList());
				if(!CollectionUtils.isEmpty(relatedModuleMasterDataEntities)) {
					List<UtilityBean> modules = masterDataEntities.stream().map(masterData -> new UtilityBean(masterData.getMasterDataId(),
							masterData.getModifiedDataValue())).collect(Collectors.toList());
					genericResponse.setData(modules);
					genericResponse.setStatusCode(HttpStatus.OK.value());
				}
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".getAllModules(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
		return genericResponse;
	}

	@Override
	public GenericResponse getAllFilters(HttpServletRequest request) throws EmailTemplateException {
		GenericResponse genericResponse = new GenericResponse();
		try {
			List<TemplateFiltersEntity> filtersEntities = masterDataService.findAllFilters();
			if(!CollectionUtils.isEmpty(filtersEntities)) {
				List<UtilityBean> filters = filtersEntities.stream().map(filterEntity -> new UtilityBean(filterEntity.getTemplateFilterId(),
						filterEntity.getTemplateFilterName())).collect(Collectors.toList());
				genericResponse.setData(filters);
				genericResponse.setStatusCode(HttpStatus.OK.value());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
		return genericResponse;
	}

	@Override
	public GenericResponse getAllUsersByDsmIdOrName(HttpServletRequest request, String name) throws EmailTemplateException {
		GenericResponse genericResponse = new GenericResponse();
		try {
			List<UserMasterEntity> userMasterEntities = masterDataService.getAllUsersByDsmIdOrName(subscriberManagementBean, name);
			if(!CollectionUtils.isEmpty(userMasterEntities)) {
				List<UtilityBean> users = new ArrayList<>();
				for (UserMasterEntity userMasterEntity : userMasterEntities) {
					if(userMasterEntity.getUserId() != subscriberManagementBean.getLoginUserId()) {
						users.add(new UtilityBean(userMasterEntity.getUserId(), userMasterEntity.getUserFullName()));
					}
				}
				/*
				 * List<UtilityBean> users = userMasterEntities.stream().map(userMasterEntity ->
				 * new UtilityBean(userMasterEntity.getUserId(),
				 * userMasterEntity.getUserFullName())).collect(Collectors.toList());
				 */
				genericResponse.setData(users);
				genericResponse.setStatusCode(HttpStatus.OK.value());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
		return genericResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse getAllModuleMapByDsmId(HttpServletRequest request) throws EmailTemplateException {
		GenericResponse genericResponse = new GenericResponse();
		try {
			genericResponse = getAllModules(request);
			if(null != genericResponse && null != genericResponse.getData()) {
				List<UtilityBean> utilityBeans = (List<UtilityBean>) genericResponse.getData();
				Map<Long, String> moduleMap = utilityBeans.stream().collect(Collectors.toMap(UtilityBean::getId, UtilityBean::getValue));
				genericResponse.setData(!moduleMap.isEmpty() ? moduleMap : null);
				genericResponse.setStatusCode(HttpStatus.OK.value());
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".getAllModules(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
		return genericResponse;
	}

	@Override
	public GenericResponse getAllFolders(HttpServletRequest request) throws EmailTemplateException {
		GenericResponse genericResponse = new GenericResponse();
		try {
			List<FolderMasterEntity> folderEntities = folderRepository.findAllByIsActiveTrueAndDsmIdAndCreatedBy(
					subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(), 
					subscriberManagementBean.getUserMasterEntity().getUserId(), repositoryUtils.getSortBy(ServiceConstants.DEFAULT_SORT_BY));
			if(!CollectionUtils.isEmpty(folderEntities)) {
				List<UtilityBean> folders = new ArrayList<>();
				for (FolderMasterEntity folderMasterEntity : folderEntities) {
					Long templateCount = templateFolderRepository.getTemplateCountByFolderId(folderMasterEntity.getId(), 
							subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
					UtilityBean utilityBean = new UtilityBean(folderMasterEntity.getId(), folderMasterEntity.getFolderName(), templateCount);
					folders.add(utilityBean);
				}
				if(!CollectionUtils.isEmpty(folders)) {
					genericResponse.setData(folders);
					genericResponse.setStatusCode(HttpStatus.OK.value());
				}
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".getAllFolders(); " + e);
			e.printStackTrace();
			return new GenericResponse(null, ServiceConstants.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR,
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
		}
		return genericResponse;
	}
}
