package com.dream.dao.wf;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.wf.WfBean;

public interface WfDao {
	public int insert(WfBean wfBean);

	public int update(WfBean wfBean);

	public int delete(String code);
	
	public List<WfBean> findWfs(Page<?> page);

	public WfBean findWf(String code);
}
