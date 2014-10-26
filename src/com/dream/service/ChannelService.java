package com.dream.service;

import java.util.List;

import com.dream.model.Channel;

public interface ChannelService {
	public int insert(Channel channel);

	public int update(Channel channel);

	public int delete(String id);

	public List<Channel> selectAll();

	public int countAll();

	public Channel findChannel(int code);
}
