package com.salezshark.emailtemplate.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salezshark.emailtemplate.bean.SubscriberManagementBean;
import com.salezshark.emailtemplate.entity.TemplateFiltersEntity;
import com.salezshark.emailtemplate.master.entity.MasterDataEntity;
import com.salezshark.emailtemplate.master.entity.UserMasterEntity;
import com.salezshark.emailtemplate.master.repository.MasterDataRepository;
import com.salezshark.emailtemplate.repository.TemplateFilterRepository;
import com.salezshark.emailtemplate.service.IMasterDataService;
/**
 * 
 * @author nibhash
 *
 */
@Service
public class MasterDataServiceImpl implements IMasterDataService {

	@Autowired
	private MasterDataRepository masterDataRepository;

	@Autowired
	private TemplateFilterRepository templateFilterRepository;

	@Override
	public List<MasterDataEntity> findByDataTypeInIgnoreCase(List<String> dataTypes, SubscriberManagementBean subscriberManagementBean) {
		if(null != subscriberManagementBean && null != subscriberManagementBean.getUserMasterEntity() 
				&& null != subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId()) {
			return masterDataRepository.findByDataTypeInIgnoreCase(dataTypes);
		}
		return null;
	}

	@Override
	public List<TemplateFiltersEntity> findAllFilters() {
		return templateFilterRepository.findAll();
	}

	@Override
	public List<UserMasterEntity> getAllUsersByDsmIdOrName(SubscriberManagementBean subscriberManagementBean, String name) {
		if(null != subscriberManagementBean && null != subscriberManagementBean.getUserMasterEntity() 
				&& null != subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId()) {
			if(StringUtils.isNoneBlank(name)) {
				return masterDataRepository.getAllUsersByDsmIdOrName(subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId(), name);
			}else {
				return masterDataRepository.getAllUsersByDsmId(subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId());
			}
		}
		return null;
	}

	@Override
	public List<UserMasterEntity> getAllUsersByDsmId(SubscriberManagementBean subscriberManagementBean) {
		// TODO Auto-generated method stub
		return null;
	}
}
