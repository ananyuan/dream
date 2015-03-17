package com.dream.service.wf;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.wf.WfNodeInstDao;
import com.dream.model.wf.WfNodeInst;

public class WfNodeInstServiceImpl implements WfNodeInstService {

	private WfNodeInstDao wfNodeInstDao;
	
	
	@Override
	public int insert(WfNodeInst nodeInst) {
		return this.wfNodeInstDao.insert(nodeInst);
	}

	@Override
	public int update(WfNodeInst nodeInst) {
		return this.wfNodeInstDao.update(nodeInst);
	}

	@Override
	public int delete(int id) {
		return this.wfNodeInstDao.delete(id);
	}

	@Override
	public List<WfNodeInst> findWfNodeInsts(Page<?> page) {
		return this.wfNodeInstDao.findWfNodeInsts(page);
	}

	@Override
	public WfNodeInst findWfNodeInst(int id) {
		return this.wfNodeInstDao.findWfNodeInst(id);
	}

	public WfNodeInstDao getWfNodeInstDao() {
		return wfNodeInstDao;
	}

	public void setWfNodeInstDao(WfNodeInstDao wfNodeInstDao) {
		this.wfNodeInstDao = wfNodeInstDao;
	}

}
