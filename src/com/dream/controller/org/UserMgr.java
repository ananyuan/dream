package com.dream.controller.org;

import java.util.ArrayList;
import java.util.List;

import com.dream.base.Constant;
import com.dream.base.Page;
import com.dream.base.TreeBean;
import com.dream.model.org.User;
import com.dream.service.org.UserService;
import com.dream.start.CacheMgr;
import com.dream.utils.SpringContextUtil;

public class UserMgr {


	private static UserService userService = SpringContextUtil.getBean("userService");
	
	/**
	 * 
	 * @param userId 用户Id
	 * @return 部门
	 */
	public static User getUser(String userId) {
		
		User user = (User) CacheMgr.getInstance().get(userId, Constant.CACHE_TYPE_USER);
		if (null != user) {
			return user;
		}
		
		user = userService.findUser(userId);
		
		if (null != user) {
			CacheMgr.getInstance().set(userId, user, Constant.CACHE_TYPE_USER);	
		}
		
		return user;
	}
	
	
	/**
	 * 清除缓存
	 * @param userId 部门编码
	 */
	public static void clearCache(String userId) {
		CacheMgr.getInstance().remove(userId, Constant.CACHE_TYPE_USER);
	}
	
	
	/**
	 * 添加缓存
	 * @param userId 用户ID
	 * @param user 用户
	 */
	public static void putCache(String userId, User user) {
		clearCache(userId);
		
		CacheMgr.getInstance().set(userId, user, Constant.CACHE_TYPE_USER);
	}

	/**
	 * 
	 * @return
	 */
	public static List<TreeBean> getUserList() {
		List<TreeBean> userList = (List<TreeBean>) CacheMgr.getInstance().get(Constant.CACHE_TYPE_USER_LIST, Constant.CACHE_TYPE_USER);
		if (null == userList) {
			Page page = new Page();
			page.setPageSize(Constant.QUERY_COUNT_MAX);
			page.setOrder("usort");
			
			List<User> users = userService.findUsers(page);
			
			userList = new ArrayList<TreeBean>();
			for (User user: users) {
				TreeBean treeBean = new TreeBean();
				treeBean.setId(user.getId());
				treeBean.setpId(user.getDeptcode());
				treeBean.setName(user.getUsername());
				
				userList.add(treeBean);
				
			}
			
			CacheMgr.getInstance().set(Constant.CACHE_TYPE_USER_LIST, userList, Constant.CACHE_TYPE_USER);
		}
		
		List<TreeBean> deptList = DeptMgr.getDeptList();
		
		List<TreeBean> rtnList = new ArrayList<TreeBean>();
		rtnList.addAll(userList);
		rtnList.addAll(deptList);
		
		return rtnList;
	}
	
	/**
	 * 清除缓存
	 */
	public static void clearCacheList() {
		CacheMgr.getInstance().remove(Constant.CACHE_TYPE_USER_LIST, Constant.CACHE_TYPE_USER);
	}
	
}
