package com.dream.dao;

import java.util.List;

import com.dream.model.Channel;

public interface ChannelDao {
	public int insert(Channel channel);

	public int update(Channel channel);

	public int delete(String code);

	public List<Channel> selectAll();

	public int countAll();

	public Channel findChannel(int code);
}
