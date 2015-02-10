package com.dream.dao;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.DictEntry;

public interface DictEntryDao {
	public int insert(DictEntry dictEntry);

	public int update(DictEntry dictEntry);

	public int delete(String id);
	
	public int deleteByDictId(String dictId);
	
	public List<DictEntry> findEntrys(Page<?> page);

	public DictEntry findEntry(String code);
}
