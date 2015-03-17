package com.dream.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import com.dream.model.Task;
import com.dream.service.TaskService;
import com.dream.utils.DictMgr;

@Controller
@RequestMapping("/task")
public class TaskController extends AbsController  {
	@Autowired
	private TaskService taskService;
	
	@NoNeedLogin(ResultTypeEnum.page)
    @RequestMapping(value="list")
    public ModelAndView list(Task task){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("task");
        
        return mav;
    }
    
    @Override
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage, HttpSession session) {
    	Page page = listPage.getPage();
    	
		List<Task> rtnList = taskService.findTasks(page);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, String> entryMap = DictMgr.getEntrysMap(Constant.DICT_TASK_STATUS);
		
		for (Task task: rtnList) {
			String caozuo = "<a href='/task/edit/"+task.getId()+"'>编辑</a>&nbsp;<a href='/task/edit/"+task.getId()+"'>删除</a>";
			
			Map<String, Object> map = pojoToMap(task);
			map.put(Constant.COLUMN_CAOZUO, caozuo);
			map.put("ttype", entryMap.get(String.valueOf(task.getTtype())));
			
			mapList.add(map);
		}
		
		listPage.setRtnList(mapList);
	}
    
    
    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id, HttpSession session){
    	ModelAndView mav=new ModelAndView();
    	
    	if (null == session.getAttribute("USER")) {
    		mav.setViewName("login");
    	} else {
            mav.setViewName("taskEdit");
            
            if (id.equals("_ADD_")) {
            	mav.addObject("task", new Task());
            } else {
            	Task task = taskService.findTask(Integer.parseInt(id));
                
                mav.addObject("task",task);
            }
    	}
    	
        return mav;
    }    
    
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Task save(@RequestBody Task task) {
    	int id = task.getId();
    	
    	if (id > 0) {
    		taskService.update(task);
    	} else {
    		taskService.insert(task);
    	}
    	
    	return task;
    }	
    
    @NoNeedLogin(ResultTypeEnum.page)
    @RequestMapping(value="timeline")
    public ModelAndView timeline(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("timeline");
        
        List<Task> tasks = taskService.selectAll();
        mav.addObject("allTask",tasks);
        
        return mav;
    }
    
    
    @RequestMapping(value="/{name}", method = RequestMethod.GET)
	public @ResponseBody Task getTaskInJSON(@PathVariable String name, HttpSession session) {
 
    	session.getAttribute("");
    	
    	Task task = new Task();
    	task.setTitle("this is title");
    	task.setDescp("setDescp");
    	task.setStart(name);
 
		return task;
	}
    
    @RequestMapping(value="/testRb", method = RequestMethod.POST)
    public @ResponseBody Task testRb(@RequestBody Task t) {
        return t;
    }
    
    @NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping(value="/taskTodo", method = RequestMethod.GET)
    public @ResponseBody List<Task> taskTodo() {
    	Page page = new Page();
    	return taskService.findTasksTodo(page);
    }
    
    @NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping(value="/taskFinish", method = RequestMethod.GET)
    public @ResponseBody List<Task> taskFinish() {
    	Page page = new Page();
    	return taskService.findTasksFinish(page);
    }
}
