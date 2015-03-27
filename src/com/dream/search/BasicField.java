package com.dream.search;

import java.util.Date;
import java.util.List;

/**
 * 建立索引的字段
 * 
 * @author anan
 * 
 */
public class BasicField {
	private String id = "";

	private String title = "";
	
	private String summery = "";

	private String content = "";
	
	private String url = "";

	private List<String> keywords;

	private Date last_modified;

	private Date create_time;

	public static final String DYNAMIC_PREFIX_STR = "_strfield";

	public static final String DYNAMIC_PREFIX_NUM = "_numfield";

	public static final String DYNAMIC_PREFIX_DATE = "_datefield";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public Date getLast_modified() {
		return last_modified;
	}

	public void setLast_modified(Date last_modified) {
		this.last_modified = last_modified;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSummery() {
		return summery;
	}

	public void setSummery(String summery) {
		this.summery = summery;
	}

}
