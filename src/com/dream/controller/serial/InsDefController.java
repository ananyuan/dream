package com.dream.controller.serial;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.base.acl.NoNeedLogin;
import com.dream.base.acl.ResultTypeEnum;
import com.dream.controller.serial.mgr.InsDefMgr;
import com.dream.controller.serial.mgr.InsMgr;
import com.dream.controller.serial.mgr.SerialPortMgr;
import com.dream.controller.serial.model.InsActLog;
import com.dream.controller.serial.model.InsBtn;
import com.dream.controller.serial.model.InsDef;
import com.dream.utils.CommUtils;
import com.dream.utils.DateUtils;


@Controller
@RequestMapping("/insDef")
public class InsDefController {

	
	private static Log log = LogFactory.getLog(InsDefController.class);
	
	
    @RequestMapping(value="/page/{inscode}", method = RequestMethod.GET)
    public ModelAndView board(@PathVariable String inscode, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	mav.setViewName("serial/board");
    	
    	mav.addObject("inscode", inscode);
    	
    	InsDefMgr insDefMgr = InsMgr.getInsDef(inscode);
    	
    	InsDef insDef = insDefMgr.getInsDef();
//    	insDef.setId(UuidUtils.base58Uuid());
//    	insDef.setCode("yiqiX");
//    	insDef.setName("仪器X");
//    	insDef.setChannum(4);
//    	insDef.setModel("");
    	
    	mav.addObject("insDef", insDef);
    	
    	return mav;
    }
    
