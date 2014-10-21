package com.dream.model;

/**
 * 栏目
 * @author anan
 *
 */
public class Channel {
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getpCode() {
		return pCode;
	}
	public void setpCode(int pCode) {
		this.pCode = pCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private int code;
	private int pCode;
	private String name;
}
