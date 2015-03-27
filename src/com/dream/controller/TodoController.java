package com.dream.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.base.Constant;
import com.dream.base.Page;
import com.dream.model.Todo;
import com.dream.model.org.User;
import com.dream.service.TodoService;
import com.dream.utils.DictMgr;


@Controller
@RequestMapping("/todo")
public class TodoController extends AbsController {
	@Autowired
	private TodoService todoService;
	
    @RequestMapping(value="list")
    public ModelAndView list(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("todo");
        
        return mav;
    }
    
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage, HttpSession session) {
    	Page page = listPage.getPage();
    	
		List<Todo> rtnList = todoService.findTodos(page);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, String> entryMap = DictMgr.getEntrysMap(Constant.DICT_TASK_STATUS);
		
		for (Todo todo: rtnList) {
			String title = "<a href='"+todo.getUrl()+"'>"+todo.getTitle()+"</a>";
			
			Map<String, Object> map = pojoToMap(todo);
			map.put("title", title);
			map.put("tdype", entryMap.get(String.valueOf(todo.getTdtype())));
			
			mapList.add(map);
		}
		
		listPage.setRtnList(mapList);
	}
    
    @RequestMapping(value="/latestTodo", method = RequestMethod.GET)
    public @ResponseBody List<Todo> latestTodo(HttpSession session) {
    	Page page = new Page();
    	page.setOrder("tdtime desc");
    	page.setPageSize(4);
    	page.put("tdtype", Constant.YES);
    	
    	
    	if (null == session.getAttribute("USER")) {
    		// TODO
    	} else {
    		User user = (User)session.getAttribute("USER");
    		
    		page.put("userid", user.getId());
    	}
    	
    	return todoService.findTodos(page);
    }
    
}
