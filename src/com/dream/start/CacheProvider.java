package com.dream.start;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheProvider {
	private CacheManager cacheManager = null;

	public CacheProvider() {
		cacheManager = CacheManager.create();
	}

	public boolean set(String type, String key, Object val) {
		return set(type, key, val, -1);
	}

	public boolean set(String type, String key, Object val, int expiry) {
		Cache cache = this.getCache(type);
		Element elem = new Element(key, val);
		if (0 < expiry) {
			elem.setTimeToLive(expiry);
		}
		cache.put(elem);
		return true;
	}

	public Object get(String type, String key) {
		if (!cacheManager.cacheExists(type)) {
			return null;
		}
		Cache cache = cacheManager.getCache(type);
		Element element = cache.get(key);
		if (element != null) {
			return element.getObjectValue();
		}
		return null;
	}

	public boolean delete(String type, String key) {
		if (cacheManager.cacheExists(type)) {
			return cacheManager.getCache(type).remove(key);
		}
		return false;
	}

	public boolean clear(String type) {
		if (cacheManager.cacheExists(type)) {
			cacheManager.removeCache(type);
			return true;
		}
		return false;
	}

	public boolean exists(String type, String key) {
		boolean exists = false;
		if (cacheManager.cacheExists(type)) {
			Cache cache = cacheManager.getCache(type);
			exists = cache.isKeyInCache(key);
		}
		return exists;
	}
	
	public boolean existType(String cacheType) {
		if (null != cacheManager.getCache(cacheType)) {
			return true;
		}
		
		return false;
	}
	

	private Cache getCache(String cacheType) {
		synchronized (cacheManager) {
			if (!cacheManager.cacheExists(cacheType)) {
				cacheManager.addCache(cacheType);
			}
		}
		return cacheManager.getCache(cacheType);
	}

	public void shutdown() {
		cacheManager.shutdown();
	}

	public int getSize(String type) {
		int count = 0;
		if (cacheManager.cacheExists(type)) {
			count = cacheManager.getCache(type).getSize();
		}
		return count;
	}

	public Object getCacheMgr() {
		return cacheManager;
	}
}
