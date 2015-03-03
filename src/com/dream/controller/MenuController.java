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
import com.dream.model.Menu;
import com.dream.service.MenuService;
import com.dream.utils.DictMgr;


@Controller
@RequestMapping("/menu")
public class MenuController extends AbsController {
	
	private static Log log = LogFactory.getLog(MenuController.class);
	
	@Autowired
	private MenuService menuService;
	
    @RequestMapping(value="list")
    public ModelAndView list(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("menu/menu");
        
        return mav;
    }
	
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage) {
    	log.debug("MenuController getRtnDataList");
		
    	Page page = listPage.getPage();
		List<Menu> rtnList = menuService.findMenus(page);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		Map<String, String> entryMap = DictMgr.getEntrysMap(Constant.DICT_MENU_TYPE);
		
		
		for (Menu menu: rtnList) {
			String caozuo = "<a href='/menu/edit/"+menu.getId()+"'>编辑</a>&nbsp;";
			caozuo += "&nbsp;<a href='#' onclick=deleteItem('"+menu.getId()+"')>删除</a>";
			
			Map<String, Object> map = pojoToMap(menu);
			map.put(Constant.COLUMN_CAOZUO, caozuo);
			
			map.put("mtype", entryMap.get(menu.getMtype()));
			
			mapList.add(map);
		}
		
		
		listPage.setRtnList(mapList);
	}
    
    
    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	
        mav.setViewName("menu/menu_item");
        
        if (id.equals("_ADD_")) {
        	Menu menu = new Menu();
        	mav.addObject("itemObj", menu);
        } else {
        	Menu menu = menuService.findMenu(Integer.parseInt(id));
            
            mav.addObject("itemObj", menu);
        }
    	
        return mav;
    }
    
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Menu save(@RequestBody Menu menu) {
    	int id = menu.getId();
    	
    	int pid = menu.getPid();
    	
    	if (pid == 0) {
    		menu.setMlevel(1);
    	} else {
    		Menu pmenu = menuService.findMenu(pid);
    		menu.setMlevel(pmenu.getMlevel() + 1);
    	}
    	
    	if (id == 0) {
    		menuService.insert(menu);
    	} else {
    		menuService.update(menu);
    	}
        
        return menu;
    }	
    
    
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> delete(@PathVariable String id, HttpSession session){
    	//删除字典的定义
    	menuService.delete(Integer.parseInt(id));
    	
    	HashMap<String, Object> rtnMap = new HashMap<String, Object>();
    	rtnMap.put("result", "success");
    	
    	
        return rtnMap;
    }
    
    
    
    @RequestMapping(value="/getAllMenu", method = RequestMethod.GET)
    public @ResponseBody List<Menu> getAllMenu(HttpSession session){
    	
    	Page page = new Page();
    	page.setPageSize(1000);
    	page.setOrder("msort asc");
    	
    	List<Menu> menuList = menuService.findMenus(page);
    	
        return recurEntry(menuList);
    }
 
    @RequestMapping(value="/getMenuList", method = RequestMethod.GET)
    public @ResponseBody List<Menu> getMenuList(HttpSession session){
    	
    	Page page = new Page();
    	page.setPageSize(1000);
    	page.setOrder("msort asc");
    	page.setStr("mtype", "FOLDER");
    	
    	List<Menu> menuList = menuService.findMenus(page);
    	
    	Menu rootMenu = new Menu();
    	rootMenu.setName("菜单定义");
    	
    	menuList.add(rootMenu);
    	
    	return menuList;
    }    
    
    
	/**
	 * 
	 * @param entrys  
	 * @return
	 */
	private List<Menu> recurEntry(List<Menu> menus) {
		HashMap<Integer, Menu> entryMap = new HashMap<Integer, Menu>();
		Menu rootEntry = new Menu();
		rootEntry.setChilds(new ArrayList<Menu>());
		entryMap.put(0, rootEntry);
		
		int pCode = 0;
		for (Menu entry: menus) {
			entry.setChilds(new ArrayList<Menu>());
			entryMap.put(entry.getId(), entry);
			
			if (entry.getPid() != pCode) {
				pCode = entry.getPid();
			}
			
			if (pCode == 0) {
				List<Menu> entryListTemp = rootEntry.getChilds();
				
				entryListTemp.add(entry);
			} else {
				Menu entryTemp = entryMap.get(pCode);
				List<Menu> entryListTemp = entryTemp.getChilds();
				
				entryListTemp.add(entry);
			}
		}
		
		return rootEntry.getChilds();
	}
    
}
