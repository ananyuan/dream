package com.dream.model;

/**
 * 文章
 * @author anan
 *
 */
public class Article {
	private int id;
	private String title;
	private String content;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	private int chanId;
	private String atime;
}
