package com.dream.start;


public class CacheMgr {

    private static CacheMgr cacheManager = null;
    
    private static CacheProvider cacheProvider = null;

    public static CacheMgr getInstance() {
        if (cacheManager == null) {
            cacheManager = new CacheMgr();
            cacheProvider = new CacheProvider();
        }
        return cacheManager;
    }

    public static void shutdown() {
        cacheProvider.shutdown();
    }


    public void clearCache(String cacheType) {
        cacheProvider.clear(cacheType);
    }


    public Object get(String key, String cacheType) {
        return cacheProvider.get(cacheType, key);
    }


    public void set(String key, Object object, String cacheType) {
        cacheProvider.set(cacheType, key, object);
    }


    public void remove(String key, String cacheType) {
        cacheProvider.delete(cacheType, key);
    }
    
    public void clear(String cacheType) {
        cacheProvider.clear(cacheType);
    }

    
    public int getSize(String cacheType) {
        return cacheProvider.getSize(cacheType);
    }

    public boolean existType(String cacheType) {
        return cacheProvider.existType(cacheType);
    }
}
