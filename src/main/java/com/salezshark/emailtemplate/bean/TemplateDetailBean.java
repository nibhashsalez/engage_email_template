package com.salezshark.emailtemplate.bean;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salezshark.emailtemplate.constants.ServiceConstants;
import com.salezshark.emailtemplate.entity.EmailTemplateDetailsEntity;
import com.salezshark.emailtemplate.entity.TemplateFavouriteEntity;
import com.salezshark.emailtemplate.entity.TemplateFolderEntity;
import com.salezshark.emailtemplate.entity.TemplateUserActivityEntity;
import com.salezshark.emailtemplate.utility.DateUtility;
import com.salezshark.emailtemplate.utility.UserMasterCacheUtility;

/**
 * @author Nibhash
 * 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateDetailBean {
	
	private Long templateId;
	private String name;
	private String subject;
	private String data;
	private Long moduleId;
	private String moduleName;
	private Long createdBy;
	private String createdByName;
	private Long dsmId;
	private String templateFilter;
	private Boolean isFavourite;
	private Boolean isDeletePermission = false;
	private Boolean isEditPermission = false;
	private Boolean isShareable = false;
	private String category;	// filter type
	private Map<Long, String> folder;
	private Map<Long, String> sharedWith;
	private Long folderId;

//	@Convert(converter = LocalDateTimeConverter.class)
	private String createdDate;
	
//	@Convert(converter = LocalDateTimeConverter.class)
	private String updatedDate;
	
	private Map<Long, String> sahredWithMap;
	
	
	/**
	 * cons
	 */
	public TemplateDetailBean() {

	}
	/**
	 * cons : when no filter applied
	 * @param userActivityEntity 
	 * @param moduleMap
	 * @param userBean
	 */
	public TemplateDetailBean(TemplateUserActivityEntity userActivityEntity, Map<Long, String> moduleMap, UserBean userBean, 
			SubscriberManagementBean subscriberManagementBean, EhCacheCacheManager cacheManager, UserMasterCacheUtility userMasterCacheUtility) {
		if(null != userActivityEntity.getTemplateDetailsEntity()) {
			this.templateId = userActivityEntity.getTemplateDetailsEntity().getTemplateId();
			this.name = StringUtils.isNoneBlank(userActivityEntity.getTemplateDetailsEntity().getTemplateName())
					? userActivityEntity.getTemplateDetailsEntity().getTemplateName() : null;
			this.subject = StringUtils.isNoneBlank(userActivityEntity.getTemplateDetailsEntity().getTemplateSubject()) 
					? userActivityEntity.getTemplateDetailsEntity().getTemplateSubject() : null;
			this.data = StringUtils.isNoneBlank(userActivityEntity.getTemplateDetailsEntity().getTemplateData()) 
					? userActivityEntity.getTemplateDetailsEntity().getTemplateData() : null;
			if(null != userActivityEntity.getTemplateDetailsEntity().getTemplateModuleId()) {
				if(!moduleMap.isEmpty()) {
					this.moduleId = userActivityEntity.getTemplateDetailsEntity().getTemplateModuleId();
					this.moduleName = moduleMap.get(userActivityEntity.getTemplateDetailsEntity().getTemplateModuleId());
				}
			}
			if(null != userBean && userBean.getUserId().equals(userActivityEntity.getTemplateDetailsEntity().getCreatedBy()) ){
				this.createdBy = userBean.getUserId();
				this.createdByName = StringUtils.isNoneBlank(userBean.getUserName()) ? userBean.getUserName() : null;
			}else {
				UserBean templateCreatedBy = userMasterCacheUtility.getUserByIdFromCacheOrDB(cacheManager, 
						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(),
						userActivityEntity.getTemplateDetailsEntity().getCreatedBy());
				if(null != templateCreatedBy) {
					this.createdBy = templateCreatedBy.getUserId();
					this.createdByName = StringUtils.isNoneBlank(templateCreatedBy.getUserName()) ? templateCreatedBy.getUserName() : null;
				}
			}
			if(!CollectionUtils.isEmpty(userBean.getFavTemplateIds())) {
				this.isFavourite = userBean.getFavTemplateIds().contains(templateId) ? true : false;
			}
			if(this.createdBy.equals(subscriberManagementBean.getLoginUserId())) {
				this.isDeletePermission = true;
				this.isEditPermission = true;
			}
			// get template type (private/public/shared)
			if(StringUtils.isNoneBlank(userActivityEntity.getTemplateFilter())) {
				if(userActivityEntity.getTemplateFilter().equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PUBLIC)) { 
					this.category = ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_PUBLIC; 
				}
				if(userActivityEntity.getTemplateFilter().equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PRIVATE)) { 
					this.category = ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_PRIVATE; 
					this.isShareable = true;
				}
				if(userActivityEntity.getTemplateFilter().equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_SHARE)) { 
					this.category = ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_SHARE; 
				} 
			}
			/*
			 * // get template assigned with
			 * if(!CollectionUtils.isEmpty(userActivityEntity.getTemplateDetailsEntity().
			 * getTemplateUserActivities())) { for (TemplateUserActivityEntity
			 * templateUserActivityEntity :
			 * userActivityEntity.getTemplateDetailsEntity().getTemplateUserActivities()) {
			 * UserBean sharedWithUser =
			 * userMasterCacheUtility.getUserByIdFromCacheOrDB(cacheManager,
			 * subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().
			 * getDataSourceMappingId(), templateUserActivityEntity.getSharedWithUserId());
			 * if(null != sharedWithUser) { this.sharedWith.put(sharedWithUser.getUserId(),
			 * sharedWithUser.getUserName()); } } } // get template moved to folder
			 * if(!CollectionUtils.isEmpty(userActivityEntity.getTemplateDetailsEntity().
			 * getTemplateFolderEntities())) { for (TemplateFolderEntity folderEntity :
			 * userActivityEntity.getTemplateDetailsEntity().getTemplateFolderEntities()) {
			 * if(folderEntity.getTemplateDetailsEntity().equals(userActivityEntity.
			 * getTemplateDetailsEntity()) &&
			 * folderEntity.getCreatedBy().equals(subscriberManagementBean.
			 * getUserMasterEntity().getUserId())) {
			 * this.folder.put(folderEntity.getFolderMasterEntity().getId(),
			 * folderEntity.getFolderMasterEntity().getFolderName()); } } }
			 */
			this.dsmId = userActivityEntity.getTemplateDetailsEntity().getDsmId();
