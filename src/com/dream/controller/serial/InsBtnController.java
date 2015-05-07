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
import com.dream.controller.serial.model.InsBtn;
import com.dream.service.serial.InsBtnService;
import com.dream.utils.UuidUtils;


@Controller
@RequestMapping("/insBtn")
public class InsBtnController extends AbsController {
	private static Log log = LogFactory.getLog(InsBtnController.class);
	
	
	@Autowired
	private InsBtnService insBtnService;
	
	
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage, HttpSession session) {
    	log.debug("SickerRecordController getRtnDataList");
		
    	Page page = listPage.getPage();
    	
		List<InsBtn> rtnList = insBtnService.findBtnsByPage(page);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		for (InsBtn entry: rtnList) {
			String caozuo = "<a href='/insBtn/edit/" + entry.getInsid() + "/" + entry.getId() +"'>编辑</a>";
			
			caozuo += "&nbsp;<a href='#' onclick=deleteItem('" + entry.getId() + "')>删除</a>";
			
			
			Map<String, Object> map = pojoToMap(entry);
			map.put(Constant.COLUMN_CAOZUO, caozuo);
			
			mapList.add(map);
		}
		
		
		listPage.setRtnList(mapList);
	}
    
    
    @RequestMapping(value="/edit/{insid}/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String insid, @PathVariable String id, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	
        mav.setViewName("serial/insBtn_item");
        mav.addObject("insid", insid);
        
        if (id.equals("_ADD_")) {
        	InsBtn insBtn = new InsBtn();
        	insBtn.setInsid(insid);
        	mav.addObject("itemObj", insBtn);
        	
        } else {
        	InsBtn insBtn = insBtnService.findBtn(id);
            
            mav.addObject("itemObj", insBtn);
        }
    	
        return mav;
    }
    
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody InsBtn save(@RequestBody InsBtn insBtn) {
    	boolean addFlag = false;
    	if (insBtn.getId().length() == 0) { 
    		addFlag = true;
    	}
    	
    	if (addFlag) {
    		insBtn.setId(UuidUtils.base58Uuid());
    		insBtnService.insert(insBtn);
    	} else {
    		insBtnService.update(insBtn);
    	}
        
    	InsMgr.removeFromCache(insBtn.getInsid());
    	
        return insBtn;
    }	
}
