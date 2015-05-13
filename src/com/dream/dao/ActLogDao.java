package com.dream.dao;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.ActLog;

public interface ActLogDao {
	public int insert(ActLog actLog);

	public int update(ActLog actLog);

	public int delete(String id);

	public int countAll();

	public ActLog findActLog(String id);
	
	public List<ActLog> findActLogs(Page<?> page);
	
	public List<ActLog> findActLogsByType(String modelType, String actType);
}
