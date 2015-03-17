package com.dream.model.wf;

public class WfNodeInst {
	private int id = 0;
	
	private int wfid = 0;
	
	private String nodeid = "";
	
	private String nodename = "";
	
	private String totime = "";
	
	private String touser = "";
	
	private String tousername = "";
	
	private String donetime = "";
	
	private String doneuser = "";
	
	private String doneusername = "";
	
	private String fromuser = "";
	
	private String fromusername = "";
	
	private int running = 1;

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

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getTotime() {
		return totime;
	}

	public void setTotime(String totime) {
		this.totime = totime;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTousername() {
		return tousername;
	}

	public void setTousername(String tousername) {
		this.tousername = tousername;
	}

	public String getDonetime() {
		return donetime;
	}

	public void setDonetime(String donetime) {
		this.donetime = donetime;
	}

	public String getDoneuser() {
		return doneuser;
	}

	public void setDoneuser(String doneuser) {
		this.doneuser = doneuser;
	}

	public String getDoneusername() {
		return doneusername;
	}

	public void setDoneusername(String doneusername) {
		this.doneusername = doneusername;
	}

	public String getFromuser() {
		return fromuser;
	}

	public void setFromuser(String fromuser) {
		this.fromuser = fromuser;
	}

	public String getFromusername() {
		return fromusername;
	}

	public void setFromusername(String fromusername) {
		this.fromusername = fromusername;
	}

	public int getRunning() {
		return running;
	}

	public void setRunning(int running) {
		this.running = running;
	}
	
	
	
}
