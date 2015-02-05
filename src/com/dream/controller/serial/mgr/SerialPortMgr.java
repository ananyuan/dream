package com.dream.controller.serial.mgr;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dream.controller.serial.model.SerialParam;

/**
 * 
 * @author anan
 * 
 */
public class SerialPortMgr {

	private static Log log = LogFactory.getLog(SerialPortMgr.class);

	private static HashMap<String, SerialPorter> serialMap = new HashMap<String, SerialPorter>();

	
	/**
	 * 
	 * @return 获取连接的串口
	 */
	public static Set<String> getSerailPorts() {
		Set<String> rtnSet = new HashSet<String>();

		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier portIdentifier = (CommPortIdentifier) portEnum
					.nextElement();

			switch (portIdentifier.getPortType()) {
			case CommPortIdentifier.PORT_SERIAL:
				try {
					String portname = portIdentifier.getName();
					
					rtnSet.add(portname);
					
					if (!serialMap.containsKey(portname)) {
						serialMap.put(portname, new SerialPorter(portname));	
					}
				} catch (Exception e) {
					System.err.println("Failed to open port " + portIdentifier.getName());
					log.error("Failed to open port " + portIdentifier.getName(), e);
				}
			}
		}

		return rtnSet;
	}
	
	/**
	 * 像串口写数据
	 * @param portName
	 * @param data
	 */
	public static void sendData(String portName, String data) {
		if (!serialMap.containsKey(portName)) {
			getSerailPorts();
		}
		
		SerialPorter serialPorter = serialMap.get(portName);
		
		if (!serialPorter.isOpen()) {
			serialPorter.open(new SerialParam());
		}
		
		serialPorter.sendData(data);
	}

	/**
	 * 
	 * @param portName
	 * @return
	 */
	public static SerialPorter getSerialPorter(String portName) {
		return serialMap.get(portName);
	}
	
	/**
	 * 
	 * @param portName 串口
	 */
	public static void releasePort(String portName) {
		serialMap.remove(portName);
		
		getSerailPorts();
	}
	
	/**
	 * 
	 * @param portName 串口
	 */
	public static void openPort(String portName, SerialParam params) {
		SerialPorter serialPoter = serialMap.get(portName);
		if (!serialPoter.isOpen()) {
			serialPoter.open(params);
		}
	}
	
	
	/**
	 * 
	 * @param portType
	 * @return
	 */
	private static String getPortTypeName(int portType) {
		switch (portType) {
		case CommPortIdentifier.PORT_I2C:
			return "I2C";
		case CommPortIdentifier.PORT_PARALLEL:
			return "Parallel";
		case CommPortIdentifier.PORT_RAW:
			return "Raw";
		case CommPortIdentifier.PORT_RS485:
			return "RS485";
		case CommPortIdentifier.PORT_SERIAL:
			return "Serial";
		default:
			return "unknown type";
		}
	}

}
