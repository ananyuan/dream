package com.dream.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.base.Constant;
import com.dream.base.Page;
import com.dream.model.Article;
import com.dream.model.BaseBean;
import com.dream.utils.DataTableReturnObject;
import com.dream.utils.JSONParam;
import com.dream.utils.UuidUtils;

/**
 * 所有 需要显示datatable列表的父类 
 * @author anan
 *
 */
public class AbsController {
	
	protected HashMap<String, String> convertToMap(JSONParam[] params) {
		HashMap<String, String> map = new HashMap<String, String>();
		for (JSONParam param : params) {
			map.put(param.getName(), param.getValue());
		}
		return map;
	}
	
	/**
	 * sEcho : Client端在每次呼叫sAjaxSource時會產生一個特定的sEcho做為驗証碼，Server端必需在JSON中回傳相同的值做為認證. 
	 * @param params
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseBody
	public DataTableReturnObject search(@RequestBody JSONParam[] params, HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		HashMap<String, String> map = convertToMap(params);
		String sEcho = map.get("sEcho");
		
		Page page = getPageFromReq(map);
		
		ListPageData listPage = new ListPageData(page);
		setRtnDataList(map, listPage);
		
		int count = page.getTotalRecord();
		List<BaseBean> rtnList = listPage.getRtnList();
		if (null == rtnList) {
			rtnList = new ArrayList<BaseBean>();
		}
		
		//调整序号
		int iDisplayStart = Integer.parseInt(map.get("iDisplayStart")); 
		for (BaseBean base: rtnList) {
			base.setXuhao(++iDisplayStart);
		}
		
		
		DataTableReturnObject dataTableRtn = new DataTableReturnObject(count, count, sEcho, rtnList);
		
		return dataTableRtn;
	}	
	
	/**
	 * 从request中获取分页对象
	 * @param reqMap
	 * @return 从request中获取分页对象
	 */
	private Page getPageFromReq(HashMap<String, String> reqMap) {
		int iDisplayStart = Integer.parseInt(reqMap.get("iDisplayStart")); 
		int iDisplayLength = Integer.parseInt(reqMap.get("iDisplayLength"));
		
		String ascOrDesc = reqMap.get("sSortDir_0");
		String colNum = reqMap.get("iSortCol_0");
		String colName = reqMap.get("mDataProp_" + colNum);
		
		Page page = new Page();
		page.setPageSize(iDisplayLength); //每页记录数
		page.setPageNo(iDisplayStart/iDisplayLength + 1); //当前页数
		page.setOrder(colName + " " + ascOrDesc);
		
		//设置上查询的值  
		Iterator<String> iterator = reqMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (key.startsWith(Constant.PREFIX_FIELD_QUERY) ) {
				page.setStr(key.replaceAll(Constant.PREFIX_FIELD_QUERY, ""), reqMap.get(key));
			}
		}
		
		return page;
	}


	/**
	 * 子类中 重载该方法
	 * @param reqMap 请求
	 * @param page 分页对象
	 * @return
	 */
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage) {
	}
	
	
    
}



