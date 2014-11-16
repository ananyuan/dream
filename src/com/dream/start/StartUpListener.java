package com.dream.start;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dream.base.Context;

/**
 * 系统启动
 * @author anan
 *
 */
public class StartUpListener implements ServletContextListener {


	@Override
	public void contextInitialized(ServletContextEvent servContext) {
	
        // 加载配置参数
        System.out.println(".........................................................");
        System.out.println("正在启动系统 ... ...");
        ServletContext sc = servContext.getServletContext();
        // 获取系统真实路径
        String systemPath = sc.getRealPath("/");
        
        if (!systemPath.endsWith(File.separator)) {
            systemPath += File.separator;
        }
        
        String contextPath = sc.getContextPath();
        if (contextPath.equals("/")) {
            contextPath = "";
        } else if (contextPath.endsWith("/")) {
            contextPath = contextPath.substring(0, contextPath.length() - 1);
        }
        
        System.out.println("系统工作目录: " + systemPath);
        System.out.println("系统服务路径: " + contextPath);
        
        
        System.out.println("系统初始化完毕，开始接收请求！");
        System.out.println(".........................................................");
        
        Context.setSYSPATH(systemPath);
        
        Context.setWEBINF(systemPath  + "WEB-INF" + File.separator);
        
        //系统启动项
        InitLogger.start(Context.getWEBINF());
	}

	

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		CacheMgr.shutdown();
	}
}
