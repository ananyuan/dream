package com.dream.controller;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.base.Constant;
import com.dream.base.Page;
import com.dream.model.WfBaseBean;
import com.dream.model.wf.StepBean;
import com.dream.service.wf.WfAct;
import com.dream.utils.DataTableReturnObject;
import com.dream.utils.JSONParam;

/**
 * 所有 需要显示datatable列表的父类 
 * @author anan
 *
 */
public class AbsController {
	
	private static Log log = LogFactory.getLog(AbsController.class);
	
	private static final String POJO_PREFIX_GET = "get";
	
	private static final String POJO_PREFIX_IS = "is";
	
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
	public DataTableReturnObject search(@RequestBody JSONParam[] params, HttpServletRequest request, HttpSession session) throws IllegalAccessException, InvocationTargetException {
		HashMap<String, String> map = convertToMap(params);
		String sEcho = map.get("sEcho");
		
		Page page = getPageFromReq(map);
		
		ListPageData listPage = new ListPageData(page);
		setRtnDataList(map, listPage, session);
		
		int count = page.getTotalRecord();
		List<Map<String, Object>> rtnList = listPage.getRtnList();
		if (null == rtnList) {
			rtnList = new ArrayList<Map<String, Object>>();
		}
		
		//调整序号
		int iDisplayStart = Integer.parseInt(map.get("iDisplayStart")); 
		for (Map<String, Object> base: rtnList) {
			base.put(Constant.COLUMN_XUHAO, ++iDisplayStart);
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
	protected void setRtnDataList(HashMap<String, String> reqMap, ListPageData listPage, HttpSession session) {
	}
	
	/**
	 * 
	 * @param pojo 将pojo类转换成Map
	 * @return map
	 */
	protected Map<String, Object> pojoToMap(Object pojo) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		try {
			Class<? extends Object> classz = pojo.getClass();
			BeanInfo info = Introspector.getBeanInfo(classz);

			PropertyDescriptor[] props = info.getPropertyDescriptors();

			for (PropertyDescriptor pd : props) {
				Method method = pd.getReadMethod();
				if (method.getName().startsWith(POJO_PREFIX_GET) || method.getName().startsWith(POJO_PREFIX_IS)) {
					String propName = pd.getDisplayName();
					hashMap.put(propName, method.invoke(pojo, new Object[0]));	
				}
			}
		} catch (Throwable e) {
			log.error("pojo to map", e);
		}
		return hashMap;
	}
	
	/**
	 * 
	 * @param wfBase 流程公共的字段
	 * @param modelCode 模块名字
	 * @param niid 节点实例ID
	 * @return 能走的下一步
	 */
	public List<StepBean> getNextSteps(WfBaseBean wfBase, String modelCode, int niid) {
		//TODO 判断当前人 正在办理才添加
		
		WfAct wfAct = new WfAct(niid);
		
		return wfAct.getNextSteps();
	}
}



