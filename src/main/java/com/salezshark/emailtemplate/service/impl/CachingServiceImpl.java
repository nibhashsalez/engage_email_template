package com.salezshark.emailtemplate.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.salezshark.emailtemplate.bean.UserBean;
import com.salezshark.emailtemplate.constants.ServiceConstants;
import com.salezshark.emailtemplate.exception.EmailTemplateException;
import com.salezshark.emailtemplate.master.entity.DataSourceMappingEntity;
import com.salezshark.emailtemplate.master.entity.UserMasterEntity;
import com.salezshark.emailtemplate.master.repository.DataSourceMappingRepository;
import com.salezshark.emailtemplate.master.repository.UserDetailsRepository;
import com.salezshark.emailtemplate.repository.FavouriteTemplateRepository;
import com.salezshark.emailtemplate.service.ICachingService;

/**
 * 
 * @author nibhash
 *
 */
@Service
public class CachingServiceImpl implements ICachingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CachingServiceImpl.class);
	private static final String classname = CachingServiceImpl.class.getName();

	@Autowired
	private EhCacheCacheManager cacheManager;

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private DataSourceMappingRepository dataSourceMappingRepository;

	@Autowired
	private FavouriteTemplateRepository favouriteTemplateRepository;

	/**
	 * @return the enterpriseCacheManager
	 */
	public EhCacheCacheManager getEnterpriseCacheManager() {
		return cacheManager;
	}

	@Override
	public void cachedDataSourceMapping() throws EmailTemplateException {
		LOGGER.info("Executing " + classname + ".cachedDataSourceMapping");
		try {
			if (cacheManager == null) {
				LOGGER.info(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".cachedDataSourceMapping (cacheManager is null)");
			}else {
				final Cache userCacheByDsmId = cacheManager.getCache("userCacheByDsmId");
				if (null != userCacheByDsmId) {
					final Map<Long, Map<Long, UserBean>> datasourceMappingMap = new ConcurrentHashMap<>(100);
					// find all data source mapping list
					List<DataSourceMappingEntity> dataSourceMappingEntities = dataSourceMappingRepository.findAllByIsActiveTrue();
					if(!CollectionUtils.isEmpty(dataSourceMappingEntities)) {
						for (DataSourceMappingEntity dataSourceMappingEntity : dataSourceMappingEntities) {
							// find all users by dsmId
							List<UserMasterEntity> userMasterEntities = userDetailsRepository.findAllUserByDsmId(false, false, true, dataSourceMappingEntity.getDataSourceMappingId());
							if(!CollectionUtils.isEmpty(userMasterEntities)) {
								final Map<Long, UserBean> useBeanMap = new ConcurrentHashMap<>(100);
								for (UserMasterEntity userMasterEntity : userMasterEntities) {
									// find all user favorite templates
									List<Long> userFavTemplateIds = favouriteTemplateRepository.
											getTemplateIdByUserIdAndDsmId(userMasterEntity.getUserId(), userMasterEntity.getDataSourceMappingId().getDataSourceMappingId());
									useBeanMap.put(userMasterEntity.getUserId(), new UserBean(userMasterEntity, userFavTemplateIds));
								}
								if(!useBeanMap.isEmpty()) {
									datasourceMappingMap.put(dataSourceMappingEntity.getDataSourceMappingId(), useBeanMap);
								}
							}
						}
						if(! datasourceMappingMap.isEmpty()) {
							userCacheByDsmId.put(ServiceConstants.USER_CACHED_BY_DSMID, datasourceMappingMap);
						}
					}
				}else {
					LOGGER.info(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".cachedDataSourceMapping (dsmCache is null)");
				}
			}
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".cachedDataSourceMapping(); " + e);
			e.printStackTrace();
		}
	}

}
