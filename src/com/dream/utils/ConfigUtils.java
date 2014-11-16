package com.dream.utils;

import java.util.List;

import com.dream.base.Constant;
import com.dream.model.Config;
import com.dream.service.ConfigService;
import com.dream.start.CacheMgr;

public class ConfigUtils {
	
	public static String getConf(String confkey) {
		if (!CacheMgr.getInstance().existType(Constant.CACHE_TYPE_CONFIG)) {
			initConfig();
		}
		
		String confValue = (String) CacheMgr.getInstance().get(confkey, Constant.CACHE_TYPE_CONFIG);
		
		return confValue;
	}
	
    /**
     * 初始化 系统配置
     */
    private static void initConfig() {
    	
    	CacheMgr.getInstance().clear(Constant.CACHE_TYPE_CONFIG);
    	
    	ConfigService configService = SpringContextUtil.getBean("configService");
    	
    	List<Config> configs = configService.findConfigs();
    	
    	for (Config config: configs) {
    		CacheMgr.getInstance().set(config.getConfkey(), config.getConfvalue(), Constant.CACHE_TYPE_CONFIG);
    	}
    }
}
