package com.dream.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

/**
 * freemarker解析常用类
 * @author anan
 *
 */
public class FreeMarkerUtils {

	private static final Configuration config = new Configuration();
	
    /**
     * 根据模板内容和实际数据解析生成html文本
     * @param ftlContent 模板内容
     * @param data 数据
     * @return html文本
     */
    public static String parseString(String ftlContent, Map<String, Object> data) {
        // 设置一个字符串模板加载器
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        config.setDefaultEncoding("UTF-8");
        config.setTemplateLoader(stringLoader);

        stringLoader.putTemplate("", ftlContent);
        StringWriter writer = new StringWriter();
        try {
            // 获取匿名模板
        	config.getTemplate("").process(data, writer);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return writer.toString();
    }
    
    
    /**
     * 
     * @param filePath 文件路径
     * @param fileName 模板名称
     * @param data 数据 
     * @return html文本
     */
    public static String parseString(String filePath, String fileName, Map<String, Object> data) {
    	String ftlContent = "";
    	
    	try {
    		ftlContent = FileUtils.readFileToString(new File(filePath + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	return parseString(ftlContent, data);
    }
}
