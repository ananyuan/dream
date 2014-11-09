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
	private String start;
	private String endTime;

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

	public int getType() {
		return ttype;
	}

	public void setType(int type) {
		this.ttype = type;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return endTime;
	}

	public void setEnd(String end) {
		this.endTime = end;
	}

}
