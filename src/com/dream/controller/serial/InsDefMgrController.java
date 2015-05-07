package com.dream.controller.serial;

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
import com.dream.controller.AbsController;
import com.dream.controller.ListPageData;
import com.dream.controller.serial.mgr.InsMgr;
import com.dream.controller.serial.model.InsDef;
import com.dream.service.serial.InsDefService;
import com.dream.utils.UuidUtils;


@Controller
@RequestMapping("/insDefMgr")
public class InsDefMgrController extends AbsController {
	private static Log log = LogFactory.getLog(InsDefMgrController.class);
	
	
	@Autowired
	private InsDefService insDefService;
	
	
    @RequestMapping(value="list")
    public ModelAndView list(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("serial/insDefMgr");
        
        return mav;
    }
	
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage, HttpSession session) {
    	log.debug("SickerRecordController getRtnDataList");
		
    	Page page = listPage.getPage();
    	
		List<InsDef> rtnList = insDefService.findDefs(page);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		for (InsDef entry: rtnList) {
			String caozuo = "<a href='/insDefMgr/edit/" + entry.getId() +"'>编辑</a>";
			
			caozuo += "&nbsp;<a href='#' onclick=deleteItem('" + entry.getId() + "')>删除</a>";
			
			
			Map<String, Object> map = pojoToMap(entry);
			map.put(Constant.COLUMN_CAOZUO, caozuo);
			
			mapList.add(map);
		}
		
		
		listPage.setRtnList(mapList);
	}
    
    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	
        mav.setViewName("serial/insDefMgr_item");
        
        if (id.equals("_ADD_")) {
        	InsDef insDef = new InsDef();
        	mav.addObject("itemObj", insDef);
        } else {
        	InsDef insDef = insDefService.findDef(id);
            
            mav.addObject("itemObj", insDef);
        }
    	
        return mav;
    }
    
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody InsDef save(@RequestBody InsDef insDef) {
    	boolean addFlag = false;
    	if (insDef.getId().length() == 0) { 
    		addFlag = true;
    	}
    	
    	if (addFlag) {
    		insDef.setId(UuidUtils.base58Uuid());
    		insDefService.insert(insDef);
    	} else {
    		insDefService.update(insDef);
    	}
        
    	InsMgr.removeFromCache(insDef.getId());
    	
        return insDef;
    }	
}
