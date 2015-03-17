package com.dream.model;

public class Todo {
	private int id = 0;
	private String title = "";
	private String userid = "";
	private String fromuser = "";
	private String url = "";
	private String tdtime = "";
	private String tdname = "";
	private int tdtype = 1;
	private int wfnodeinstid = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFromuser() {
		return fromuser;
	}
	public void setFromuser(String fromuser) {
		this.fromuser = fromuser;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTdtime() {
		return tdtime;
	}
	public void setTdtime(String tdtime) {
		this.tdtime = tdtime;
	}
	public String getTdname() {
		return tdname;
	}
	public void setTdname(String tdname) {
		this.tdname = tdname;
	}
	public int getTdtype() {
		return tdtype;
	}
	public void setTdtype(int tdtype) {
		this.tdtype = tdtype;
	}
	public int getWfnodeinstid() {
		return wfnodeinstid;
	}
	public void setWfnodeinstid(int wfnodeinstid) {
		this.wfnodeinstid = wfnodeinstid;
	}
	
	
	
	
}
