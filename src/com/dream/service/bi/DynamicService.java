package com.dream.service.bi;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.bi.Dynamic;

public interface DynamicService {
	public int insert(Dynamic dynamic);

	public int update(Dynamic dynamic);

	public int delete(String id);
	
	public List<Dynamic> findDynamics(Page<?> page);

	public Dynamic findDynamic(String id);
}
