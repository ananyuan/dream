package com.dream.controller.serial.mgr;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 采用轮询的方式去发现  hot plug event
 * @author anan
 *
 */
public class PortMonitor implements Runnable {

	private static Log log = LogFactory.getLog(PortMonitor.class);
	
	@Override
	public void run() {
		
		while(true) {
			
			Set<String> newPortSet = getPortCurrent();
			
			HashMap<String, SerialPorter> oldPortMap = SerialPortMgr.getPorts();
			
			Set<String> oldPortSet = oldPortMap.keySet();
			Iterator<String> oldPortIter = oldPortSet.iterator();
			
			String delPortsName = "";
			
			while(oldPortIter.hasNext()) { //循环旧的， 新的里面没有则删除
				String oldPortName = oldPortIter.next();
				
				if(!newPortSet.contains(oldPortName)) {
					delPortsName += oldPortName + ",";
					
					log.debug("---remove old port---" + oldPortName);
				}
			}
			
			for (String xx: delPortsName.split(",")) {
				if (xx.length() > 0) {
					SerialPortMgr.releasePort(xx);	
				}
				
			}
			
			Iterator<String> newPortIter = newPortSet.iterator();
			
			while(newPortIter.hasNext()) { //循环新的， 旧的里面没有则添加
				String newPortName = newPortIter.next();
				
				if(!oldPortSet.contains(newPortName)) {
					SerialPortMgr.addPort(newPortName);
					log.debug("---add new port---" + newPortName);
				}
			}
			
			
			try {
				Thread.currentThread().sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @return 当前的串口的数量
	 */
	private Set<String> getPortCurrent() {
		Set<String> rtnSet = new HashSet<String>();

		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier portIdentifier = (CommPortIdentifier) portEnum.nextElement();

			switch (portIdentifier.getPortType()) {
			case CommPortIdentifier.PORT_SERIAL:
				try {
					String portname = portIdentifier.getName();
					
					rtnSet.add(portname);
				} catch (Exception e) {
					log.error("Failed getPortCurrent " + portIdentifier.getName(), e);
				}
			}
		}
		
		return rtnSet;
	}
	
	
	public static void main(String[] args) {
		PortMonitor monitor = new PortMonitor();
		Thread backThread = new Thread(monitor);
		backThread.start();
	}
	
}
