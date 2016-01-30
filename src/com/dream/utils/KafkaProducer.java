package com.dream.utils;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer {

	public static void sendData(String dataStr) {
		sendData(dataStr, "newTopic");
	}
	
	public static void sendData(String dataStr, String topic) {
		Properties props = new Properties();
		props.put("metadata.broker.list", "yuananan.cn:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");

		ProducerConfig config = new ProducerConfig(props);

		Producer<String, String> producer = new Producer<String, String>(config);

		KeyedMessage<String, String> data = new KeyedMessage<String, String>(
				topic, dataStr);
		producer.send(data);
		producer.close();
	}

	public static void main(String[] args) {
		KafkaProducer.sendData("{fuck}");
	}

}
