package com.dream.controller.serial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.base.Constant;
import com.dream.base.Context;
import com.dream.base.acl.NoNeedLogin;
import com.dream.base.acl.ResultTypeEnum;
import com.dream.controller.serial.mgr.InsDefMgr;
import com.dream.controller.serial.mgr.InsMgr;
import com.dream.controller.serial.mgr.SerialPortMgr;
import com.dream.controller.serial.model.InsBtn;
import com.dream.controller.serial.model.InsDef;
import com.dream.controller.serial.model.InsField;
import com.dream.model.ActLog;
import com.dream.model.sicker.SickerRecord;
import com.dream.service.ActLogService;
import com.dream.service.serial.SickerRecordService;
import com.dream.utils.CommUtils;
import com.dream.utils.DateUtils;
import com.dream.utils.DictMgr;
import com.dream.utils.UuidUtils;


@Controller
@RequestMapping("/insDef")
public class InsDefController {

	
	private static Log log = LogFactory.getLog(InsDefController.class);
	
	@Autowired
	private ActLogService actLogService;
	
	
	@Autowired
	private SickerRecordService sickerRecordService;
	
	
    @RequestMapping(value="/page/{inscode}", method = RequestMethod.GET)
    public ModelAndView board(@PathVariable String inscode, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	mav.setViewName("serial/board");
    	
    	mav.addObject("inscode", inscode);
    	
    	InsDefMgr insDefMgr = InsMgr.getInsDef(inscode);
    	
    	InsDef insDef = insDefMgr.getInsDef();
    	
    	mav.addObject("insDef", insDef);
    	
    	return mav;
    }
    
    @RequestMapping(value="/page/{inscode}/{channelport}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String inscode, @PathVariable String channelport, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	mav.setViewName("serial/page");
    	
    	mav.addObject("inscode", inscode);
    	mav.addObject("channelport", channelport);
    	
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

    	InsDefMgr insDefMgr = InsMgr.getInsDef(inscode);
    	
    	InsDef insDef = insDefMgr.getInsDef();
    	
    	insDef.setInsBtns(insDefMgr.getInsBtns());
    	insDef.setInsFields(insDefMgr.getFields());
    	
    	rtnMap.put("_DATA_", insDef);
    	
		return rtnMap;
	}
	
