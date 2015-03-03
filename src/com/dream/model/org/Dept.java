package com.dream.model.org;


public class Dept {

	private String code = "";

	private String pcode = "";

	private String name = "";
	
	private String pname = "";

	private String dpath = "";

	private int dlevel = 1;

	private int dsort = 1;

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

	public String getDpath() {
		return dpath;
	}

	public void setDpath(String dpath) {
		this.dpath = dpath;
	}

	public int getDlevel() {
		return dlevel;
	}

	public void setDlevel(int dlevel) {
		this.dlevel = dlevel;
	}

	public int getDsort() {
		return dsort;
	}

	public void setDsort(int dsort) {
		this.dsort = dsort;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}



}
