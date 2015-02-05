package com.dream.controller.serial.model;

/**
 * 打开串口的参数
 * @author anan
 *
 */
public class SerialParam {

	public int delayRead = 200; // 延时等待端口数据准备的时间
	public int timeout = 1000; // 超时时间
	public int dataBits = 8; // 数据位
	public int stopBits = 1; // 停止位
	public int parity = 0; // 奇偶校验
	public int rate = 9600; // 波特率
	public int getDelayRead() {
		return delayRead;
	}
	public void setDelayRead(int delayRead) {
		this.delayRead = delayRead;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public int getDataBits() {
		return dataBits;
	}
	public void setDataBits(int dataBits) {
		this.dataBits = dataBits;
	}
	public int getStopBits() {
		return stopBits;
	}
	public void setStopBits(int stopBits) {
		this.stopBits = stopBits;
	}
	public int getParity() {
		return parity;
	}
	public void setParity(int parity) {
		this.parity = parity;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	
	
}
