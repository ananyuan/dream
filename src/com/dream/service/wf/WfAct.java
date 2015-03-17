package com.dream.service.wf;

import java.util.ArrayList;
import java.util.List;

import com.dream.base.Constant;
import com.dream.base.Page;
import com.dream.model.Todo;
import com.dream.model.WfBaseBean;
import com.dream.model.org.User;
import com.dream.model.wf.StepBean;
import com.dream.model.wf.WfLineDef;
import com.dream.model.wf.WfNodeDef;
import com.dream.model.wf.WfNodeInst;
import com.dream.service.TodoService;
import com.dream.utils.DateUtils;
import com.dream.utils.SpringContextUtil;

public class WfAct {

	private static WfNodeInstService wfNodeInstService = SpringContextUtil.getBean("wfNodeInstService");
	
	private WfProcess process;
	
	private WfNodeInst wfNodeInst;
	
	public WfAct(WfProcess process, WfNodeInst nodeInst) {
		this.process = process;
		
		this.wfNodeInst = nodeInst;
	}
	
	public WfAct(int niid) {
		this.wfNodeInst = wfNodeInstService.findWfNodeInst(niid);
	}
	
	public WfAct(WfNodeInst wfNodeInst) {
		this.wfNodeInst = wfNodeInst;
	}
	
	public WfProcess getProcess() {
		if (null == process) {
			process = new WfProcess(this.wfNodeInst.getWfid());
		}
		
		return process;
	}

	public void setProcess(WfProcess process) {
		this.process = process;
	}
	
	/**
	 * 
	 * @param wfBaseBean 节点实例
	 */
	public void sendTodo(WfBaseBean wfBaseBean, String title) {
		Todo todo = new Todo();
		
		todo.setTitle(title);
		todo.setUrl("/" + wfBaseBean.getVmodel() + "/edit/" + wfBaseBean.getId() + "/" + this.wfNodeInst.getId());
		todo.setTdtime(DateUtils.getDatetime());
		todo.setUserid(this.wfNodeInst.getTouser());
		todo.setFromuser(this.wfNodeInst.getFromuser());
		todo.setWfnodeinstid(this.wfNodeInst.getId());
		
		TodoService todoService = SpringContextUtil.getBean("todoService");
		
		todoService.insert(todo);
	}
	
	/**
	 * 
	 * @param nextNode
	 * @param toUser
	 * @param doUser
	 * @return
	 */
	public WfAct toNext(String nextNode, User toUser, User doUser) {
		WfNodeDef wfNodeDef = this.getProcess().getProcDef().findNode(nextNode);
		
		WfNodeInst nodeInst = new WfNodeInst();
		
        nodeInst.setWfid(this.getProcess().getWfInstId());
        nodeInst.setNodeid(nextNode);
        nodeInst.setNodename(wfNodeDef.getNname());
        nodeInst.setFromuser(doUser.getId());
        nodeInst.setFromusername(doUser.getUsername());
        nodeInst.setTotime(DateUtils.getDatetime());
        nodeInst.setTouser(toUser.getId());
        nodeInst.setTousername(toUser.getUsername());
		
        wfNodeInstService.insert(nodeInst);
        
        this.finish(doUser);
        
        WfAct newAct = new WfAct(nodeInst);
        
        return newAct;
	}
	
	/**
	 * 完成节点 消除待办
	 */
	public void finish(User doUser) {
		//节点完成
		wfNodeInst.setDonetime(DateUtils.getDatetime());
		wfNodeInst.setDoneuser(doUser.getId());
		wfNodeInst.setDoneusername(doUser.getUsername());
		wfNodeInst.setRunning(Constant.NO);
		
		wfNodeInstService.update(wfNodeInst);
		
		TodoService todoService = SpringContextUtil.getBean("todoService");
		Page page = new Page();
		page.setPageSize(Constant.QUERY_COUNT_MAX);
		page.put("tdtype", Constant.YES);  //待办
		page.put("wfnodeinstid", wfNodeInst.getId());  //节点实例ID
		
		List<Todo> todos = todoService.findTodos(page);
		for (Todo todo: todos) {
			todo.setTdtype(Constant.NO);
			
			todoService.update(todo);
		}
	}
	
	
	/**
	 * 
	 * @return 下一步能走的点
	 */
	public List<StepBean> getNextSteps() {
		List<StepBean> steps = new ArrayList<StepBean>();
		
		List<WfLineDef> lines = this.getProcess().getProcDef().findLines(this.wfNodeInst.getNodeid());
		
		for (WfLineDef line: lines) {
			WfNodeDef nodeDef = this.getProcess().getProcDef().findNode(line.getTarNode());
			
			StepBean step = new StepBean();
			step.setNodeCode(nodeDef.getId());
			step.setNodeName(nodeDef.getNname());
			
			steps.add(step);
		}
		
		return steps;
	}
	
}
