package com.dream.dao.org;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.org.User;

public interface UserDao {
	public int insert(User user);

	public int update(User user);

	public int delete(String id);

	public int countAll();

	public User findUser(String id);
	
	public User findUserByLoginname(String loginname);

	public List<User> findUsers(Page page);
	
}
