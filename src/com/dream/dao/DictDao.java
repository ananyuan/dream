package com.dream.dao;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.Dict;

/**
 * 
 * @author anan
 *
 */
public interface DictDao {
	public int insert(Dict dict);

	public int update(Dict dict);

	public int delete(String code);
	
	public List<Dict> findDicts(Page<?> page);

	public Dict findDict(String code);
}
