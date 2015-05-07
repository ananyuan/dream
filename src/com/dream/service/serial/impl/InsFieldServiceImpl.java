package com.dream.service.serial.impl;

import java.util.List;

import com.dream.base.Page;
import com.dream.controller.serial.model.InsField;
import com.dream.dao.serial.InsFieldDao;
import com.dream.service.serial.InsFieldService;

public class InsFieldServiceImpl implements InsFieldService {

	private InsFieldDao insFieldDao;
	
	@Override
	public int insert(InsField insField) {
		return this.insFieldDao.insert(insField);
	}

	@Override
	public int update(InsField insField) {
		return this.insFieldDao.update(insField);
	}

	@Override
	public int delete(String id) {
		return this.insFieldDao.delete(id);
	}

	@Override
	public InsField findField(String id) {
		return this.insFieldDao.findField(id);
	}

	@Override
	public List<InsField> findFields(String insid) {
		return this.insFieldDao.findFields(insid);
	}

	public InsFieldDao getInsFieldDao() {
		return insFieldDao;
	}

	public void setInsFieldDao(InsFieldDao insFieldDao) {
		this.insFieldDao = insFieldDao;
	}

	@Override
	public List<InsField> findFieldsByPage(Page<?> page) {
		return this.insFieldDao.findFieldsByPage(page);
	}

}
