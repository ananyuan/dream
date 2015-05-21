package com.dream.model;


/**
 * 评论
 * @author anan
 *
 */
public class Comment {
	private int id = 0;
	private int pid = 0;
	private int depth = 0;
	private String dataid = "";
	private String ucode = "";
	private String uname = "";
	private String ip = "";
	private String content = "";
	private String atime = "";
	
	private String certval = "";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getDataid() {
		return dataid;
	}
	public void setDataid(String dataid) {
		this.dataid = dataid;
	}
	public String getUcode() {
		return ucode;
	}
	public void setUcode(String ucode) {
		this.ucode = ucode;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAtime() {
		return atime;
	}
	public void setAtime(String atime) {
		this.atime = atime;
	}
	public String getCertval() {
		return certval;
	}
	public void setCertval(String certval) {
		this.certval = certval;
	}
	
	
	
}
