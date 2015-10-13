package com.dream.utils;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * redis 缓存
 * @author anan
 * 
 */
public class RedisCache {
	/**缓存对象*/
	private static RedisCacheManager cacheManager;

	public void setCacheManager(RedisCacheManager cacheManager){
		RedisCache.cacheManager = cacheManager;
	}
	
	
	/**
	 * 获取指定缓存
	 * @param cacheType 
	 * @return
	 */
	public static Cache getRedisCache(String cacheType) {
		// 获取缓存管理器中的缓存配置名称
		return cacheManager.getCache(cacheType);
	}

	
	/**
	 * 
	 * @param cacheType
	 * @param key
	 * @param value
	 */
	public static void setRedisCache(String cacheType, String key, Object value) {
		getRedisCache(cacheType).put(key, value);
	}
	
	/**
	 * 
	 * @param cacheType
	 * @param key
	 * @return
	 */
	public static Object getRedisCache(String cacheType, String key) {
		ValueWrapper value  = getRedisCache(cacheType).get(key);
		
		if (null != value) {
			return value.get();
		}
		
		return null;
	}
	
	
	
	/** 不过期*/
	public static final String CACHE_TYPE_NEVER_EXPIRE = "NEVER_EXPIRE"; 
	
	/** 两小时 过期*/
	public static final String CACHE_TYPE_TWO_HOUR_EXPIRE = "TWO_HOUR_EXPIRE"; 
	
	/** 一小时 过期*/
	public static final String CACHE_TYPE_ONE_HOUR_EXPIRE = "ONE_HOUR_EXPIRE"; 
	
	/** 半小时 过期*/
	public static final String CACHE_TYPE_HALF_HOUR_EXPIRE = "HALF_HOUR_EXPIRE"; 
	
	/** 十分钟 过期*/
	public static final String CACHE_TYPE_TEN_MIN_EXPIRE = "TEN_MIN_EXPIRE"; 
	
	/** 五分钟过期 */
	public static final String CACHE_TYPE_FIVE_MIN_EXPIRE = "FIVE_MIN_EXPIRE"; 
	
	/** 两分钟 过期*/
	public static final String CACHE_TYPE_TWO_MIN_EXPIRE = "TWO_MIN_EXPIRE"; 
	
	/** 一分钟过期 */
	public static final String CACHE_TYPE_ONE_MIN_EXPIRE = "ONE_MIN_EXPIRE"; 
}
