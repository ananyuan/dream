package com.dream.controller.serial.model;

public class InsLog {
	
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
	 * 数据类型  电流强度/XXX
	 */
	private String datatype = "";
	
	/**
	 * 通道序号
	 */
	private String channelport = "";
	
	
	/**
	 * 抓取的值
	 */
	private String logvalue = "";
	
	/**
	 * 抓取的时间
	 */
	private String logtime = "";

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

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}


	public String getLogvalue() {
		return logvalue;
	}

	public void setLogvalue(String logvalue) {
		this.logvalue = logvalue;
	}

	public String getLogtime() {
		return logtime;
	}

	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}

	public String getChannelport() {
		return channelport;
	}

	public void setChannelport(String channelport) {
		this.channelport = channelport;
	}
	
	
	
}
