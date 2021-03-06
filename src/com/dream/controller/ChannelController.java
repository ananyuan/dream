package com.dream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.base.acl.NoNeedLogin;
import com.dream.base.acl.ResultTypeEnum;
import com.dream.model.Channel;
import com.dream.service.ChannelService;


@Controller
@RequestMapping("/channel")
public class ChannelController {
	@Autowired
	private ChannelService channelService;
	
    @RequestMapping(value="list")
    public ModelAndView list(Channel channel){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("channel");
        
        List<Channel> channels = channelService.selectAll();
        mav.addObject("allTask",channels);
        
        return mav;
    }
	
    
    @NoNeedLogin(ResultTypeEnum.json)
    @RequestMapping(value="/channels", method = RequestMethod.GET)
	public @ResponseBody List<Channel> getChannels() {
    	List<Channel> channels = channelService.selectAll();
 
		return channels;
	}
}
