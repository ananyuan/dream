package com.dream.controller.serial.model;

public class InsBtn {

	/**
	 * 主键
	 */
	private String id = "";

	/**
	 * 编码
	 */
	private String code = "";
	
	/**
	 * 名称
	 */
	private String name = "";
	
	/**
	 * #FIELD# / 0X01 ... 
	 */
	private String command = "";
	
	/**
	 * 按钮附加样式
	 */
	private String cssplus = "";

	/**
	 * 排序
	 */
	private int sortnum = 0;
	
	private String insid = "";
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getCssplus() {
		return cssplus;
	}

	public void setCssplus(String cssplus) {
		this.cssplus = cssplus;
	}

	public int getSortnum() {
		return sortnum;
	}

	public void setSortnum(int sortnum) {
		this.sortnum = sortnum;
	}

	public String getInsid() {
		return insid;
	}

	public void setInsid(String insid) {
		this.insid = insid;
	}
	
}
