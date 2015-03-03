package com.dream.controller.org;

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
import com.dream.base.TreeBean;
import com.dream.controller.AbsController;
import com.dream.controller.ListPageData;
import com.dream.controller.MenuController;
import com.dream.model.org.Dept;
import com.dream.service.org.DeptService;
import com.dream.utils.UuidUtils;


@Controller
@RequestMapping("/dept")
public class DeptController extends AbsController {

	
	private static Log log = LogFactory.getLog(MenuController.class);
	
	@Autowired
	private DeptService deptService;
	
    @RequestMapping(value="list")
    public ModelAndView list(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("org/dept");
        
        return mav;
    }
	
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage) {
    	log.debug("MenuController getRtnDataList");
		
    	Page page = listPage.getPage();
		List<Dept> rtnList = deptService.findDepts(page);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		for (Dept dept: rtnList) {
			String caozuo = "<a href='/dept/edit/"+dept.getCode()+"'>编辑</a>&nbsp;";
			caozuo += "&nbsp;<a href='#' onclick=deleteItem('"+dept.getCode()+"')>删除</a>";
			
			Map<String, Object> map = pojoToMap(dept);
			map.put(Constant.COLUMN_CAOZUO, caozuo);
			
			mapList.add(map);
		}
		
		
		listPage.setRtnList(mapList);
	}
    
    
    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	
        mav.setViewName("org/dept_item");
        
        if (id.equals("_ADD_")) {
        	Dept dept = new Dept();
        	mav.addObject("itemObj", dept);
        } else {
        	Dept dept = DeptMgr.getDept(id);
        	
        	if (dept.getPcode().length() > 0) {
            	Dept pdept = DeptMgr.getDept(dept.getPcode());
                if (null != pdept) {
                	dept.setPname(pdept.getName());	
                }
        	}
        	
            mav.addObject("itemObj", dept);
        }
    	
        return mav;
    }
    
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Dept save(@RequestBody Dept dept) {
    	String code = dept.getCode();
    	String pcode = dept.getPcode();
    	boolean addFlag = false;
    	
    	
    	if (code.length() == 0) {
    		addFlag = true;
    		code = UuidUtils.base58Uuid();
    		dept.setCode(code);
    	}
    	
    	if (pcode.length() == 0) {
    		dept.setDlevel(1);
    		dept.setDpath(code + Constant.COMMA_SEPARATOR);
    	} else {
    		Dept pmenu = deptService.findDept(pcode);
    		dept.setDlevel(pmenu.getDlevel() + 1);
    		dept.setDpath(pmenu.getDpath() + code + Constant.COMMA_SEPARATOR);
    	}
    	
    	if (addFlag) {
    		deptService.insert(dept);
    	} else {
    		deptService.update(dept);
    	}
    	
    	//更新部门的字典
    	DeptMgr.putCache(code, dept);
    	
    	DeptMgr.clearCacheList();
        
        return dept;
    }	
    
    
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> delete(@PathVariable String id, HttpSession session){
    	//删除字典的定义
    	deptService.delete(id);
    	
    	HashMap<String, Object> rtnMap = new HashMap<String, Object>();
    	rtnMap.put("result", "success");
    	
    	
        return rtnMap;
    }
    
    @RequestMapping(value="/getDeptListForTree", method = RequestMethod.GET)
    public @ResponseBody List<TreeBean> getDeptListForTree(HttpSession session){
    	
    	return DeptMgr.getDeptList();
    }
}
