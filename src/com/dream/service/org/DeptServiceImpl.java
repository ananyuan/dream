package com.dream.service.org;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.org.DeptDao;
import com.dream.model.org.Dept;

public class DeptServiceImpl implements DeptService {

	private DeptDao deptDao;
	
	
	public DeptDao getDeptDao() {
		return deptDao;
	}

	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
	}

	@Override
	public int insert(Dept dept) {
		return deptDao.insert(dept);
	}

	@Override
	public int update(Dept dept) {
		return deptDao.update(dept);
	}

	@Override
	public int delete(String code) {
		return deptDao.delete(code);
	}

	@Override
	public Dept findDept(String code) {
		return deptDao.findDept(code);
	}

	@Override
	public List<Dept> findDepts(Page page) {
		return deptDao.findDepts(page);
	}

}
