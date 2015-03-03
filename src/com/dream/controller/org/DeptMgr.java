package com.dream.controller.org;

import java.util.ArrayList;
import java.util.List;

import com.dream.base.Constant;
import com.dream.base.Page;
import com.dream.base.TreeBean;
import com.dream.model.org.Dept;
import com.dream.service.org.DeptService;
import com.dream.start.CacheMgr;
import com.dream.utils.SpringContextUtil;

public class DeptMgr {

	private static DeptService deptService = SpringContextUtil.getBean("deptService");
	
	/**
	 * 
	 * @param deptCode 部门编码
	 * @return 部门
	 */
	public static Dept getDept(String deptCode) {
		
		Dept dept = (Dept) CacheMgr.getInstance().get(deptCode, Constant.CACHE_TYPE_DEPT);
		if (null != dept) {
			return dept;
		}
		
		dept = deptService.findDept(deptCode);
		
		if (null != dept) {
			CacheMgr.getInstance().set(deptCode, dept, Constant.CACHE_TYPE_DEPT);	
		}
		
		return dept;
	}
	
	
	/**
	 * 清除缓存
	 * @param deptCode 部门编码
	 */
	public static void clearCache(String deptCode) {
		CacheMgr.getInstance().remove(deptCode, Constant.CACHE_TYPE_DEPT);
	}
	
	/**
	 * 添加缓存
	 * @param deptCode 部门编码
	 * @param dept 部门对象
	 */
	public static void putCache(String deptCode, Dept dept) {
		clearCache(deptCode);
		
		CacheMgr.getInstance().set(deptCode, dept, Constant.CACHE_TYPE_DEPT);
	}

	
	/**
	 * 
	 * @param deptCode
	 * @return
	 */
	public static List<TreeBean> getDeptList() {
		
		List<TreeBean> deptList = (List<TreeBean>) CacheMgr.getInstance().get(Constant.CACHE_TYPE_DEPT_LIST, Constant.CACHE_TYPE_DEPT);
		
		if (null == deptList) {
			Page page = new Page();
			page.setPageSize(Constant.QUERY_COUNT_MAX);
			page.setOrder("dlevel , dsort");
			
			List<Dept> depts = deptService.findDepts(page);
			deptList = new ArrayList<TreeBean>();
			for (Dept dept: depts) {
				TreeBean treeBean = new TreeBean();
				treeBean.setId(dept.getCode());
				treeBean.setpId(dept.getPcode());
				treeBean.setName(dept.getName());
				
				deptList.add(treeBean);
			}
			
			CacheMgr.getInstance().set(Constant.CACHE_TYPE_DEPT_LIST, deptList, Constant.CACHE_TYPE_DEPT);
		}
		
		return deptList;
	}
	
	
	/**
	 * 清除缓存
	 */
	public static void clearCacheList() {
		CacheMgr.getInstance().remove(Constant.CACHE_TYPE_DEPT_LIST, Constant.CACHE_TYPE_DEPT);
	}
}
