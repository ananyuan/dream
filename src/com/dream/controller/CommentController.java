package com.dream.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.base.Page;
import com.dream.model.Comment;
import com.dream.service.CommentService;


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
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Comment save(@RequestBody Comment comment, HttpSession session) {
    	
    	boolean validated = IdentifyController.checkImgCert(session, comment.getCertval());
    	
    	if (!validated) {
    		return new Comment();
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
    		commentService.insert(comment);
    	}
    	
        return comment;
    }	
}
