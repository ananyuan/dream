package com.dream.utils;

import java.util.List;

public class DataTableReturnObject {
	private long iTotalRecords;
	private long iTotalDisplayRecords;
	private String sEcho;
	private List aaData;
	
	public DataTableReturnObject(long totalRecords, long totalDisplayRecords, String echo, List d) {
		this.setiTotalRecords(totalRecords);
		this.setiTotalDisplayRecords(totalDisplayRecords);
		this.setsEcho(echo);
		this.setAaData(d);
	}

	public void setiTotalRecords(long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public long getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setAaData(List aaData) {
		this.aaData = aaData;
	}

	public List getAaData() {
		return aaData;
	}
}
