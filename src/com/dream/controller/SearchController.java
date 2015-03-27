package com.dream.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.base.Page;
import com.dream.base.acl.NoNeedLogin;
import com.dream.base.acl.ResultTypeEnum;
import com.dream.search.SolrMgr;
import com.dream.utils.CommUtils;


@Controller
@RequestMapping("/search")
public class SearchController {

	private static Log log = LogFactory.getLog(SearchController.class);
	
	@NoNeedLogin(ResultTypeEnum.json)
	@RequestMapping(value="/{queryStr}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> search(HttpServletRequest request, @PathVariable String queryStr) {
    	log.debug("queryStr = " + queryStr);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
    	
    	HashMap<String, Object> params = CommUtils.getParams(request);
    	Page<?> page;
    	if (null == params.get("_PAGE_")) {
    		page = new Page<Object>();  //获取第一页的数据
    	} else {
    		page = CommUtils.getPage(String.valueOf(params.get("_PAGE_")));
    	}
    	
    	List<HashMap<String, Object>> result = SolrMgr.query(queryStr, page);
    	
    	rtnMap.put("_DATA_", result);
    	rtnMap.put("_PAGE_", page);
    	rtnMap.put("_queryStr_", queryStr);
    	
		return rtnMap;
	}
	
	@NoNeedLogin(ResultTypeEnum.page)
	@RequestMapping(value="/page/{queryStr}/{pageNo}", method = RequestMethod.GET)
	public ModelAndView toPage(HttpServletRequest request, @PathVariable int pageNo, @PathVariable String queryStr) {
    	log.debug("queryStr = " + queryStr);
    	ModelAndView mav=new ModelAndView();
    	mav.setViewName("searchResult");
    	
    	Page<?> page = new Page<Object>();
    	page.setPageNo(pageNo);
    	
    	List<HashMap<String, Object>> result = SolrMgr.query(queryStr, page);
    	
    	mav.addObject("_queryStr_", queryStr);
    	mav.addObject("_DATA_", result);
    	mav.addObject("_PAGE_", page);
    	
    	return mav;
	}
}
