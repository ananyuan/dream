package com.dream.start;

import com.dream.controller.serial.mgr.PortMonitor;

/**
 * 系统启动第三方的
 * @author anan
 *
 */
public class InitThirdParty {
	
	public static void start() {
		//串口监控的线程
		PortMonitor monitor = new PortMonitor();
		Thread backThread = new Thread(monitor);
		backThread.start();
	}
}
