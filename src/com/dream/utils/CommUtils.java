package com.dream.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
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
    
    

}
