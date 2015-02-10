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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.base.Constant;
import com.dream.base.Page;
import com.dream.model.Dict;
import com.dream.service.DictEntryService;
import com.dream.service.DictService;
import com.dream.utils.DictMgr;
import com.dream.utils.SpringContextUtil;


@Controller
@RequestMapping("/dict")
public class DictController extends AbsController {
	
	private static Log log = LogFactory.getLog(DictController.class);
	
	@Autowired
	private DictService dictService;
	
    @RequestMapping(value="list")
    public ModelAndView list(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("dict/dict");
        
        return mav;
    }
	
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage) {
    	log.debug("DictController getRtnDataList");
		
    	Page page = listPage.getPage();
		List<Dict> rtnList = dictService.findDicts(page);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		for (Dict dict: rtnList) {
			String caozuo = "<a href='/dict/edit/"+dict.getCode()+"'>编辑</a>&nbsp;";
			caozuo += "&nbsp;<a href='#' onclick=deleteItem('"+dict.getCode()+"')>删除</a>";
			
			Map<String, Object> map = pojoToMap(dict);
			map.put(Constant.COLUMN_CAOZUO, caozuo);
			mapList.add(map);
		}
		
		
		listPage.setRtnList(mapList);
	}
    
    
    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	
        mav.setViewName("dict/dict_item");
        
        if (id.equals("_ADD_")) {
        	Dict dict = new Dict();
        	mav.addObject("itemObj", dict);
        } else {
        	Dict dict = dictService.findDict(id);
            
            mav.addObject("itemObj",dict);
        }
    	
        return mav;
    }
    
    @RequestMapping(value="/getDict/{id}", method = RequestMethod.GET)
    public @ResponseBody Dict getDict(@PathVariable String id, HttpSession session){
        Dict dict = DictMgr.getDict(id);
    	
        return dict;
    }
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Dict save(@RequestBody Dict dict) {
    	String code = dict.getCode();
    	
    	Dict oldDict = dictService.findDict(code);
    	
    	if (null == oldDict) {
    		dictService.insert(dict);
    	} else {
    		dictService.update(dict);
    	}
        
        return dict;
    }	
    
    
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> delete(@PathVariable String id, HttpSession session){
    	//删除关联的字典项
    	DictEntryService dictEntryService = SpringContextUtil.getBean("dictEntryService");
    	dictEntryService.deleteByDictId(id);
    	
    	//删除字典的定义
    	dictService.delete(id);
    	
    	HashMap<String, Object> rtnMap = new HashMap<String, Object>();
    	rtnMap.put("result", "success");
    	
    	
        return rtnMap;
    }
}
