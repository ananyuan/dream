package com.dream.model.org;

/**
 * 用户表
 */
public class User {

	private String id = "";
	private String loginname = "";
	private String username = "";
	private String password = "";
	private String sex = "0";
	private String email = "";
	private int age = 0;
	
	private int usort = 0;
	
	private String deptcode = "";
	
	private String deptname = "";
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getUsort() {
		return usort;
	}
	public void setUsort(int usort) {
		this.usort = usort;
	}
	public String getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
}