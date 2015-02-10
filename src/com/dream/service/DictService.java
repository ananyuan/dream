package com.dream.service;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.Dict;

public interface DictService {
	public int insert(Dict dict);

	public int update(Dict dict);

	public int delete(String code);

	public Dict findDict(String code);
	
	public List<Dict> findDicts(Page page);
}
