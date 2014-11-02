package com.dream.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.model.User;
import com.dream.service.UserService;
 
@Controller
@RequestMapping("/user")
public class UserController {
 
	@Autowired
	private UserService userService;
	
    @RequestMapping(value="index")
    public ModelAndView index(User user){
    	userService.insert(user);
        ModelAndView mav=new ModelAndView();
        mav.setViewName("index");
        mav.addObject("user",user);
        return mav;
    }
    
    
    @RequestMapping(value="/login")
    public ModelAndView login(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("login");
        
        return mav;
    }
    
    @RequestMapping(value="/authenticate", method = RequestMethod.POST)
    public @ResponseBody User authenticate(@RequestBody User user, HttpSession session){
        if (!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword())) {
        	User userFind = userService.findByUserName(user.getUsername());
        	if (null != userFind && userFind.getPassword().equalsIgnoreCase(user.getPassword())) {
        		session.setAttribute("USER", userFind);
        		return userFind;
        	}
        } 
        
        return new User();
    }
}