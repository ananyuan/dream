package com.dream.service.wf;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.wf.WfDao;
import com.dream.model.wf.WfBean;

public class WfServiceImpl implements WfService {

	private WfDao wfDao;
	
	public WfDao getWfDao() {
		return wfDao;
	}

	public void setWfDao(WfDao wfDao) {
		this.wfDao = wfDao;
	}

	@Override
	public int insert(WfBean wfBean) {
		return this.wfDao.insert(wfBean);
	}

	@Override
	public int update(WfBean wfBean) {
		return this.wfDao.update(wfBean);
	}

	@Override
	public int delete(String code) {
		return this.wfDao.delete(code);
	}

	@Override
	public List<WfBean> findWfs(Page<?> page) {
		return this.wfDao.findWfs(page);
	}

	@Override
	public WfBean findWf(String code) {
		return this.wfDao.findWf(code);
	}


}
