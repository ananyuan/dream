package com.dream.model.wf;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


/**
 * 线定义
 *
 */
public class WfLineDef {

	/**
	 * 
	 * @param line 线定义
	 */
	public WfLineDef(Line line) {
		JSONObject lineJsonObj =  (JSONObject) JSONSerializer.toJSON(line.getJsonstr());
    	String label = lineJsonObj.getString("label");
    	
    	String source = lineJsonObj.getString("source");
    	String target = lineJsonObj.getString("target");
		
    	this.setId(line.getId());
    	this.setWfcode(line.getWfcode());
		this.setLabel(label);
		this.setSrcNode(source);
		this.setTarNode(target);
	}
	
	private String id = "";
	
	private String wfcode = "";
	
	private String label = "";
	
	private String srcNode = "";
	
	private String tarNode = "";
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getWfcode() {
		return wfcode;
	}


	public void setWfcode(String wfcode) {
		this.wfcode = wfcode;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getSrcNode() {
		return srcNode;
	}


	public void setSrcNode(String srcNode) {
		this.srcNode = srcNode;
	}


	public String getTarNode() {
		return tarNode;
	}


	public void setTarNode(String tarNode) {
		this.tarNode = tarNode;
	}




	
	
	
}
