package com.dream.controller.wf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dream.base.Constant;
import com.dream.base.Page;
import com.dream.controller.AbsController;
import com.dream.controller.ListPageData;
import com.dream.model.wf.WfNodeInst;
import com.dream.service.wf.WfNodeInstService;
import com.dream.utils.DictMgr;

@Controller
@RequestMapping("/wftrack")
public class WfTrackController extends AbsController {
	@Autowired
	private WfNodeInstService wfNodeInstService;
	
    @RequestMapping(value="list/{wfid}")
    public ModelAndView list(@PathVariable String wfid){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("wf/wftrack");
        mav.addObject("wfid", wfid);
        
        return mav;
    }
    
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage, HttpSession session) {
    	Page page = listPage.getPage();
    	
		List<WfNodeInst> rtnList = wfNodeInstService.findWfNodeInsts(page);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, String> entryMap = DictMgr.getEntrysMap(Constant.DICT_TASK_STATUS);
		
		for (WfNodeInst nodeInst: rtnList) {
			Map<String, Object> map = pojoToMap(nodeInst);
			map.put("running", entryMap.get(String.valueOf(nodeInst.getRunning())));
			
			mapList.add(map);
		}
		
		listPage.setRtnList(mapList);
	}
}
