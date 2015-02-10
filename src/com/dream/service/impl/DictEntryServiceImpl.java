package com.dream.service.impl;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.DictEntryDao;
import com.dream.model.DictEntry;
import com.dream.service.DictEntryService;

public class DictEntryServiceImpl implements DictEntryService {

	private DictEntryDao dictEntryDao;
	
	
	@Override
	public int insert(DictEntry dictEntry) {
		return dictEntryDao.insert(dictEntry);
	}

	@Override
	public int update(DictEntry dictEntry) {
		return dictEntryDao.update(dictEntry);
	}

	@Override
	public int delete(String id) {
		return dictEntryDao.delete(id);
	}

	@Override
	public DictEntry findEntry(String id) {
		return dictEntryDao.findEntry(id);
	}

	@Override
	public List<DictEntry> findEntrys(Page page) {
		return dictEntryDao.findEntrys(page);
	}

	public DictEntryDao getDictEntryDao() {
		return dictEntryDao;
	}

	public void setDictEntryDao(DictEntryDao dictEntryDao) {
		this.dictEntryDao = dictEntryDao;
	}

	@Override
	public int deleteByDictId(String dictId) {
		return dictEntryDao.deleteByDictId(dictId);
	}

}
