package com.dream.dao;

import java.util.List;

import com.dream.model.Article;
import com.dream.model.Config;

public interface ConfigDao {
	public int insert(Config config);

	public int update(Config config);

	public int delete(String id);

	public Article findConfig(String id);
	
	public List<Config> findConfigs();
}
