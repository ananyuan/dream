package com.dream.model;

import java.util.List;

public class DictEntry {
	
	private int id = 0;
	private String code = "";
	private String pcode = "";
	private String pname = "";
	private String name = "";
	private int esort = 0;
	private String dictid = "";
	private int dlevel = 0;
	
	private List<DictEntry> childs;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEsort() {
		return esort;
	}
	public void setEsort(int esort) {
		this.esort = esort;
	}
	public String getDictid() {
		return dictid;
	}
	public void setDictid(String dictid) {
		this.dictid = dictid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDlevel() {
		return dlevel;
	}
	public void setDlevel(int dlevel) {
		this.dlevel = dlevel;
	}
	public List<DictEntry> getChilds() {
		return childs;
	}
	public void setChilds(List<DictEntry> childs) {
		this.childs = childs;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	
	
	
}
