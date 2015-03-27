package com.dream.controller.serial;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.base.Constant;
import com.dream.base.acl.NoNeedLogin;
import com.dream.base.acl.ResultTypeEnum;
import com.dream.controller.serial.mgr.ReceiveData;
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

	@NoNeedLogin(ResultTypeEnum.json)
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
	
	@NoNeedLogin(ResultTypeEnum.json)
	@RequestMapping(value="/sendData", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> sendData(@RequestBody SendData sendData, HttpServletRequest request) 
			 {
    	Map<String, Object> rtnMap = new HashMap<String, Object>();
    	
    	String action = sendData.getActionMethod();
    	
    	HashMap<String, Object> dataObj = sendData.getDataObj();
    	
    	if (action.equalsIgnoreCase("writeData")) {
    		
    		try {
    			SerialPortMgr.sendData(dataObj.get("PORT_NUM").toString(), dataObj.get("COMMAND").toString());
    		} catch(Exception e) {
    			rtnMap.put(Constant.RTN_ERR_MSG, e.getMessage());
    		}
    	} else if (action.equalsIgnoreCase("open")) {
    		//{PORT_NUM=COM4, PARAM_OBJ={baudrate=9600, databits=8, stopbits=1}}
    		
    		String portName = dataObj.get("PORT_NUM").toString();
    		HashMap<String, Object> paramMap = (HashMap<String, Object>)dataObj.get("PARAM_OBJ");
    		
    		SerialParam params = new SerialParam();
    		params.setRate(Integer.parseInt(paramMap.get("baudrate").toString()));
    		params.setDataBits(Integer.parseInt(paramMap.get("databits").toString()));
    		params.setStopBits(Integer.parseInt(paramMap.get("stopbits").toString()));
    		
    		try {
    			SerialPortMgr.openPort(portName, params);
    		} catch(Exception e) {
    			rtnMap.put(Constant.RTN_ERR_MSG, e.getMessage());
    		}
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
	@NoNeedLogin(ResultTypeEnum.json)
	@RequestMapping(value="/receive/{portNum}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> readData(@PathVariable String portNum, HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> rtnMap = new HashMap<String, Object>();
    	
		SerialPorter serialPorter = SerialPortMgr.getSerialPorter(portNum);
		ReceiveData receiveData = serialPorter.getReceiveData();
    	
    	
    	PrintWriter out;
		try {
			out = response.getWriter();
			
			while(true) {
				Thread.currentThread().sleep(10);
				
				String data = receiveData.pop();
				if (null != data) {
					out.write("<script>parent.handleRtnMsg('" + data + "')</script>");
					
					out.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//out.close();
    	
		return rtnMap;
	}
	
	
	
	
}
