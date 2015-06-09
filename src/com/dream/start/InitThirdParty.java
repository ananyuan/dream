package com.dream.start;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dream.base.Context;
import com.dream.controller.serial.mgr.PortMonitor;

/**
 * 系统启动第三方的
 * @author anan
 *
 */
public class InitThirdParty {
	
	private static Log log = LogFactory.getLog(InitThirdParty.class);
	
	
	public static void start() {
		
		setLibPath();
		
		//串口监控的线程
		PortMonitor monitor = new PortMonitor();
		Thread backThread = new Thread(monitor);
		backThread.start();
	}

	/**
	 * 设置 java.library.path
	 */
	private static void setLibPath() {
		String pathSeparator = System.getProperty("path.separator");
		
		String libPath = System.getProperty("java.library.path") + pathSeparator + Context.getWEBINF() + "/lib";

		try {
			System.setProperty("java.library.path", libPath);
			Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
			fieldSysPath.setAccessible(true);
			fieldSysPath.set(null, null);
			System.loadLibrary("rxtxSerial");
		} catch (Exception e) {
			log.error("set java.library.path ERROR", e);
		}
	}
}
