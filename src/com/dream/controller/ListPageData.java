package com.dream.controller;

import java.util.List;
import java.util.Map;

import com.dream.base.Page;

public class ListPageData {
	
	private Page page;
	
	private List<Map<String, Object>> rtnList;

	public ListPageData(Page page) {
		this.page = page;
	}
	
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<Map<String, Object>> getRtnList() {
		return rtnList;
	}

	public void setRtnList(List<Map<String, Object>> rtnList) {
		this.rtnList = rtnList;
	}
	
}
