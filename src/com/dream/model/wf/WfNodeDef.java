package com.dream.model.wf;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class WfNodeDef {

	public WfNodeDef(Node node) {
		JSONObject nodeJsonObj =  (JSONObject) JSONSerializer.toJSON(node.getJsonstr());
		
    	String ndesc = nodeJsonObj.getString("ndesc");
    	String nname = nodeJsonObj.getString("nname");
    	
    	
    	this.setId(node.getId());
    	this.setWfcode(node.getWfcode());
		this.setNdesc(ndesc);
		this.setNname(nname);
		try {
			String startNode = nodeJsonObj.getString("startNode");
			if (null != startNode) {
				this.setStartNode(Integer.parseInt(startNode));	
			}
		} catch (Exception e) {
			
		}
	}

	
	
	private String id = "";
	
	private String wfcode = "";
	
	private String ndesc = "";
	
	private String nname = "";
	
	private int startNode = 0;

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

	public String getNdesc() {
		return ndesc;
	}

	public void setNdesc(String ndesc) {
		this.ndesc = ndesc;
	}

	public String getNname() {
		return nname;
	}

	public void setNname(String nname) {
		this.nname = nname;
	}

	public int getStartNode() {
		return startNode;
	}

	public void setStartNode(int startNode) {
		this.startNode = startNode;
	}
	
	
	
}
