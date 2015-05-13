package com.dream.controller.serial.mgr;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.TooManyListenersException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dream.base.Constant;
import com.dream.controller.serial.model.SerialParam;
import com.dream.utils.DrThreadPool;

public class SerialPorter implements Runnable, SerialPortEventListener {

	private static Log log = LogFactory.getLog(SerialPorter.class);

	static CommPortIdentifier portId;
	private String portName = ""; // 端口名称
	private String validateReq = "";
	private String validateRes = "";
	
	int delayRead = 200;
	int numBytes; // buffer中的实际数据字节数
	private static byte[] readBuffer = new byte[4096]; // 4k的buffer空间,缓存串口读入的数据

	InputStream inputStream;
	OutputStream outputStream;
	SerialPort serialPort;
	SerialParam serialParams;
	// 端口是否打开了
	private boolean isOpen = false;
	
	private ReceiveData receiveData = new ReceiveData();
	
	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * 初始化端口操作的参数.
	 * 
	 * @throws SerialPortException
	 * 
	 * @see
	 */
	public SerialPorter(String portNum) {
		this.portName = portNum;
	}

	public void open(SerialParam params) throws Exception {
		serialParams = params;
		try {
			// 参数初始化
			int timeout = serialParams.getTimeout();
			int rate = serialParams.getRate();
			int dataBits = serialParams.getDataBits();
			int stopBits = serialParams.getStopBits();
			int parity = serialParams.getParity();
			delayRead = serialParams.getDelayRead();
			String port = portName;
			// 打开端口
			portId = CommPortIdentifier.getPortIdentifier(port);
			serialPort = (SerialPort) portId.open("SerialReader", timeout);
			inputStream = serialPort.getInputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			serialPort.setSerialPortParams(rate, dataBits, stopBits, parity);

			isOpen = true;
		} catch (PortInUseException e) {
			log.error("端口" + portName + "已经被占用", e);
			throw new Exception("端口" + portName + "已经被占用", e);
		} catch (TooManyListenersException e) {
			log.error("端口" + portName + "监听者过多");
			throw new Exception("端口" + portName + "监听者过多");
		} catch (UnsupportedCommOperationException e) {
			log.error("端口操作命令不支持");
			throw new Exception("端口操作命令不支持");
		} catch (NoSuchPortException e) {
			log.error("端口" + portName + "不存在");
			throw new Exception("端口" + portName + "不存在");
		} catch (IOException e) {
			log.error("打开端口" + portName + "失败");
			throw new Exception("打开端口" + portName + "失败");
		}

		Thread readThread = new Thread(this);
		readThread.start();
	}

	/**
	 * 
	 * @param sendData 发送的数据
	 */
	public void sendData(String sendData) {
		if (isOpen) {
			try {
				// write string to serial port
				outputStream.write(sendData.getBytes());
			} catch (IOException e) {
				log.error("sendData" + sendData, e);
			}
		}
	}

	public void initWriteToPort() {
		try {
			// get the outputstream
			outputStream = serialPort.getOutputStream();
		} catch (IOException e) {
			log.error("serialPort.getOutputStream ", e);
		}

		try {
			// activate the OUTPUT_BUFFER_EMPTY notifier
			serialPort.notifyOnOutputEmpty(true);
		} catch (Exception e) {
			log.error("Error setting event notification");
			log.error(e.toString());
		}

	}

	/**
	 * Method declaration
	 * 
	 * @see
	 */
	public void run() {
		try {
			initWriteToPort();
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

	public void close() {
		if (isOpen) {
			try {
				serialPort.notifyOnDataAvailable(false);
				serialPort.removeEventListener();
				inputStream.close();
				serialPort.close();
				isOpen = false;
			} catch (IOException ex) {
				log.error("关闭串口失败");
			}
		}
	}

	/**
	 * Method declaration
	 * 
	 * @param event
	 * @see
	 */
	public void serialEvent(SerialPortEvent event) {
		try {
			// 等待1秒钟让串口把数据全部接收后在处理
			Thread.sleep(delayRead);
			log.debug("serialEvent[" + event.getEventType() + "]    ");
		} catch (InterruptedException e) {
			log.error("serialEvent " + event.getEventType(), e);
		} 
		switch (event.getEventType()) {
			case SerialPortEvent.BI:/* Break interrupt,通讯中断 */
			case SerialPortEvent.OE:/* Overrun error，溢位错误 */
			case SerialPortEvent.FE:/* Framing error，传帧错误 */
			case SerialPortEvent.PE:/* Parity error，校验错误 */
			case SerialPortEvent.CD:/* Carrier detect，载波检测 */
			case SerialPortEvent.CTS:/* Clear to send，清除发送 */
			case SerialPortEvent.DSR:/* Data set ready，数据设备就绪 */
			case SerialPortEvent.RI:/* Ring indicator，响铃指示 */
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY: /* Output buffer is empty，输出缓冲区清空 */
				break;
			case SerialPortEvent.DATA_AVAILABLE: /* Data available at the serial port，端口有可用数据。读到缓冲数组，输出到终端 */
				try {
					// 多次读取,将所有数据读入
					while (inputStream.available() > 0) {
						numBytes = inputStream.read(readBuffer);
					}

					String result = new String(readBuffer).trim();
	
					log.debug(portName + " receive " + result);
	
					ReadSerialDataTask readTask = new ReadSerialDataTask(portName, result);
					DrThreadPool.getDefaultPool().execute(readTask);
					
					
					if (result.startsWith(Constant.SERIAL_VALIDATE_RESPONSE_PREFIX)) {
						this.setValidateRes(result);
					}
					
					
					receiveData.push(result);
				} catch (IOException e) {
					log.error("serialEvent DATA_AVAILABLE ", e);
					close();
					
					SerialPortMgr.releasePort(portName);
				}
				break;
		}
	}
	
	/**
	 * 
	 * @return 获取到的数据
	 */
	public ReceiveData getReceiveData() {
		return receiveData;
	}
	

	/**
	 * @return A HashSet containing the CommPortIdentifier for all serial ports
	 *         that are not currently being used.
	 */
	public static HashSet<CommPortIdentifier> getAvailableSerialPorts() {
		HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
		Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
		while (thePorts.hasMoreElements()) {
			CommPortIdentifier com = (CommPortIdentifier) thePorts
					.nextElement();
			switch (com.getPortType()) {
			case CommPortIdentifier.PORT_SERIAL:
				try {
					CommPort thePort = com.open("CommUtil", 50);
					thePort.close();
					h.add(com);
				} catch (PortInUseException e) {
					System.out.println("Port, " + com.getName()
							+ ", is in use.");
				} catch (Exception e) {
					System.out.println("Failed to open port " + com.getName()
							+ e);
				}
			}
		}
		return h;
	}

	public String getValidateRes() {
		return validateRes;
	}

	public void setValidateRes(String validateRes) {
		this.validateRes = validateRes;
	}

	public String getValidateReq() {
		return validateReq;
	}

	public void setValidateReq(String validateReq) {
		this.validateReq = validateReq;
	}

}
