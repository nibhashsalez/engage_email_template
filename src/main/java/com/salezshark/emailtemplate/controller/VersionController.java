package com.salezshark.emailtemplate.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salezshark.emailtemplate.bean.UserBean;
import com.salezshark.emailtemplate.constants.ServiceConstants;

/**
 * @author nibhash
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/template")
public class VersionController {

	/** Cache Manager */
	@Autowired
	private EhCacheCacheManager cacheManager;
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/version")
	public String listUser() {
		Cache cache = cacheManager.getCache("userCacheByDsmId");
		if(null != cache) {
			ValueWrapper valueWrapper =  cache.get(ServiceConstants.USER_CACHED_BY_DSMID);
			final Map<Long, Map<Long, UserBean>> datasourceMappingMap = (Map<Long, Map<Long, UserBean>>) valueWrapper.get();
			System.out.println(datasourceMappingMap.size());
			System.out.println("dmsId : "+2021+" - "+datasourceMappingMap.get(2021l).size());
		}
		return "VERSION 1.0";
	}
}
