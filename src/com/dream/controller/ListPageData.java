package com.dream.controller;

import java.util.List;

import com.dream.base.Page;

public class ListPageData {
	
	private Page page;
	
	private List rtnList;

	public ListPageData(Page page) {
		this.page = page;
	}
	
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List getRtnList() {
		return rtnList;
	}

	public void setRtnList(List rtnList) {
		this.rtnList = rtnList;
	}
	
}
