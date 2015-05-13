package com.dream.controller.serial.model;

import java.util.List;

public class InsDef {

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
	 * 模板类型及路径
	 */
	private String model = "";
	
	/**
	 * 通道个数
	 */
	private int channum = 1;
	
	/**
	 * 值改变，动态发送的命令
	 */
	private String command = "";
	
	/** 验证命令 */
	private String validatereq = "";
	
	private String validateres = "";
	
	private List<InsField> insFields;
	
	
	private List<InsBtn> insBtns;
	

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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getChannum() {
		return channum;
	}

	public void setChannum(int channum) {
		this.channum = channum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<InsField> getInsFields() {
		return insFields;
	}

	public void setInsFields(List<InsField> insFields) {
		this.insFields = insFields;
	}

	public List<InsBtn> getInsBtns() {
		return insBtns;
	}

	public void setInsBtns(List<InsBtn> insBtns) {
		this.insBtns = insBtns;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getValidatereq() {
		return validatereq;
	}

	public void setValidatereq(String validatereq) {
		this.validatereq = validatereq;
	}

	public String getValidateres() {
		return validateres;
	}

	public void setValidateres(String validateres) {
		this.validateres = validateres;
	}

	
	
	
	
}
