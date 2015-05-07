package com.dream.service.serial;

import java.util.List;

import com.dream.base.Page;
import com.dream.controller.serial.model.InsDef;

public interface InsDefService {
	public int insert(InsDef insDef);

	public int update(InsDef insDef);

	public int delete(String id);

	public InsDef findDef(String id);
	

	public List<InsDef> findDefs(Page<?> page);
}
