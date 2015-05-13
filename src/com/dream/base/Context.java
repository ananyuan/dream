package com.dream.base;

import java.util.HashMap;

public class Context {

	
	private static String SYSPATH = "";
	
	private static String WEBINF = "";
	
	private static HashMap<String, HashMap<String, Object>> contextObj = new  HashMap<String, HashMap<String, Object>>();

	public static String getWEBINF() {
		return WEBINF;
	}

	public static void setWEBINF(String wEBINF) {
		WEBINF = wEBINF;
	}

	public static String getSYSPATH() {
		return SYSPATH;
	}

	public static void setSYSPATH(String sYSPATH) {
		SYSPATH = sYSPATH;
	}

	/**
	 * 
	 * @param contextKey 
	 * @return 临时的对象
	 */
	public static HashMap<String, Object> getContextObj(String contextKey) {
		return contextObj.get(contextKey);
	}
	
	/**
	 * 添加对象
	 * @param contextKey
	 * @param conObj
	 */
	public static void addContextObj(String contextKey, HashMap<String, Object> conObj) {
		contextObj.put(contextKey, conObj);
	}
	
	/**
	 * 根据key删除
	 * @param contextKey
	 */
	public static void removeContextObj(String contextKey) {
		contextObj.remove(contextKey);
	}
	
	public static HashMap<String, HashMap<String, Object>> getContextObj() {
		return contextObj;
	}

	public static void setContextObj(HashMap<String, HashMap<String, Object>> contextObj) {
		Context.contextObj = contextObj;
	}
	
	
	
}
