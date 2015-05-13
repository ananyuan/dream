package com.dream.controller.serial.mgr;

import gnu.io.CommPortIdentifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dream.base.Constant;
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
		return serialMap.keySet();
	}
	
	/**
	 * 
	 * @param prefix 前缀
	 * @return 匹配的串口号
	 */
	public static String getMatchPortNum(String prefix) {
		String portnum = "";
		Iterator<String> keySet = serialMap.keySet().iterator();
		while (keySet.hasNext()) {
			String portTemp = keySet.next();
			
			SerialPorter porter = serialMap.get(portTemp);
			try {
				if (porter.getValidateRes().indexOf(prefix) >= 0) {
					portnum = portTemp;
					break;
				}
			} catch (Exception e) {
				log.error("getMatchPortNum", e);
			}
		}
		
		return portnum;
	}
	
	/**
	 * 像串口写数据
	 * @param portName
	 * @param data
	 * @throws Exception 
	 */
	public static void sendData(String portName, String data) throws Exception {
		if (!serialMap.containsKey(portName)) {
			log.error(portName + " 不存在");
			throw new Exception(portName + " 不存在");
		}
		
		SerialPorter serialPorter = serialMap.get(portName);
		
		if (!serialPorter.isOpen()) {
			serialPorter.open(new SerialParam());
		}
		
		serialPorter.sendData(data);
	}

	/**
	 * 
	 * @return 所有的串口的数据信息
	 */
	public static HashMap<String, SerialPorter> getPorts() {
		return serialMap;
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
		SerialPorter rmPort = serialMap.get(portName);
		rmPort.close();
		
		serialMap.remove(portName);
	}
	
	
	/**
	 * 
	 * @param portName 串口
	 */
	public static void addPort(String portName) {
		if (!serialMap.containsKey(portName)) {
			SerialPorter porter = new SerialPorter(portName);
			serialMap.put(portName, porter);
			
			porter.sendData(Constant.SERIAL_VALIDATE_REQUEST);
		}
	}
	
	/**
	 * 
	 * @param portName 串口
	 * @throws Exception 
	 */
	public static void openPort(String portName, SerialParam params) throws Exception {
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
