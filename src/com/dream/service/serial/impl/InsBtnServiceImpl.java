package com.dream.service.serial.impl;

import java.util.List;

import com.dream.base.Page;
import com.dream.controller.serial.model.InsBtn;
import com.dream.dao.serial.InsBtnDao;
import com.dream.service.serial.InsBtnService;

public class InsBtnServiceImpl implements InsBtnService {

	private InsBtnDao insBtnDao;
	
	@Override
	public int insert(InsBtn insBtn) {
		return this.insBtnDao.insert(insBtn);
	}

	@Override
	public int update(InsBtn insBtn) {
		return this.insBtnDao.update(insBtn);
	}

	@Override
	public int delete(String id) {
		return this.insBtnDao.delete(id);
	}

	@Override
	public InsBtn findBtn(String id) {
		return this.insBtnDao.findBtn(id);
	}

	@Override
	public List<InsBtn> findBtns(String insid) {
		return this.insBtnDao.findBtns(insid);
	}

	public InsBtnDao getInsBtnDao() {
		return insBtnDao;
	}

	public void setInsBtnDao(InsBtnDao insBtnDao) {
		this.insBtnDao = insBtnDao;
	}

	@Override
	public List<InsBtn> findBtnsByPage(Page<?> page) {
		return this.insBtnDao.findBtnsByPage(page);
	}

}
