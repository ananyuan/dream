package com.dream.dao.serial;

import java.util.List;

import com.dream.base.Page;
import com.dream.controller.serial.model.InsField;

public interface InsFieldDao {
	public int insert(InsField insField);

	public int update(InsField insField);

	public int delete(String id);

	public InsField findField(String id);
	
	public List<InsField> findFields(String insid);
	
	public List<InsField> findFieldsByPage(Page<?> page);
}
