package com.salezshark.emailtemplate.service;

import java.util.List;

import com.salezshark.emailtemplate.bean.SubscriberManagementBean;
import com.salezshark.emailtemplate.entity.TemplateFiltersEntity;
import com.salezshark.emailtemplate.master.entity.MasterDataEntity;
import com.salezshark.emailtemplate.master.entity.UserMasterEntity;

public interface IMasterDataService {

	/**
	 * findByDataTypeInIgnoreCase
	 * @param dataTypes
	 * @param subscriberManagementBean
	 * @return
	 */
	List<MasterDataEntity> findByDataTypeInIgnoreCase(List<String> dataTypes, SubscriberManagementBean subscriberManagementBean);
	/**
	 * findAllFilters
	 * @return
	 */
	List<TemplateFiltersEntity> findAllFilters();
	
	/**
	 * getAllUsersByDsmId
	 * @param subscriberManagementBean
	 * @return
	 */
	List<UserMasterEntity> getAllUsersByDsmId(SubscriberManagementBean subscriberManagementBean);
	/**
	 * getAllUsersByDsmIdOrName : get either all users or by user name
	 * @param subscriberManagementBean
	 * @param name
	 * @return
	 */
	List<UserMasterEntity> getAllUsersByDsmIdOrName(SubscriberManagementBean subscriberManagementBean, String name);
}
