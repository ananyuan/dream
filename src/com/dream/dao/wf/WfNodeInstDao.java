package com.dream.dao.wf;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.wf.WfNodeInst;

public interface WfNodeInstDao {

	public int insert(WfNodeInst nodeInst);

	public int update(WfNodeInst nodeInst);

	public int delete(int id);
	
	public List<WfNodeInst> findWfNodeInsts(Page<?> page);

	public WfNodeInst findWfNodeInst(int id);
}
