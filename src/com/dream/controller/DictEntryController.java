package com.dream.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.base.Constant;
import com.dream.base.Page;
import com.dream.base.acl.NoNeedLogin;
import com.dream.base.acl.ResultTypeEnum;
import com.dream.model.DictEntry;
import com.dream.service.DictEntryService;
import com.dream.utils.DictMgr;


@Controller
@RequestMapping("/dictentry")
public class DictEntryController extends AbsController {

	
	private static Log log = LogFactory.getLog(DictEntryController.class);
	
	@Autowired
	private DictEntryService dictEntryService;
	
    @RequestMapping(value="relate/{dictId}")
    public ModelAndView relate(@PathVariable String dictId){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("dict/dict_entry");
        
        mav.addObject("dictId", dictId);
        
        return mav;
    }
    
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage, HttpSession session) {
    	log.debug("DictController getRtnDataList");
		
    	Page page = listPage.getPage();
    	
		List<DictEntry> rtnList = dictEntryService.findEntrys(page);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		for (DictEntry entry: rtnList) {
			String caozuo = "<a href='/dictentry/edit/" + entry.getDictid() + "/"+entry.getId()+"'>编辑</a>";
			
			caozuo += "&nbsp;<a href='#' onclick=deleteItem('" + entry.getId() + "')>删除</a>";
			
			
			Map<String, Object> map = pojoToMap(entry);
			map.put(Constant.COLUMN_CAOZUO, caozuo);
			
			mapList.add(map);
		}
		
		
		listPage.setRtnList(mapList);
	}
    
    @RequestMapping(value="/edit/{dictid}/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String dictid, @PathVariable String id, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	
        mav.setViewName("dict/dict_entry_item");
        
        if (id.equals("_ADD_")) {
        	DictEntry entry = new DictEntry();
        	entry.setDictid(dictid);
        	
        	mav.addObject("itemObj", entry);
        } else {
        	DictEntry entry = dictEntryService.findEntry(id);
            
        	if (StringUtils.isNotBlank(entry.getPcode())) {
        		DictEntry pentry = DictMgr.getEntry(dictid, entry.getPcode());
        		if (null != pentry) {
        			entry.setPname(pentry.getName());	
        		}
        	}
        	
            mav.addObject("itemObj",entry);
        }
    	
        return mav;
    }
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody DictEntry save(@RequestBody DictEntry entry) {
    	int id = entry.getId();
    	
    	if (StringUtils.isNotBlank(entry.getPcode())) {
    		String pcode = entry.getPcode();
    		String dictid = entry.getDictid();
    		DictEntry pentry = DictMgr.getEntry(dictid, pcode);
    		
    		if (null != pentry) {
    			entry.setDlevel(pentry.getDlevel() + 1);
    		}
    	}
    	
    	if (id > 0) {
    		dictEntryService.update(entry);
    	} else {
    		dictEntryService.insert(entry);
    	}
        
    	//刷新缓存
    	DictMgr.clearCache(entry.getDictid());
    	
    	
        return entry;
    }	
    
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> delete(@PathVariable String id, HttpSession session){
    	DictEntry entry = dictEntryService.findEntry(id);
    	String dictId = entry.getDictid();
    	
    	dictEntryService.delete(id);
    	
    	HashMap<String, Object> rtnMap = new HashMap<String, Object>();
    	rtnMap.put("result", "success");
    	
    	
    	//刷新缓存
    	DictMgr.clearCache(dictId);
    	
        return rtnMap;
    }
    
    @NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping(value="/getDictItems/{dictId}", method = RequestMethod.GET)
    public @ResponseBody List<DictEntry> getDictItems(@PathVariable String dictId, HttpSession session){
    	
    	Page page = new Page();
    	page.put("dictid", dictId);
    	page.setPageSize(Constant.QUERY_COUNT_MAX);
    	page.setOrder("dlevel, esort");
    	
        return dictEntryService.findEntrys(page);
    }
}
