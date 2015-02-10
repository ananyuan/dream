package com.dream.model;

/**
 * 任务
 * 
 * @author anan
 * 
 */
public class Task {
	private int id = 0;
	private String title = "";
	private String descp = "";
	private int ttype = 1;
	private String start = "";
	private String endTime = "";

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

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}



	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getTtype() {
		return ttype;
	}

	public void setTtype(int ttype) {
		this.ttype = ttype;
	}


}
