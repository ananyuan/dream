package com.dream.service.impl;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.DictDao;
import com.dream.model.Dict;
import com.dream.model.Task;
import com.dream.service.DictService;

public class DictServiceImpl implements DictService {

	private DictDao dictDao;
	
	@Override
	public int insert(Dict dict) {
		
		return this.dictDao.insert(dict);
	}

	@Override
	public int update(Dict dict) {
		return this.dictDao.update(dict);
	}

	@Override
	public int delete(String code) {
		return this.dictDao.delete(code);
	}

	@Override
	public Dict findDict(String code) {
		return this.dictDao.findDict(code);
	}

	@Override
	public List<Dict> findDicts(Page page) {
		return this.dictDao.findDicts(page);
	}

	public DictDao getDictDao() {
		return dictDao;
	}

	public void setDictDao(DictDao dictDao) {
		this.dictDao = dictDao;
	}

}
