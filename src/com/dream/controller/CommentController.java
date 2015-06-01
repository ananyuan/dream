package com.dream.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.base.Page;
import com.dream.base.acl.NoNeedLogin;
import com.dream.base.acl.ResultTypeEnum;
import com.dream.model.Comment;
import com.dream.service.CommentService;
import com.dream.utils.CommUtils;
import com.dream.utils.DateUtils;


@Controller
@RequestMapping("/comments")
public class CommentController extends AbsController {

	private static Log log = LogFactory.getLog(CommentController.class);
	
	@Autowired
	private CommentService commentService;
	
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage, HttpSession session) {
    	log.debug("CommentController getRtnDataList");
		
    	Page page = listPage.getPage();
    	
		List<Comment> rtnList = commentService.findCommnets(page);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		for (Comment entry: rtnList) {
			Map<String, Object> map = pojoToMap(entry);
			
			mapList.add(map);
		}
		
		listPage.setRtnList(mapList);
	}
    
    @NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Comment save(@RequestBody Comment comment, HttpServletRequest request, HttpSession session) {
    	
    	boolean validated = IdentifyController.checkImgCert(session, comment.getCertval());
    	
    	if (!validated) {
    		comment.setAtime("_ERROR_");
    		
    		return comment;
    	}
    	
    	
    	int id = comment.getId();
    	
    	if (comment.getPid() > 0) {
    		int pid = comment.getPid();
    		
    		Comment pcomment = commentService.findComment(pid);
    		
    		if (null != pcomment) {
    			comment.setDepth(pcomment.getDepth() + 1);
    		}
    	}
    	
    	if (id > 0) {
    		commentService.update(comment);
    	} else {
    		comment.setAtime(DateUtils.getDatetime());
    		comment.setIp(request.getRemoteAddr());
    		commentService.insert(comment);
    	}
    	
        return comment;
    }	

    @NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping(value="/list/{dataid}", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> list(@PathVariable String dataid, HttpServletRequest request, HttpSession session) {
    	
    	JSONObject myJsonObject = null;
		try {
			myJsonObject = (JSONObject) JSONSerializer.toJSON(request.getReader().readLine());
		} catch (IllegalStateException e) {
			log.error("IllegalStateException", e);
		} catch (IOException e) {
			log.error("IOException", e);
		}
    	
    	
    	
    	int pageNo = 1;
    	if (myJsonObject.containsKey("pageNo")) {
    		pageNo = myJsonObject.getInt("pageNo");
    	}
    	
    	
    	HashMap<String, Object> rtnMap = new HashMap<String, Object>();
    	
    	Page page = new Page();
    	page.setStr("dataid", dataid);
    	page.setOrder("atime desc");
    	page.setPageNo(pageNo);
    	page.setPageSize(10);
    	
		List<Comment> rtnList = commentService.findCommnets(page);
		rtnMap.put("data", rtnList);
		rtnMap.put("page", page);
		
        return rtnMap;
    }	

}
