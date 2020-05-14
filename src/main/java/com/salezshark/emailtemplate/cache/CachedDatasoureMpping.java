/*
 * package com.salezshark.emailtemplate.cache;
 * 
 * import java.io.IOException; import java.io.Serializable; import
 * java.util.LinkedHashSet; import java.util.LinkedList; import java.util.List;
 * import java.util.Properties; import java.util.Set;
 * 
 * import javax.annotation.PostConstruct;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.cache.Cache; import
 * org.springframework.cache.Cache.ValueWrapper; import
 * org.springframework.cache.CacheManager; import
 * org.springframework.context.annotation.Scope; import
 * org.springframework.stereotype.Component;
 * 
 * import com.fasterxml.jackson.core.JsonGenerationException; import
 * com.fasterxml.jackson.databind.JsonMappingException; import
 * com.fasterxml.jackson.databind.ObjectMapper; import
 * com.salezshark.emailtemplate.controller.EmailTemplateController; import
 * com.xav.web.bean.ActivityBean; import com.xav.web.bean.ActorBean; import
 * com.xav.web.bean.SocialRawData; import
 * com.xav.web.constants.XavBookConstants; import com.xav.web.dao.IActivityDao;
 * import com.xav.web.dao.ISocialHandlerDao; import
 * com.xav.web.model.SocialHandle; import com.xav.web.model.UserSession; import
 * com.xav.web.proxy.ActivityServiceProxy; import
 * com.xav.web.utility.CacheUtility; import com.xav.web.utility.IProfileUtility;
 * 
 * 
 *//**
	 * The Cache Manager
	 * 
	 * @author nibhash
	 * 
	 *//*
		 * @Component("cachedDatasoureMpping")
		 * 
		 * @Scope(value = "singleton") public class CachedDatasoureMpping implements
		 * Serializable {
		 * 
		 * private static final long serialVersionUID = -6403977959827009377L;
		 * 
		 * private static final Logger LOGGER =
		 * LoggerFactory.getLogger(CachedDatasoureMpping.class); private static final
		 * String classname = CachedDatasoureMpping.class.getName();
		 * 
		 * 
		 * @Autowired private CacheManager cacheManager;
		 * 
		 * private Cache cachePost;
		 * 
		 * @Autowired private Properties properties;
		 * 
		 * @Autowired private ISocialHandlerDao socialHandlerDao;
		 * 
		 * @Autowired private IActivityDao activityDao;
		 * 
		 * @Autowired private IProfileUtility profileUtility;
		 * 
		 * @Autowired private ActivityServiceProxy activityServiceProxy;
		 * 
		 * public CachedDatasoureMpping() {
		 * 
		 * }
		 * 
		 * @Override
		 * 
		 * @PostConstruct public void init() { if (this.cacheManager == null) { try {
		 * throw new Exception("Error Pre Init Cache"); } catch (final Exception e) {
		 * LOGGER.error("Error !!", e); } } cachePost =
		 * this.cacheManager.getCache("GetActivitiesCache"); if (cachePost != null) {
		 * for (final ActivityBean activityBean :
		 * this.activityDao.getActivitiesForCacheBuilt()) {
		 * cachePost.put(activityBean.getActivityID(), activityBean); } } else { try {
		 * throw new Exception("Error pre init Cache"); } catch (final Exception e) {
		 * LOGGER.error("Error !!", e); } }
		 * 
		 * } }
		 */