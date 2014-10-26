package com.dream.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.base.Page;
import com.dream.model.Task;
import com.dream.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {
	@Autowired
	private TaskService taskService;
	
    @RequestMapping(value="list")
    public ModelAndView list(Task task){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("task");
        
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
    
    @RequestMapping(value="/taskTodo", method = RequestMethod.GET)
    public @ResponseBody List<Task> taskTodo() {
    	Page page = new Page();
    	return taskService.findTasksTodo(page);
    }
    
    @RequestMapping(value="/taskFinish", method = RequestMethod.GET)
    public @ResponseBody List<Task> taskFinish() {
    	Page page = new Page();
    	return taskService.findTasksFinish(page);
    }
}
