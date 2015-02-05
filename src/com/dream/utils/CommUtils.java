package com.dream.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.dream.base.Constant;
import com.dream.base.Page;

/**
 * 常用工具类
 * @author anan
 *
 */
public class CommUtils {

    /**
     * @param fileName 文件地址
     * @return 属性文件
     */
    public static Properties getProperties(String fileName) {
        Properties prop = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream(fileName);
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
    
    /**
     * 
     * @param request 请求
     * @return 从 request 获取到参数
     */
    public static HashMap<String, Object> getParams(HttpServletRequest request) {
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String pName = paramNames.nextElement();
            String value = request.getParameter(pName);
            params.put(pName, value);
        }
        
        
        return params;
    }
    
    /**
     * 
     * @param pageStr 分页的json的数据
     * @return 转换成的page对象
     */
    public static Page<?> getPage(String pageStr) {
    	Page<?> page = new Page();
		try {
			JSONObject jObject = JSONObject.fromObject(pageStr);
			
	        Iterator<?> keys = jObject.keys();

	        while( keys.hasNext() ){
	            String key = (String)keys.next();
	            String value = jObject.getString(key); 
	            
	            if (key.equalsIgnoreCase("pageNo")) {
	            	page.setPageNo(Integer.parseInt(value));
	            } else if (key.equalsIgnoreCase("pageSize")) {
	            	page.setPageSize(Integer.parseInt(value));
	            } else if (key.equalsIgnoreCase("totalPage")) {
	            	page.setTotalPage(Integer.parseInt(value));
	            } else if (key.equalsIgnoreCase("totalRecord")) {
	            	page.setTotalRecord(Integer.parseInt(value));
	            }
	        }
			
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	
    	return new Page();
    }

    
    /**
     * 
     * @param summary
     * @return
     */
	public static String getText(String htmlStr) {
        String textStr ="";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

       try{
             String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
             String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
             String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

             p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
             m_script = p_script.matcher(htmlStr);
             htmlStr = m_script.replaceAll(""); //过滤script标签

             p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
             m_style = p_style.matcher(htmlStr);
             htmlStr = m_style.replaceAll(""); //过滤style标签

             p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
             m_html = p_html.matcher(htmlStr);
             htmlStr = m_html.replaceAll(""); //过滤html标签

             textStr = htmlStr;
        }catch(Exception e){
            e.printStackTrace();
        }
        return textStr;//返回文本字符串
	}
    
    /**
     * 
     * @param articleId 文章的ID
     * @return 相对路径
     */
	public static String getArticleLocal(String articleId) {
		return Constant.PATH_SEPARATOR + "html" + Constant.PATH_SEPARATOR  + "article" 
				+ Constant.PATH_SEPARATOR + articleId + ".html";
	}

	
    /**
     * 
     * @param articleId 文章的ID
     * @return 相对路径
     */
	public static String getArticleLocalDir() {
		return Constant.PATH_SEPARATOR + "html" + Constant.PATH_SEPARATOR  + "article" 
				+ Constant.PATH_SEPARATOR;
	}
}
