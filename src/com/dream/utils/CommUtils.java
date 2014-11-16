package com.dream.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

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
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	Page<?> page = null;
		try {
			page = objectMapper.readValue(pageStr, Page.class);
			return page;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
    
    

}
