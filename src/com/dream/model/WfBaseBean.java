package com.dream.model;

/**
 * 需要挂载流程的model类需要继承这个
 * @author anan
 *
 */
public class WfBaseBean {

	private int id = 0;
	
	private int wfid = 0;
	
	private int isrun = 1;
	
	private String curuser = "";
	
	private String curnode = "";

	private String vmodel = "";
	
	private int niid = 0;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWfid() {
		return wfid;
	}

	public void setWfid(int wfid) {
		this.wfid = wfid;
	}

	public int getIsrun() {
		return isrun;
	}

	public void setIsrun(int isrun) {
		this.isrun = isrun;
	}

	public String getCuruser() {
		return curuser;
	}

	public void setCuruser(String curuser) {
		this.curuser = curuser;
	}

	public String getCurnode() {
		return curnode;
	}

	public void setCurnode(String curnode) {
		this.curnode = curnode;
	}

	public String getVmodel() {
		return vmodel;
	}

	public void setVmodel(String vmodel) {
		this.vmodel = vmodel;
	}

	public int getNiid() {
		return niid;
	}

	public void setNiid(int niid) {
		this.niid = niid;
	}
	
}
