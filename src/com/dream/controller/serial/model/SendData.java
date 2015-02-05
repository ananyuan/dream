package com.dream.controller.serial.model;

import java.util.HashMap;

public class SendData {
	
	private String servObj = "serial";

	private String actionMethod = "";
	
	
	private HashMap<String, Object> dataObj;


	public String getActionMethod() {
		return actionMethod;
	}


	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}




	public String getServObj() {
		return servObj;
	}


	public void setServObj(String servObj) {
		this.servObj = servObj;
	}


	public HashMap<String, Object> getDataObj() {
		return dataObj;
	}


	public void setDataObj(HashMap<String, Object> dataObj) {
		this.dataObj = dataObj;
	}
	
	
}
