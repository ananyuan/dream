package com.dream.model;

import java.util.List;

/**
 * 字典
 *
 */
public class Dict {
	
	public static final String CHILD = "childs";
	
	private String code = "";
	private String name = "";
	
	private List<DictEntry> childs;
	
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
	
	
	public List<DictEntry> getChilds() {
		return childs;
	}
	public void setChilds(List<DictEntry> childs) {
		this.childs = childs;
	}
	
}
