package com.salezshark.emailtemplate.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.salezshark.emailtemplate.bean.UserBean;
import com.salezshark.emailtemplate.constants.ServiceConstants;
import com.salezshark.emailtemplate.master.entity.DataSourceMappingEntity;
import com.salezshark.emailtemplate.master.entity.UserMasterEntity;
import com.salezshark.emailtemplate.master.repository.DataSourceMappingRepository;
import com.salezshark.emailtemplate.master.repository.UserDetailsRepository;
import com.salezshark.emailtemplate.repository.FavouriteTemplateRepository;


/**
 * @author Nibhash
 * 
 */
@Component
public class UserMasterCacheUtility {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserMasterCacheUtility.class);
	private static final String classname = UserMasterCacheUtility.class.getName();

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private DataSourceMappingRepository dataSourceMappingRepository;

	@Autowired
	private FavouriteTemplateRepository favouriteTemplateRepository;

	/**
	 * getUserByIdFromCacheOrDB
	 * @param cacheManager
	 * @param dsmId
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  UserBean getUserByIdFromCacheOrDB(EhCacheCacheManager cacheManager, Long dsmId, Long userId) {
		LOGGER.info("Executing " + classname + ".getUserByIdFromCacheOrDB");
		UserBean userBean = null;
		Cache cache = cacheManager.getCache("userCacheByDsmId");
		Map<Long, Map<Long, UserBean>> datasourceMappingMap = new ConcurrentHashMap<>();
		if(null != cache) {
			ValueWrapper valueWrapper =  cache.get(ServiceConstants.USER_CACHED_BY_DSMID);
			if(null == valueWrapper) {
				LOGGER.info("No Master Cache Available");
				// get from DB and push to master cache
				getDsmForCaching(cache, datasourceMappingMap);
			}else {
				datasourceMappingMap = (Map<Long, Map<Long, UserBean>>) valueWrapper.get();
			}
			if(!datasourceMappingMap.isEmpty()) {
				Map<Long, UserBean> cachedUserMap = datasourceMappingMap.get(dsmId);
				if(!cachedUserMap.isEmpty()) {
					userBean = cachedUserMap.get(userId);
					if(null == userBean) {
						List<UserMasterEntity> userMasterEntities = userDetailsRepository.findAllUserByDsmId(false, false, true, dsmId);
						if(!CollectionUtils.isEmpty(userMasterEntities)) {
							UserMasterEntity userMasterEntity = userMasterEntities.get(0);
							List<Long> userFavTemplateIds = favouriteTemplateRepository.
									getTemplateIdByUserIdAndDsmId(userMasterEntity.getUserId(), userMasterEntity.getDataSourceMappingId().getDataSourceMappingId());

							if(cachedUserMap.size() > ServiceConstants.CACHE_SIZE) {
								Iterator<Long> iterator = cachedUserMap.keySet().iterator();
								while(iterator.hasNext()) {
									iterator.next(); 
									iterator.remove(); 
									break; 
								}
								userBean = new UserBean(userMasterEntity, userFavTemplateIds);
								cachedUserMap.put(userMasterEntity.getUserId(), userBean);
							}
						}
					}
				}
			}
		}
		return userBean;
	}

	/**
	 * getDsmForCaching
	 * @param cache
	 * @param datasourceMappingMap
	 */
	private void getDsmForCaching(Cache cache, Map<Long, Map<Long, UserBean>> datasourceMappingMap) {
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
				cache.put(ServiceConstants.USER_CACHED_BY_DSMID, datasourceMappingMap);
			}
		}
	}
	
	/**
	 * updateUserMasteCacheAfterFavourite
	 * @param cacheManager
	 * @param dsmId
	 * @param userId
	 * @param templateId
	 * @param unFavFlag
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  UserBean updateUserMasteCacheAfterFavourite(EhCacheCacheManager cacheManager, Long dsmId, Long userId, Long templateId, Boolean unFavFlag) {
		LOGGER.info("Executing " + classname + ".updateUserMasteCacheAfterFavourite");
		UserBean userBean = null;
		Cache cache = cacheManager.getCache("userCacheByDsmId");
		Map<Long, Map<Long, UserBean>> datasourceMappingMap = new ConcurrentHashMap<>();
		if(null != cache) {
			ValueWrapper valueWrapper =  cache.get(ServiceConstants.USER_CACHED_BY_DSMID);
			if(null != valueWrapper) {
				datasourceMappingMap = (Map<Long, Map<Long, UserBean>>) valueWrapper.get();
			}
			if(!datasourceMappingMap.isEmpty()) {
				Map<Long, UserBean> userMap = datasourceMappingMap.get(dsmId);
				if(!userMap.isEmpty()) {
					userBean = userMap.get(userId);
					if(null != userBean && null != templateId) {
						List<Long> favTemplateIds = new ArrayList<Long>();
						if(!CollectionUtils.isEmpty(userBean.getFavTemplateIds())){
							favTemplateIds = userBean.getFavTemplateIds();
						}
						if(!unFavFlag) {
							favTemplateIds.add(templateId);
						}else if(favTemplateIds.contains(templateId)){
							favTemplateIds.remove(templateId);
						}
						userBean.setFavTemplateIds(favTemplateIds);
					}
					userMap.put(userId, userBean);
					datasourceMappingMap.put(dsmId, userMap);
				}
			}
		}
		return userBean;
	}
}
