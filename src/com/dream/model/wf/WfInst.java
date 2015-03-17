package com.dream.model.wf;

public class WfInst {

	private int id = 0;
	
	private String wfcode = "";
	
	private int dataid = 0;
	
	private String btime = "";
	
	private String etime = "";
	
	private int running = 1;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWfcode() {
		return wfcode;
	}

	public void setWfcode(String wfcode) {
		this.wfcode = wfcode;
	}

	public int getDataid() {
		return dataid;
	}

	public void setDataid(int dataid) {
		this.dataid = dataid;
	}

	public String getBtime() {
		return btime;
	}

	public void setBtime(String btime) {
		this.btime = btime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public int getRunning() {
		return running;
	}

	public void setRunning(int running) {
		this.running = running;
	}
	
	
	
}
