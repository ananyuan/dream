package com.dream.controller.serial.mgr;

import java.util.HashMap;

import com.dream.base.Constant;
import com.dream.base.Context;
import com.dream.model.sicker.SickerLog;
import com.dream.service.serial.SickerLogService;
import com.dream.utils.DateUtils;
import com.dream.utils.SpringContextUtil;
import com.dream.utils.ThreadTask;

public class ReadSerialDataTask extends ThreadTask {
	
	private static SickerLogService sickerLogService = SpringContextUtil.getBean("sickerLogService");
	

	private String portName = "";
	
	private String readData = "";
	
	public ReadSerialDataTask(String portName, String readData) {
		this.portName = portName;
		
		this.readData = readData;
	}
	
	
	@Override
	public void execute() {
		HashMap<String, Object> conObj = Context.getContextObj(Constant.CONTEXT_KEY_SERIAL);
		
		
		SickerLog sickerlog = new SickerLog();
		
		String pici = "";
		if (null != conObj.get("pici")) {
			pici = (String)conObj.get("pici");
		}
		
		String userid = "";
		if (null != conObj.get("userid")) {
			userid = (String)conObj.get("userid");
		}
		
		sickerlog.setRecordid(pici);
		sickerlog.setUserid(userid);
		
		
		sickerlog.setXvalue(DateUtils.getDatetime());
		sickerlog.setYvalue(readData);   //TODO
		
		sickerLogService.insert(sickerlog);
	}

}
