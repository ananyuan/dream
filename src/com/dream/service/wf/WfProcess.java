package com.dream.service.wf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dream.model.org.User;
import com.dream.model.wf.WfDef;
import com.dream.model.wf.WfInst;
import com.dream.model.wf.WfNodeDef;
import com.dream.model.wf.WfNodeInst;
import com.dream.utils.DateUtils;
import com.dream.utils.SpringContextUtil;

public class WfProcess {
	
	private static Log log = LogFactory.getLog(WfProcess.class);
	
	private WfDef wfDef;
	
	private WfInst wfInst;	

	public WfProcess(WfInst wfInst) {
		this.wfInst = wfInst;
	}
	
	public WfProcess(int wfInstId) {
		WfInstService wfInstService = SpringContextUtil.getBean("wfInstService");
		this.wfInst = wfInstService.findWfInst(wfInstId);
	}
	
    /**
     * 
     * @return 流程定义
     */
    public WfDef getProcDef() {
        if (wfDef == null) {
        	wfDef = WfDefMgr.getWfDef(wfInst.getWfcode()); 
        }
        
        return wfDef;
    }
	
	public int getWfInstId() {
		return wfInst.getId();
	}
    
    
    /**
     * 创建起草点 工作流节点实例 起草新的 工作流 向 流程实例表，节点实例表，节点历史表 插入 构造的相关数据
     * @param startUsers 起草任务处理人   没设置用户时，取当前登录用户
     * @return 起始节点实例
     */
    public WfAct createStartWfNodeInst(User user) {
    	log.debug("create a start procees node");
    	
        // 插入新的节点实例
    	WfNodeInst nodeInst = new WfNodeInst();
        
        WfNodeDef startNodeDef = getProcDef().findStartNode();
        
        nodeInst.setWfid(wfInst.getId());
        nodeInst.setNodeid(startNodeDef.getId());
        nodeInst.setNodename(startNodeDef.getNname());
        nodeInst.setFromuser(user.getId());
        nodeInst.setFromusername(user.getUsername());
        nodeInst.setTotime(DateUtils.getDatetime());
        nodeInst.setTouser(user.getId());
        nodeInst.setTousername(user.getUsername());
        
        WfNodeInstService wfNodeInstService = SpringContextUtil.getBean("wfNodeInstService");
        
        wfNodeInstService.insert(nodeInst);
        
        log.debug("the new start node inst id is " + nodeInst.getId());
        
        WfAct wfAct = new WfAct(this, nodeInst);
        
        return wfAct;
    }

    
}
