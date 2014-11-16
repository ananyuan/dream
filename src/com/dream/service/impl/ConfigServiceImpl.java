package com.dream.service.impl;

import java.util.List;

import com.dream.dao.ConfigDao;
import com.dream.model.Config;
import com.dream.service.ConfigService;

public class ConfigServiceImpl implements ConfigService {

	private ConfigDao configDao;
	
	

	@Override
	public List<Config> findConfigs() {
		return configDao.findConfigs();
	}
	

	public ConfigDao getConfigDao() {
		return configDao;
	}

	public void setConfigDao(ConfigDao configDao) {
		this.configDao = configDao;
	}


}
