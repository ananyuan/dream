package com.dream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dream.model.Task;
import com.dream.service.TaskService;

@Controller
public class TaskController {
	@Autowired
	private TaskService taskService;
	
    @RequestMapping(value="task")
    public ModelAndView index(Task task){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("task");
        
        List<Task> tasks = taskService.selectAll();
        mav.addObject("allTask",tasks);
        
        return mav;
    }
}
