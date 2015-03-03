package com.dream.dao.org;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.org.Dept;

public interface DeptDao {
	public int insert(Dept dept);

	public int update(Dept dept);

	public int delete(String code);
	
	public List<Dept> findDepts(Page<?> page);

	public Dept findDept(String code);
}
