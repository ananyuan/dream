package com.dream.service.wf;

import com.dream.model.WfBaseBean;
import com.dream.model.org.User;
import com.dream.model.wf.WfDef;
import com.dream.model.wf.WfInst;
import com.dream.utils.DateUtils;
import com.dream.utils.SpringContextUtil;

public class WfMgr {
	
	private static WfInstService wfInstService = SpringContextUtil.getBean("wfInstService");
	
	/**
	 * 
	 * @param data
	 * @return 流程的ID
	 */
	public static WfAct startProcess(WfBaseBean data, String title) {
		//获取流程定义
		WfDef wfDef  =WfDefMgr.getWfDef(data.getClass().getName());
		
		//添加一条wfInst
		WfInst wfInst = new WfInst();
		wfInst.setBtime(DateUtils.getDatetime());
		wfInst.setDataid(data.getId());
		wfInst.setWfcode(wfDef.getModelCode());
		
		wfInstService.insert(wfInst);
		
		
		//添加一条 起点的节点实例
		WfProcess process = new WfProcess(wfInst);
		WfAct wfAct = process.createStartWfNodeInst(new User());  //TODO
		
		wfAct.sendTodo(data, title);
		
		
		return wfAct;
	}
	
	
}
