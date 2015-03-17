package com.dream.dao.wf;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.wf.WfInst;

public interface WfInstDao {
	public int insert(WfInst wfInst);

	public int update(WfInst wfInst);

	public int delete(int id);
	
	public List<WfInst> findWfInsts(Page<?> page);

	public WfInst findWfInst(int id);
}
