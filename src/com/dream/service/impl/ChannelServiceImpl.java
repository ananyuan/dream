package com.dream.service.impl;

import java.util.List;

import com.dream.dao.ChannelDao;
import com.dream.model.Channel;
import com.dream.service.ChannelService;

public class ChannelServiceImpl implements ChannelService {

	private ChannelDao channelDao;
	
	@Override
	public int insert(Channel channel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Channel channel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Channel> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Channel findChannel(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ChannelDao getChannelDao() {
		return channelDao;
	}

	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}

}
