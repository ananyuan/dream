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
import com.dream.base.Page;
import com.dream.search.SolrMgr;
import com.dream.utils.CommUtils;


@Controller
@RequestMapping("/search")
public class SearchController {

	private static Log log = LogFactory.getLog(SearchController.class);
	
	
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
    	
		return rtnMap;
	}
}