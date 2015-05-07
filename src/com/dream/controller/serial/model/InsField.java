package com.dream.controller.serial.model;

public class InsField {
	
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
	 * 输入类型  input/countdown/select/checkbox/image/onoff/刻度/descp
	 */
	private String itemtype = "";
	
	/**
	 * 关联字典
	 */
	private String reldict = "";
	
	/**
	 * 默认值
	 */
	private String defaultval = "";
	
	/**
	 * 排序
	 */
	private int sortnum = 0;
	
	private String insid = "";
	
	private String command = "";
	
	/**
	 * 附加样式
	 */
	private String cssplus = "";
	
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

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public String getReldict() {
		return reldict;
	}

	public void setReldict(String reldict) {
		this.reldict = reldict;
	}

	public String getDefaultval() {
		return defaultval;
	}

	public void setDefaultval(String defaultval) {
		this.defaultval = defaultval;
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

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
}
