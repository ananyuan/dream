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
import com.dream.controller.serial.model.InsField;
import com.dream.model.DictEntry;
import com.dream.service.serial.InsFieldService;
import com.dream.utils.DictMgr;
import com.dream.utils.UuidUtils;

@Controller
@RequestMapping("/insField")
public class InsFieldController extends AbsController {
	private static Log log = LogFactory.getLog(InsFieldController.class);
	
	
	@Autowired
	private InsFieldService insFieldService;
	
	
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage, HttpSession session) {
    	log.debug("SickerRecordController getRtnDataList");
		
    	Page page = listPage.getPage();
    	
		List<InsField> rtnList = insFieldService.findFieldsByPage(page);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		Map<String, String> entryMap = DictMgr.getEntrysMap(Constant.DICT_INS_INPUT_TYPE);
		
		for (InsField entry: rtnList) {
			String caozuo = "<a href='/insField/edit/" + entry.getInsid() + "/" + entry.getId() +"'>编辑</a>";
			
			caozuo += "&nbsp;<a href='#' onclick=deleteItem('" + entry.getId() + "')>删除</a>";
			
			
			Map<String, Object> map = pojoToMap(entry);
			map.put(Constant.COLUMN_CAOZUO, caozuo);
			
			
			map.put("itemtype", entryMap.get(entry.getItemtype()));
			
			mapList.add(map);
		}
		
		
		listPage.setRtnList(mapList);
	}
    
    
    @RequestMapping(value="/edit/{insid}/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String insid, @PathVariable String id, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	
        mav.setViewName("serial/insField_item");
        mav.addObject("insid", insid);
        
        if (id.equals("_ADD_")) {
        	InsField insField = new InsField();
        	insField.setInsid(insid);
        	mav.addObject("itemObj", insField);
        } else {
        	InsField insField = insFieldService.findField(id);
            
            mav.addObject("itemObj", insField);
        }
    	
        List<DictEntry> insInputTypes = DictMgr.getDict(Constant.DICT_INS_INPUT_TYPE).getChilds();
        mav.addObject("insInputTypes", insInputTypes);
        
        
        return mav;
    }
    
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody InsField save(@RequestBody InsField insField) {
    	boolean addFlag = false;
    	if (insField.getId().length() == 0) { 
    		addFlag = true;
    	}
    	
    	if (addFlag) {
    		insField.setId(UuidUtils.base58Uuid());
    		insFieldService.insert(insField);
    	} else {
    		insFieldService.update(insField);
    	}
    	
    	InsMgr.removeFromCache(insField.getInsid());
        
        return insField;
    }	
}
