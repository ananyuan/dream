package com.dream.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.base.acl.NoNeedLogin;
import com.dream.base.acl.ResultTypeEnum;
import com.dream.utils.WxUtils;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;



@Controller
@RequestMapping("/wechat/js")
public class WechatJsController {
	
	private static Log log = LogFactory.getLog(WechatJsController.class);
	
	@NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping
	public @ResponseBody Map<String, String> wxjsConfig(HttpServletRequest request) {
    	JSONObject myJsonObject = null;
		try {
			myJsonObject = (JSONObject) JSONSerializer.toJSON(request.getReader().readLine());
		} catch (IllegalStateException e) {
			
			log.error("IllegalStateException", e);
		} catch (IOException e) {
			
			log.error("IOException", e);
		}
    	
		String urlStr = myJsonObject.getString("urlStr");
		
    	return WxUtils.getSign(urlStr);
    } 
}
