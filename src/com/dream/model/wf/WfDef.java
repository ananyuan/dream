package com.dream.model.wf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dream.base.Constant;
import com.dream.base.Page;
import com.dream.service.wf.LineService;
import com.dream.service.wf.NodeService;
import com.dream.service.wf.WfService;
import com.dream.utils.SpringContextUtil;

/**
 * 
 *
 */
public class WfDef {
	
	private static WfService wfService = SpringContextUtil.getBean("wfService");
	
	private static NodeService nodeService = SpringContextUtil.getBean("nodeService");
	
	private static LineService lineService = SpringContextUtil.getBean("lineService");
	
	private String modelCode = "";
	
	private String wfname = "";
	
	
	private Map<String, WfNodeDef> nodeDefMap = new HashMap<String, WfNodeDef>();

	private List<WfLineDef> lineDefList = new ArrayList<WfLineDef>();
	
	
	public WfDef(String modelCode) {
		this.setModelCode(modelCode);
		
		initWf();
	}
	
	/**
	 * 初始化流程定义信息
	 */
	private void initWf() {
		loadWf();
		
		loadNodes();
		
		loadLines();
	}


	private void loadLines() {
		Page page = new Page();
		page.setPageSize(Constant.QUERY_COUNT_MAX);
		page.put("wfcode", this.modelCode);
		
		List<Line> lines = lineService.findLines(page);
		
		for (Line line: lines) {
			WfLineDef lineDef = new WfLineDef(line);
			
			lineDefList.add(lineDef);
		}
		
	}

	/**
	 * 加载节点定义信息
	 */
	private void loadNodes() {
		Page page = new Page();
		page.setPageSize(Constant.QUERY_COUNT_MAX);
		page.put("wfcode", this.modelCode);
		
		List<Node> nodes = nodeService.findNodes(page);
		
		for (Node node: nodes) {
			WfNodeDef nodeDef = new WfNodeDef(node);
			
			nodeDefMap.put(node.getId(), nodeDef);
		}
		
	}

	/**
	 * 加载定义信息
	 */
	private void loadWf() {
		WfBean wfBean = wfService.findWf(this.modelCode);
		this.wfname = wfBean.getName();
	}

	/**
	 * @param code 节点CODE
	 * @return 节点定义
	 */
	public WfNodeDef findNode(String code) {
		return nodeDefMap.get(code);
	}
	
	/**
	 * @return 找到工作流的起草点
	 */
	public WfNodeDef findStartNode() {
		Object[] aNodeDefs = nodeDefMap.values().toArray();
		for (int i = 0; i < aNodeDefs.length; i++) {
			WfNodeDef node = (WfNodeDef) aNodeDefs[i];
			if (node.getStartNode() == Constant.WF_NODE_START) { //TODO
				return node;
			}
		}
		
		return null;
	}

	/**
	 * 
	 */
	public WfLineDef findLineDef(String srcNode, String tarNode) {
		for (WfLineDef lineDef : lineDefList) {
			if (lineDef.getSrcNode().equals(srcNode)
					&& lineDef.getTarNode().equals(tarNode)) {
				return lineDef;
			}
			
			if (lineDef.getTarNode().equals(srcNode)
					&& lineDef.getSrcNode().equals(tarNode) ) { //TODO , can back
				return lineDef;
			}
		}
		
		return null;
	}

	/**
	 * 
	 */
	public List<WfLineDef> findLines(String srcNode) {
		List<WfLineDef> rtnLines = new ArrayList<WfLineDef>();

		for (WfLineDef lineDef : lineDefList) {
			if (lineDef.getSrcNode().equals(srcNode)) {
				rtnLines.add(lineDef);
			}
		}
		
		return rtnLines;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getWfname() {
		return wfname;
	}

	public void setWfname(String wfname) {
		this.wfname = wfname;
	}
	
}
