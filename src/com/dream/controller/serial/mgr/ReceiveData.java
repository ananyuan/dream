package com.dream.controller.serial.mgr;

import java.util.PriorityQueue;
import java.util.Queue;

public class ReceiveData {

	private static final int RECEIVE_DATA_COUNT = 10;
	
	private Queue<String> receiveData = new PriorityQueue<String>();
	
	/**
	 * 
	 * @param data 加入的数据
	 */
	public void push(String data) {
		if (receiveData.size() > RECEIVE_DATA_COUNT) {
			pop();
		}
		
		receiveData.add(data);
	}
	
	/**
	 * 
	 * @return 获取数据并移除
	 */
	public String pop() {
		return receiveData.poll();
	}
}
