package com.dream.model;

import java.util.List;

/**
 * 字典
 *
 */
public class Menu {
	
	public static final String CHILD = "childs";
	
	private int id = 0;
	private String name = "";
	private String mtype = "LEAF";
	private String mclass = "";
	private int msort = 0;
	private String url = "";
	private int pid = 0;
	
	private int mlevel = 0;
	private String mpath = "";
	
	
	private List<Menu> childs;



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getMtype() {
		return mtype;
	}



	public void setMtype(String mtype) {
		this.mtype = mtype;
	}



	public String getMclass() {
		return mclass;
	}



	public void setMclass(String mclass) {
		this.mclass = mclass;
	}



	public int getMsort() {
		return msort;
	}



	public void setMsort(int msort) {
		this.msort = msort;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public List<Menu> getChilds() {
		return childs;
	}



	public void setChilds(List<Menu> childs) {
		this.childs = childs;
	}



	public int getPid() {
		return pid;
	}



	public void setPid(int pid) {
		this.pid = pid;
	}



	public int getMlevel() {
		return mlevel;
	}



	public void setMlevel(int mlevel) {
		this.mlevel = mlevel;
	}



	public String getMpath() {
		return mpath;
	}



	public void setMpath(String mpath) {
		this.mpath = mpath;
	}
	
}
