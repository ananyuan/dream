package com.dream.service;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.DictEntry;

public interface DictEntryService {

	
	public int insert(DictEntry dictEntry);

	public int update(DictEntry dictEntry);

	public int delete(String id);
	
	public int deleteByDictId(String dictId);

	public DictEntry findEntry(String id);
	
	public List<DictEntry> findEntrys(Page page);
	
}
