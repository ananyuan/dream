package com.dream.controller.wf;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.controller.org.UserMgr;
import com.dream.model.WfBaseBean;
import com.dream.model.org.User;
import com.dream.service.wf.WfAct;


@Controller
@RequestMapping("/wf")
public class WfController {

	private static Log log = LogFactory.getLog(WfController.class);
	
    @RequestMapping(value="/toNext", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> toNext(HttpServletRequest request, HttpSession session) {
    	try {
    		JSONObject myJsonObject =  (JSONObject) JSONSerializer.toJSON(request.getReader().readLine());
        	String nextNode = myJsonObject.getString("nextNode");
        	
        	String niid = myJsonObject.getString("niid");
        	String dataid = myJsonObject.getString("dataid");
        	String touser = myJsonObject.getString("touser");
        	String title = myJsonObject.getString("title");
        	User toUserObj = UserMgr.getUser(touser);
        	
        	WfAct wfAct = new WfAct(Integer.parseInt(niid));
        	WfAct nextAct = wfAct.toNext(nextNode, toUserObj, toUserObj);
        	
        	WfBaseBean wfBaseBean = new WfBaseBean();
        	wfBaseBean.setVmodel("vacation");
        	wfBaseBean.setId(Integer.parseInt(dataid));
        	
        	nextAct.sendTodo(wfBaseBean, title);
    	} catch (Exception e) {
    		log.error("save wf error ", e);
    	}

    	
    	return new HashMap<String, Object>();
    }	
}
