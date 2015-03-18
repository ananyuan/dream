package com.dream.service.bi;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.bi.DynamicDao;
import com.dream.model.bi.Dynamic;
import com.dream.model.bi.Vacation;

public class DynamicServiceImpl implements DynamicService {

	private DynamicDao dynamicDao;
	
	@Override
	public int insert(Dynamic dynamic) {
		return dynamicDao.insert(dynamic);
	}

	@Override
	public int update(Dynamic dynamic) {
		return dynamicDao.update(dynamic);
	}

	@Override
	public int delete(String id) {
		return dynamicDao.delete(id);
	}

	@Override
	public List<Dynamic> findDynamics(Page<?> page) {
		return dynamicDao.findDynamics(page);
	}

	@Override
	public Dynamic findDynamic(String id) {
		return dynamicDao.findDynamic(id);
	}

	public DynamicDao getDynamicDao() {
		return dynamicDao;
	}

	public void setDynamicDao(DynamicDao dynamicDao) {
		this.dynamicDao = dynamicDao;
	}

}
