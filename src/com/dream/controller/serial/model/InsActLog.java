package com.dream.controller.serial.model;

public class InsActLog {
	private String id = "";
	
	/**
	 * 批次
	 */
	private String pici = "";
	

	/**
	 * 关联ID 
	 */
	private String userid = "";
	
	/**
	 * 操作类型  开始/停止/数据变化
	 */
	private String actcode = "";
	
	
	private String actname = "";
	
	/**
	 * 通道序号
	 */
	private String channelport = "";
	
	
	/**
	 * 抓取的时间
	 */
	private String logtime = "";
	
	
	private String command = "";
	
	private String result = "";
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPici() {
		return pici;
	}


	public void setPici(String pici) {
		this.pici = pici;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}



	public String getChannelport() {
		return channelport;
	}


	public void setChannelport(String channelport) {
		this.channelport = channelport;
	}


	public String getLogtime() {
		return logtime;
	}


	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getActname() {
		return actname;
	}


	public void setActname(String actname) {
		this.actname = actname;
	}


	public String getActcode() {
		return actcode;
	}


	public void setActcode(String actcode) {
		this.actcode = actcode;
	}


	public String getCommand() {
		return command;
	}


	public void setCommand(String command) {
		this.command = command;
	}


}
