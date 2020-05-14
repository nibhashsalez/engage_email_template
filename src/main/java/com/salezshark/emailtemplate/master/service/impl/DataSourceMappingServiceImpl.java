package com.salezshark.emailtemplate.master.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.salezshark.emailtemplate.master.entity.DataSourceMappingEntity;
import com.salezshark.emailtemplate.master.repository.DataSourceMappingRepository;
import com.salezshark.emailtemplate.master.service.IDataSourceMappingService;

/**
 * @author nibhash
 * @version 1.0
 * @since 1.0
 */

@Service
public class DataSourceMappingServiceImpl implements IDataSourceMappingService {

    @Autowired
    DataSourceMappingRepository dataSourceMappingRepository;

    @Override
    @Cacheable("dsmID_cache")
    public String getDataSourceMappingById(Long dataSourceMappingId) {
    	DataSourceMappingEntity dataSourceMappingEntity = dataSourceMappingRepository.findByDataSourceMappingIdAndIsActiveTrue(dataSourceMappingId);
    	if(null != dataSourceMappingEntity && StringUtils.isNoneBlank(dataSourceMappingEntity.getTenantId())) {
    		return dataSourceMappingEntity.getTenantId();
    	}
        return null;
    }
}