	@NoNeedLogin(ResultTypeEnum.json)
	@RequestMapping(value = "/matchport", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> matchport(HttpServletRequest request, HttpSession session) {
		HashMap<String, Object> params = CommUtils.getParams(request);
		
		String inscode = (String)params.get("inscode");
		InsDefMgr insDefMgr = InsMgr.getInsDef(inscode);
		
		String portNum = SerialPortMgr.getMatchPortNum(insDefMgr.getInsDef().getValidateres());
		
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		
		if (StringUtils.isNotBlank(portNum)) {
			rtnMap.put("serial_port_num", portNum);	
		} else {
			rtnMap.put("serial_port_num", "__ERROR__");	
		}
		
		return rtnMap;
	}
	
	
	@RequestMapping(value = "/dynamicdata", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> dynamicdata(HttpServletRequest request, HttpSession session) {
		HashMap<String, Object> params = CommUtils.getParams(request);
		
		String inscode = (String)params.get("inscode");
		String dytype = (String)params.get("dytype");
		
		
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("_DATA_", Math.random());
		
		return rtnMap;
	}
	
	
	@RequestMapping(value = "/csstart", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> csstart(HttpServletRequest request, HttpSession session) {
		return doAct("csstart", request);
	}
	
	
	@RequestMapping(value = "/cspause", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> cspause(HttpServletRequest request, HttpSession session) {
		return doAct("cspause", request);
	}
	
	@RequestMapping(value = "/csstop", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> csstop(HttpServletRequest request, HttpSession session) {
		return doAct("csstop", request);
	}
	
	@RequestMapping(value = "/act", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> act(HttpServletRequest request, HttpSession session) {
		return doAct("", request);
	}
	
	
	/**
	 * 
	 * @param actCode 操作
	 * @param request request
	 * @return 操作的结果
	 */
	private Map<String, Object> doAct(String actCode,  HttpServletRequest request) {
		HashMap<String, Object> params = CommUtils.getParams(request);
		
		String insCode = (String) request.getParameter("inscode");
		String channelPort = (String)params.get("channelport");
		String portNum = (String)params.get("serialportnum");
		String userid = (String)params.get("userid");
		
		if (StringUtils.isEmpty(actCode)) {
			actCode = (String)params.get("act_code");
		}
		
		log.debug("send command actcode is " + actCode);
		
		InsDefMgr insDefMgr = InsMgr.getInsDef(insCode);
		
		String pici = (String)params.get("pici");
		if (!params.containsKey("pici") && StringUtils.isNotBlank("pici")) {
			pici = UuidUtils.base58Uuid();
		}
		
		if (actCode.equalsIgnoreCase("csstart")) {
			
			HashMap<String, Object> conObj = new HashMap<String, Object>();
			conObj.put("userid", userid);
			conObj.put("pici", pici);
			
			Context.addContextObj(Constant.CONTEXT_KEY_SERIAL, conObj);
			
			if (StringUtils.isBlank((String)params.get("pici"))) {
				SickerRecord sickerRecord = new SickerRecord();
				sickerRecord.setId(pici);
				sickerRecord.setSickerid(Integer.parseInt(userid));
				sickerRecord.setIntime(DateUtils.getDatetime());
				
				String memo = getMemoFieldData(insDefMgr.getFields(), params);
				sickerRecord.setMemo(memo);
				
				sickerRecordService.insert(sickerRecord);
			}
		} else if (actCode.equalsIgnoreCase("csstop")) {
			Context.removeContextObj(Constant.CONTEXT_KEY_SERIAL);
			
			SickerRecord sickerRecord = sickerRecordService.findRecord(pici);
			sickerRecord.setOuttime(DateUtils.getDatetime());
			sickerRecordService.update(sickerRecord);
		}
		
		
		InsBtn insBtn = insDefMgr.getInsBtn(actCode);
		
		String btnCommand = CommUtils.replaceValues(insBtn.getCommand(), params);

		if (StringUtils.isBlank(portNum)) {
			portNum = SerialPortMgr.getMatchPortNum(insDefMgr.getInsDef().getValidateres());
		}
		
		// 记录日志
		ActLog insAct = getBaseActLog(actCode);
		insAct.setActname(insBtn.getName());
		insAct.setMemo(btnCommand);
		insAct.setPortnum(channelPort);
		insAct.setModelType(insDefMgr.getInsDef().getName());
		insAct.setDataId(pici);
		
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("pici", pici);
		
		if (StringUtils.isBlank(portNum)) {
			rtnMap.put("_ERROR_MSG_", "关联端口未找到");
			
			return rtnMap;
		}
		
		// 发送命令
		try {
			SerialPortMgr.sendData(portNum, btnCommand);
		} catch (Exception e) {
			log.error("SerialPortMgr.sendData", e);
			rtnMap.put("_ERROR_MSG_", e.getMessage());
			insAct.setResult(e.getMessage());
		}
		
		if (actCode.equalsIgnoreCase("csstop")) {
			pici = ""; //重置
		}
		
		actLogService.insert(insAct);
		
		return rtnMap;
	}
	
	/**
	 * 
	 * @param fields 字段列表
	 * @param params 上传的页面参数
	 * @return 页面设置的值
	 */
	private String getMemoFieldData(List<InsField> fields,
			HashMap<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		
		for (InsField field: fields) {
			if (field.getItemtype().equalsIgnoreCase("DYNAMICGRAPH") ||
					field.getItemtype().equalsIgnoreCase("DESCP")) {
				continue;
			}
			
			String value = (String)params.get(field.getCode());
			
			if (StringUtils.isNotBlank(field.getReldict())) {
				Map<String, String> entryMap = DictMgr.getEntrysMap(field.getReldict());
				
				value = entryMap.get(value);
			}
			
			sb.append(field.getName()).append(":").append(value).append(";\n");
		}
		
		
		return sb.toString();
	}

	private ActLog getBaseActLog(String act) {
		ActLog insAct = new ActLog();
		insAct.setActType(act);
		insAct.setAtime(DateUtils.getDatetime());
		
		
		return insAct;
	}
}
