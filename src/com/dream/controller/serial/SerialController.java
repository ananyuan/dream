package com.dream.controller.serial;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.controller.serial.mgr.SerialPortMgr;
import com.dream.controller.serial.mgr.SerialPorter;
import com.dream.controller.serial.model.SendData;
import com.dream.controller.serial.model.SerialParam;

/**
 * 
 * @author anan
 *
 */

@Controller
@RequestMapping("/serial")
public class SerialController {

	
    @RequestMapping(value="/listPort", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getPorts() {
    	HashMap<String, Object> rtnMap = new HashMap<String, Object>();
    	
    	StringBuffer rtnStr = new StringBuffer();
    	
    	Set<String> ports = SerialPortMgr.getSerailPorts();
    	
    	Iterator<String> iter = ports.iterator();
    	while (iter.hasNext()) {
    		String name = iter.next();
    		
    		rtnStr.append(name).append(",");
    	}
    	
    	if (rtnStr.length() > 0) {
    		rtnStr.setLength(rtnStr.length() - 1);
    	}
    	
    	rtnMap.put("data", rtnStr.toString());
    	
    	return rtnMap;
	}
	
	@RequestMapping(value="/sendData", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> sendData(@RequestBody SendData sendData, HttpServletRequest request) {
    	Map<String, Object> rtnMap = new HashMap<String, Object>();
    	
    	String action = sendData.getActionMethod();
    	
    	HashMap<String, Object> dataObj = sendData.getDataObj();
    	
    	if (action.equalsIgnoreCase("writeData")) {
    		SerialPortMgr.sendData(dataObj.get("PORT_NUM").toString(), dataObj.get("COMMAND").toString());
    	} else if (action.equalsIgnoreCase("open")) {
    		//{PORT_NUM=COM4, PARAM_OBJ={baudrate=9600, databits=8, stopbits=1}}
    		
    		String portName = dataObj.get("PORT_NUM").toString();
    		HashMap<String, Object> paramMap = (HashMap<String, Object>)dataObj.get("PARAM_OBJ");
    		
    		SerialParam params = new SerialParam();
    		params.setRate(Integer.parseInt(paramMap.get("baudrate").toString()));
    		params.setDataBits(Integer.parseInt(paramMap.get("databits").toString()));
    		params.setStopBits(Integer.parseInt(paramMap.get("stopbits").toString()));
    		
    		SerialPortMgr.openPort(portName, params);
    	} else if (action.equalsIgnoreCase("close")) {
    		String portName = dataObj.get("PORT_NUM").toString();
    		
    		SerialPorter serialPorter = SerialPortMgr.getSerialPorter(portName);
    		serialPorter.close();
    	}
    	
    	
		return rtnMap;
	}
    
	/**
	 * 读取串口数据
	 * @param portNum
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/receive/{portNum}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> readData(@PathVariable String portNum, HttpServletRequest request) {
    	Map<String, Object> rtnMap = new HashMap<String, Object>();
    	
    	
    	
    	
    	
    	
    	
		return rtnMap;
	}
	
	
	
	
}