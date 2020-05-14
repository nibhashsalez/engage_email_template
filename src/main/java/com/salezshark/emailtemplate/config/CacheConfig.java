package com.salezshark.emailtemplate.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class CacheConfig {
	@Bean 
	public EhCacheManagerFactoryBean cacheManager() {
		return new EhCacheManagerFactoryBean();
	}

	@Bean
	public EhCacheCacheManager ehCacheManager() {
		// testEhCache Configuration - create configuration of cache that previous required XML
		CacheConfiguration ehCacheConfig = new CacheConfiguration()
				.eternal(false)						// if true, timeouts are ignored
				.timeToIdleSeconds(2678400)			// (31 days) time since last accessed before item is marked for removal
				.timeToLiveSeconds(0)				// time since inserted before item is marked for removal
				.maxEntriesLocalHeap(100)			// total items that can be stored in cache
				.memoryStoreEvictionPolicy("LRU")	// eviction policy for when items exceed cache. LRU = Least Recently Used
				.name("userCacheByDsmId");

		Cache cache = new Cache(ehCacheConfig);

		cacheManager().getObject().addCache(cache);
		return new EhCacheCacheManager(cacheManager().getObject());
	}
}
