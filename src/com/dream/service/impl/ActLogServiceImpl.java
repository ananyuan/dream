package com.dream.service.impl;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.ActLogDao;
import com.dream.model.ActLog;
import com.dream.service.ActLogService;

public class ActLogServiceImpl implements ActLogService {

	private ActLogDao actLogDao;
	
	@Override
	public int insert(ActLog actLog) {
		return actLogDao.insert(actLog);
	}

	@Override
	public int update(ActLog actLog) {
		return actLogDao.update(actLog);
	}

	@Override
	public int delete(String id) {
		return actLogDao.delete(id);
	}

	@Override
	public int countAll() {
		return actLogDao.countAll();
	}

	@Override
	public ActLog findActLog(String id) {
		return actLogDao.findActLog(id);
	}

	@Override
	public List<ActLog> findActLogsByType(String modelType, String actType) {
		return actLogDao.findActLogsByType(modelType, actType);
	}

	public ActLogDao getActLogDao() {
		return actLogDao;
	}

	public void setActLogDao(ActLogDao actLogDao) {
		this.actLogDao = actLogDao;
	}

	@Override
	public List<ActLog> findActLogs(Page<?> page) {
		return this.actLogDao.findActLogs(page);
	}

}
