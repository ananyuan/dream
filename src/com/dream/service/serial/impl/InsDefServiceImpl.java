package com.dream.service.serial.impl;

import java.util.List;

import com.dream.base.Page;
import com.dream.controller.serial.model.InsDef;
import com.dream.dao.serial.InsDefDao;
import com.dream.service.serial.InsDefService;

public class InsDefServiceImpl implements InsDefService {

	private InsDefDao insDefDao;
	
	@Override
	public int insert(InsDef insDef) {
		return this.insDefDao.insert(insDef);
	}

	@Override
	public int update(InsDef insDef) {
		return this.insDefDao.update(insDef);
	}

	@Override
	public int delete(String id) {
		return this.insDefDao.delete(id);
	}

	@Override
	public InsDef findDef(String id) {
		return this.insDefDao.findDef(id);
	}

	@Override
	public List<InsDef> findDefs(Page<?> page) {
		return this.insDefDao.findDefs(page);
	}

	public InsDefDao getInsDefDao() {
		return insDefDao;
	}

	public void setInsDefDao(InsDefDao insDefDao) {
		this.insDefDao = insDefDao;
	}

}
