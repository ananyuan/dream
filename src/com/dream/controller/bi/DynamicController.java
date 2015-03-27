package com.dream.controller.bi;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.base.acl.NoNeedLogin;
import com.dream.base.acl.ResultTypeEnum;
import com.dream.controller.AbsController;
import com.dream.model.bi.Dynamic;
import com.dream.service.bi.DynamicService;

@Controller
@RequestMapping("/dynamic")
public class DynamicController extends AbsController {
	
	@Autowired
	private DynamicService dynamicService;
	
	@NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping(value="/sync", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> sync(@RequestBody Dynamic dynamic, HttpSession session){
		
		//TODO 动态token 判断， 否则不让进行下一步
		Dynamic old = dynamicService.findDynamic(dynamic.getId());
		if (null == old) {
			dynamicService.insert(dynamic);	
		}
		
    	HashMap<String, Object> rtnMap = new HashMap<String, Object>();
    	rtnMap.put("_MSG_", "success");
    	
    	return rtnMap;
    } 
	
	@NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping(value="/list/{lasttime}", method = RequestMethod.GET)
    public @ResponseBody List<Dynamic> getMoreDynamics(@PathVariable String lasttime, HttpSession session){
		
		return dynamicService.findNewDynamics(lasttime);
    } 
	
}
