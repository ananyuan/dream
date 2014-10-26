package com.dream.service.impl;

import java.util.List;

import com.dream.dao.ChannelDao;
import com.dream.model.Channel;
import com.dream.service.ChannelService;

public class ChannelServiceImpl implements ChannelService {

	private ChannelDao channelDao;
	
	@Override
	public int insert(Channel channel) {
		return channelDao.insert(channel);
	}

	@Override
	public int update(Channel channel) {
		return channelDao.update(channel);
	}

	@Override
	public int delete(String code) {
		return channelDao.delete(code);
	}

	@Override
	public List<Channel> selectAll() {
		return channelDao.selectAll();
	}

	@Override
	public int countAll() {
		return channelDao.countAll();
	}

	@Override
	public Channel findChannel(int code) {
		return channelDao.findChannel(code);
	}

	public ChannelDao getChannelDao() {
		return channelDao;
	}

	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}

}
