package com.dream.service.org;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.org.UserDao;
import com.dream.model.org.User;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public int countAll() {
		return this.userDao.countAll();
	}

	public int delete(String id) {
		return this.userDao.delete(id);
	}

	public User findUser(String id) {
		return this.userDao.findUser(id);
	}

	public int insert(User user) {
		return this.userDao.insert(user);
	}

	public int update(User user) {
		return this.userDao.update(user);
	}

	public List<User> findUsers(Page page) {
		return this.userDao.findUsers(page);
	}

	@Override
	public User findUserByLoginname(String loginname) {
		return this.userDao.findUserByLoginname(loginname);
	}

}