package com.dream.start;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.dream.utils.CommUtils;

public class InitLogger {
	/**
	 * 初始化log4j日志资源
	 */
	public static void start(String filePath) {
		Properties prop = CommUtils.getProperties(filePath + "log4j.properties");
        PropertyConfigurator.configure(prop);
	}
}