    @RequestMapping(value="/page/{inscode}/{channelport}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String inscode, @PathVariable String channelport, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	mav.setViewName("serial/page");
    	
    	mav.addObject("inscode", inscode);
    	mav.addObject("channelport", channelport);
    	
//    	InsDef insDef = new InsDef();
//    	insDef.setId(UuidUtils.base58Uuid());
//    	insDef.setCode("yiqiX");
//    	insDef.setName("仪器X");
//    	insDef.setChannum(4);
//    	insDef.setModel("");
    	
    	InsDefMgr insDefMgr = InsMgr.getInsDef(inscode);
    	
    	InsDef insDef = insDefMgr.getInsDef();
    	
    	mav.addObject("insDef", insDef);
    	
    	return mav;
    }
	
	
	@NoNeedLogin(ResultTypeEnum.json)
	@RequestMapping(value="/defdata", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getInsDefData(HttpServletRequest request, HttpSession session) {
		String inscode = (String)request.getParameter("inscode");
		HashMap<String, Object> params = CommUtils.getParams(request);
		
		
    	Map<String, Object> rtnMap = new HashMap<String, Object>();
//    	InsDef insDef = new InsDef();
//    	insDef.setId(UuidUtils.base58Uuid());
//    	insDef.setCode("yiqiX");
//    	insDef.setName("仪器X");
//    	insDef.setChannum(4);
//    	insDef.setModel("");
//    	List<InsField> insFields = new ArrayList<InsField>();
//    	for (int i=0;i<7;i++) {
//    		InsField instField = new InsField();
//    		instField.setCode("code" + i);
//    		instField.setName("名称"+i);
//    		if (i==6) {
//    			instField.setItemtype("INPUT");	
//    		} else if (i == 1) {
//    			instField.setItemtype("SLIDER");
//    		} else if (i==2) {
//    			instField.setItemtype("SELECT");
//    		} else if (i==3) {
//    			instField.setItemtype("DESCP");
//    		} else if (i==4) {
//    			instField.setItemtype("ONOFF");
//    		} else if (i==5) {
//    			instField.setItemtype("DYNAMICGRAPH");
//    		} else if (i==0) {
//    			instField.setItemtype("COUNTDOWN");
//    		} 
//    		
//    		insFields.add(instField);
//    	}
//    	
//    	
//    	List<InsBtn> insBtns = new ArrayList<InsBtn>();
//    	for (int i=0;i<3;i++) {
//    		InsBtn instBtn = new InsBtn();
//    		instBtn.setCode("btncode" + i);
//    		instBtn.setName("按钮名" + i);
//    		instBtn.setCommand("#code"+i+"#");
//    		insBtns.add(instBtn);
//    	}
//    	
//    	insDef.setInsBtns(insBtns);
//    	insDef.setInsFields(insFields);
    	
    	
    	InsDefMgr insDefMgr = InsMgr.getInsDef(inscode);
    	
    	InsDef insDef = insDefMgr.getInsDef();
    	
    	insDef.setInsBtns(insDefMgr.getInsBtns());
    	insDef.setInsFields(insDefMgr.getFields());
    	
    	rtnMap.put("_DATA_", insDef);
    	
		return rtnMap;
	}
	
	
	@RequestMapping(value = "/csstart", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> csstart(HttpServletRequest request, HttpSession session) {
		HashMap<String, Object> params = CommUtils.getParams(request);
		String insCode = (String) request.getParameter("inscode");
		String channelPort = (String)params.get("channelport");
		
		InsDefMgr insDefMgr = InsMgr.getInsDef(insCode);
		InsBtn insBtn = insDefMgr.getInsBtn("csstart");
		
		String btnCommand = CommUtils.replaceValues(insBtn.getCommand(), params);
		
		
		
		// 记录日志
		InsActLog insAct = getBaseActLog("csstart");
		insAct.setActname(insBtn.getName());
		insAct.setCommand(btnCommand);
		
		// 发送命令
		//TODO 
		String portNum = "COM4";
		
		try {
			SerialPortMgr.sendData(portNum, btnCommand);
		} catch (Exception e) {
			log.error("SerialPortMgr.sendData", e);
		}
		
		return new HashMap<String, Object>();
	}
	
	
	@RequestMapping(value = "/cspause", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> cspause(HttpServletRequest request, HttpSession session) {
		HashMap<String, Object> params = CommUtils.getParams(request);
		String insCode = (String) request.getParameter("inscode");
		String channelPort = (String)params.get("channelport");
		
		InsDefMgr insDefMgr = InsMgr.getInsDef(insCode);
		InsBtn insBtn = insDefMgr.getInsBtn("csstart");
		
		String btnCommand = CommUtils.replaceValues(insBtn.getCommand(), params);
		
		
		
		// 记录日志
		InsActLog insAct = getBaseActLog("cspause");
		insAct.setActname(insBtn.getName());
		insAct.setCommand(btnCommand);
		
		// 发送命令
		//TODO 
		String portNum = "COM4";
		
		try {
			SerialPortMgr.sendData(portNum, btnCommand);
		} catch (Exception e) {
			log.error("SerialPortMgr.sendData", e);
		}
		
		return new HashMap<String, Object>();
	}
	
	@RequestMapping(value = "/csstop", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> csstop(HttpServletRequest request, HttpSession session) {
		HashMap<String, Object> params = CommUtils.getParams(request);
		String insCode = (String) request.getParameter("inscode");
		String channelPort = (String)params.get("channelport");
		
		InsDefMgr insDefMgr = InsMgr.getInsDef(insCode);
		InsBtn insBtn = insDefMgr.getInsBtn("csstart");
		
		String btnCommand = CommUtils.replaceValues(insBtn.getCommand(), params);
		
		
		
		// 记录日志
		InsActLog insAct = getBaseActLog("csstop");
		insAct.setActname(insBtn.getName());
		insAct.setCommand(btnCommand);
		
		// 发送命令
		//TODO 
		String portNum = "COM4";
		
		try {
			SerialPortMgr.sendData(portNum, btnCommand);
		} catch (Exception e) {
			log.error("SerialPortMgr.sendData", e);
		}
		
		return new HashMap<String, Object>();
	}
	
	
	
	private InsActLog getBaseActLog(String act) {
		InsActLog insAct = new InsActLog();
		insAct.setActcode(act);
		insAct.setLogtime(DateUtils.getDatetime());
		
		
		return insAct;
	}
}
