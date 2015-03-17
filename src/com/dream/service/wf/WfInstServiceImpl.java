package com.dream.service.wf;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.wf.WfInstDao;
import com.dream.model.wf.WfInst;

public class WfInstServiceImpl implements WfInstService {

	private WfInstDao wfInstDao;
	
	
	@Override
	public int insert(WfInst wfInst) {
		return this.wfInstDao.insert(wfInst);
	}

	@Override
	public int update(WfInst wfInst) {
		return this.wfInstDao.update(wfInst);
	}

	@Override
	public int delete(int id) {
		return this.wfInstDao.delete(id);
	}

	@Override
	public List<WfInst> findWfInsts(Page<?> page) {
		return this.wfInstDao.findWfInsts(page);
	}

	@Override
	public WfInst findWfInst(int id) {
		return this.wfInstDao.findWfInst(id);
	}

	public WfInstDao getWfInstDao() {
		return wfInstDao;
	}

	public void setWfInstDao(WfInstDao wfInstDao) {
		this.wfInstDao = wfInstDao;
	}

}
