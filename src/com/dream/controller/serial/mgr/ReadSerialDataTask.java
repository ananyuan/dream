package com.dream.controller.serial.mgr;

import com.dream.utils.ThreadTask;

public class ReadSerialDataTask extends ThreadTask {

	private String portName = "";
	
	private String readData = "";
	
	public ReadSerialDataTask(String portName, String readData) {
		this.portName = portName;
		
		this.readData = readData;
	}
	
	
	@Override
	public void execute() {
		
		
	}

}