//			this.createdDate = userActivityEntity.getTemplateDetailsEntity().getCreatedDate();
			this.createdDate = DateUtility.convertFormTimstampToDD_MMM_YYYYFormat(userActivityEntity.getTemplateDetailsEntity().getCreatedDate());
		}
	}
	/**
	 * cons : when favourite filter applied 
	 * @param favTemplateEntity
	 * @param moduleMap
	 * @param userBean
	 * @param subscriberManagementBean
	 */
	public TemplateDetailBean(TemplateFavouriteEntity favTemplateEntity, Map<Long, String> moduleMap, UserBean userBean, 
			SubscriberManagementBean subscriberManagementBean, EhCacheCacheManager cacheManager, UserMasterCacheUtility userMasterCacheUtility) {
		if(null != favTemplateEntity.getTemplateDetailsEntity()) {
			this.templateId = favTemplateEntity.getTemplateDetailsEntity().getTemplateId();
			this.name = StringUtils.isNoneBlank(favTemplateEntity.getTemplateDetailsEntity().getTemplateName())
					? favTemplateEntity.getTemplateDetailsEntity().getTemplateName() : null;
			this.subject = StringUtils.isNoneBlank(favTemplateEntity.getTemplateDetailsEntity().getTemplateSubject()) 
					? favTemplateEntity.getTemplateDetailsEntity().getTemplateSubject() : null;
			this.data = StringUtils.isNoneBlank(favTemplateEntity.getTemplateDetailsEntity().getTemplateData()) 
					? favTemplateEntity.getTemplateDetailsEntity().getTemplateData() : null;
			if(null != favTemplateEntity.getTemplateDetailsEntity().getTemplateModuleId()) {
				if(!moduleMap.isEmpty()) {
					this.moduleId = favTemplateEntity.getTemplateDetailsEntity().getTemplateModuleId();
					this.moduleName = moduleMap.get(favTemplateEntity.getTemplateDetailsEntity().getTemplateModuleId());
				}
			}
			
			if(null != userBean && userBean.getUserId().equals(favTemplateEntity.getTemplateDetailsEntity().getCreatedBy()) ){
				this.createdBy = userBean.getUserId();
				this.createdByName = StringUtils.isNoneBlank(userBean.getUserName()) ? userBean.getUserName() : null;
			}else {
				UserBean templateCreatedBy = userMasterCacheUtility.getUserByIdFromCacheOrDB(cacheManager, 
						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(),
						favTemplateEntity.getTemplateDetailsEntity().getCreatedBy());
				if(null != templateCreatedBy) {
					this.createdBy = templateCreatedBy.getUserId();
					this.createdByName = StringUtils.isNoneBlank(templateCreatedBy.getUserName()) ? templateCreatedBy.getUserName() : null;
				}
			}
			if(!CollectionUtils.isEmpty(userBean.getFavTemplateIds())) {
				this.isFavourite = userBean.getFavTemplateIds().contains(templateId) ? true : false;
			}
			if(this.createdBy.equals(subscriberManagementBean.getLoginUserId())) {
				this.isDeletePermission = true;
				this.isEditPermission = true;
			}
			if(StringUtils.isNoneBlank(favTemplateEntity.getTemplateDetailsEntity().getTemplateType())) {
				
				this.category = favTemplateEntity.getTemplateDetailsEntity().getTemplateType();
				if(favTemplateEntity.getTemplateDetailsEntity().getTemplateType().equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_PRIVATE)) {
					this.isShareable = true;
				}
				/*
				 * if(favTemplateEntity.getTemplateDetailsEntity().getTemplateType().
				 * equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PUBLIC)) {
				 * this.category = ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_PUBLIC; }
				 * if(favTemplateEntity.getTemplateDetailsEntity().getTemplateType().
				 * equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PRIVATE)) {
				 * this.category = ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_PRIVATE;
				 * this.isShareable = true; }
				 */
				/*
				 * if(userActivityEntity.getTemplateFilter().equalsIgnoreCase(ServiceConstants.
				 * EMAIL_TEMPLATE_FILTER_VALUE_SHARE)) { this.category =
				 * ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_SHARE; }
				 */
			}
			this.dsmId = favTemplateEntity.getTemplateDetailsEntity().getDsmId();
//			this.createdDate = favTemplateEntity.getTemplateDetailsEntity().getCreatedDate();
			this.createdDate = DateUtility.convertFormTimstampToDD_MMM_YYYYFormat(favTemplateEntity.getTemplateDetailsEntity().getCreatedDate());
		}
	}
	/**
	 * cons : when created by me filter applied 
	 * @param templateDetailEntity
	 * @param moduleMap
	 * @param userBean
	 * @param subscriberManagementBean
	 */
	public TemplateDetailBean(EmailTemplateDetailsEntity templateDetailEntity, Map<Long, String> moduleMap, UserBean userBean, 
			SubscriberManagementBean subscriberManagementBean, EhCacheCacheManager cacheManager, UserMasterCacheUtility userMasterCacheUtility) {
		if(null != templateDetailEntity) {
			this.templateId = templateDetailEntity.getTemplateId();
			this.name = StringUtils.isNoneBlank(templateDetailEntity.getTemplateName())
					? templateDetailEntity.getTemplateName() : null;
			this.subject = StringUtils.isNoneBlank(templateDetailEntity.getTemplateSubject()) 
					? templateDetailEntity.getTemplateSubject() : null;
			this.data = StringUtils.isNoneBlank(templateDetailEntity.getTemplateData()) 
					? templateDetailEntity.getTemplateData() : null;
			if(null != templateDetailEntity.getTemplateModuleId()) {
				if(!moduleMap.isEmpty()) {
					this.moduleId = templateDetailEntity.getTemplateModuleId();
					this.moduleName = moduleMap.get(templateDetailEntity.getTemplateModuleId());
				}
			}
			if(null != userBean && userBean.getUserId().equals(templateDetailEntity.getCreatedBy()) ){
				this.createdBy = userBean.getUserId();
				this.createdByName = StringUtils.isNoneBlank(userBean.getUserName()) ? userBean.getUserName() : null;
			}else {
				UserBean templateCreatedBy = userMasterCacheUtility.getUserByIdFromCacheOrDB(cacheManager, 
						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(),
						templateDetailEntity.getCreatedBy());
				if(null != templateCreatedBy) {
					this.createdBy = templateCreatedBy.getUserId();
					this.createdByName = StringUtils.isNoneBlank(templateCreatedBy.getUserName()) ? templateCreatedBy.getUserName() : null;
				}
			}
			if(!CollectionUtils.isEmpty(userBean.getFavTemplateIds())) {
				this.isFavourite = userBean.getFavTemplateIds().contains(templateId) ? true : false;
			}
			if(this.createdBy.equals(subscriberManagementBean.getLoginUserId())) {
				this.isDeletePermission = true;
				this.isEditPermission = true;
			}
			if(StringUtils.isNoneBlank(templateDetailEntity.getTemplateType())) {
				this.category = templateDetailEntity.getTemplateType();
				if(templateDetailEntity.getTemplateType().equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_PRIVATE)) {
					this.isShareable = true;
				}
			}
			this.dsmId = templateDetailEntity.getDsmId();
//			this.createdDate = templateDetailEntity.getCreatedDate();
			this.createdDate = DateUtility.convertFormTimstampToDD_MMM_YYYYFormat(templateDetailEntity.getCreatedDate());
			this.updatedDate = null != templateDetailEntity.getUpdatedDate() ? 
					DateUtility.convertFormTimstampToDD_MMM_YYYYFormat(templateDetailEntity.getUpdatedDate()) : null;
		}
	}
	/**
	 * cons : when folder filter is applied
	 * @param templateFolderEntity
	 * @param moduleMap
	 * @param userBean
	 * @param subscriberManagementBean
	 * @param cacheManager
	 * @param userMasterCacheUtility
	 */
	public TemplateDetailBean(TemplateFolderEntity templateFolderEntity, Map<Long, String> moduleMap, UserBean userBean, 
			SubscriberManagementBean subscriberManagementBean, EhCacheCacheManager cacheManager, UserMasterCacheUtility userMasterCacheUtility) {
		if(null != templateFolderEntity.getTemplateDetailsEntity()) {
			this.templateId = templateFolderEntity.getTemplateDetailsEntity().getTemplateId();
			this.name = StringUtils.isNoneBlank(templateFolderEntity.getTemplateDetailsEntity().getTemplateName())
					? templateFolderEntity.getTemplateDetailsEntity().getTemplateName() : null;
			this.subject = StringUtils.isNoneBlank(templateFolderEntity.getTemplateDetailsEntity().getTemplateSubject()) 
					? templateFolderEntity.getTemplateDetailsEntity().getTemplateSubject() : null;
			this.data = StringUtils.isNoneBlank(templateFolderEntity.getTemplateDetailsEntity().getTemplateData()) 
					? templateFolderEntity.getTemplateDetailsEntity().getTemplateData() : null;
			if(null != templateFolderEntity.getTemplateDetailsEntity().getTemplateModuleId()) {
				if(!moduleMap.isEmpty()) {
					this.moduleId = templateFolderEntity.getTemplateDetailsEntity().getTemplateModuleId();
					this.moduleName = moduleMap.get(templateFolderEntity.getTemplateDetailsEntity().getTemplateModuleId());
				}
			}
			
			if(null != userBean && userBean.getUserId().equals(templateFolderEntity.getTemplateDetailsEntity().getCreatedBy()) ){
				this.createdBy = userBean.getUserId();
				this.createdByName = StringUtils.isNoneBlank(userBean.getUserName()) ? userBean.getUserName() : null;
			}else {
				UserBean templateCreatedBy = userMasterCacheUtility.getUserByIdFromCacheOrDB(cacheManager, 
						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(),
						templateFolderEntity.getTemplateDetailsEntity().getCreatedBy());
				if(null != templateCreatedBy) {
					this.createdBy = templateCreatedBy.getUserId();
					this.createdByName = StringUtils.isNoneBlank(templateCreatedBy.getUserName()) ? templateCreatedBy.getUserName() : null;
				}
			}
			if(!CollectionUtils.isEmpty(userBean.getFavTemplateIds())) {
				this.isFavourite = userBean.getFavTemplateIds().contains(templateId) ? true : false;
			}
			this.isDeletePermission = true;
			if(this.createdBy.equals(subscriberManagementBean.getLoginUserId())) {
				this.isEditPermission = true;
			}
			if(StringUtils.isNoneBlank(templateFolderEntity.getTemplateDetailsEntity().getTemplateType())) {
				this.category = templateFolderEntity.getTemplateDetailsEntity().getTemplateType();
				if(templateFolderEntity.getTemplateDetailsEntity().getTemplateType().equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_PRIVATE)) {
					this.isShareable = true;
				}
			}
			this.dsmId = templateFolderEntity.getTemplateDetailsEntity().getDsmId();
//			this.createdDate = templateFolderEntity.getTemplateDetailsEntity().getCreatedDate();
			this.createdDate = DateUtility.convertFormTimstampToDD_MMM_YYYYFormat(templateFolderEntity.getTemplateDetailsEntity().getCreatedDate());
		}
	}
	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Long getDsmId() {
		return dsmId;
	}

	public void setDsmId(Long dsmId) {
		this.dsmId = dsmId;
	}

	public String getTemplateFilter() {
		return templateFilter;
	}

	public void setTemplateFilter(String templateFilter) {
		this.templateFilter = templateFilter;
	}

	public Boolean getIsFavourite() {
		return isFavourite;
	}

	public void setIsFavourite(Boolean isFavourite) {
		this.isFavourite = isFavourite;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Boolean getIsDeletePermission() {
		return isDeletePermission;
	}
	public void setIsDeletePermission(Boolean isDeletePermission) {
		this.isDeletePermission = isDeletePermission;
	}
	public Boolean getIsEditPermission() {
		return isEditPermission;
	}
	public void setIsEditPermission(Boolean isEditPermission) {
		this.isEditPermission = isEditPermission;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public Map<Long, String> getFolder() {
		return folder;
	}
	public void setFolder(Map<Long, String> folder) {
		this.folder = folder;
	}
	public Map<Long, String> getSharedWith() {
		return sharedWith;
	}
	public void setSharedWith(Map<Long, String> sharedWith) {
		this.sharedWith = sharedWith;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Map<Long, String> getSahredWithMap() {
		return sahredWithMap;
	}
	public void setSahredWithMap(Map<Long, String> sahredWithMap) {
		this.sahredWithMap = sahredWithMap;
	}
	public Boolean getIsShareable() {
		return isShareable;
	}
	public void setIsShareable(Boolean isShareable) {
		this.isShareable = isShareable;
	}
	public Long getFolderId() {
		return folderId;
	}
	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
}
