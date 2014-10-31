package com.dream.model;

/**
 * 文章
 * @author anan
 *
 */
public class Article {
	private String id;
	private String title;
	private String content;
	private int chanId;
	private String atime;
	private String summary;
	private String localurl;
	
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
	public int getChanId() {
		return chanId;
	}
	public void setChanId(int chanId) {
		this.chanId = chanId;
	}
	public String getAtime() {
		return atime;
	}
	public void setAtime(String atime) {
		this.atime = atime;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getLocalurl() {
		return localurl;
	}
	public void setLocalurl(String localurl) {
		this.localurl = localurl;
	}

}
