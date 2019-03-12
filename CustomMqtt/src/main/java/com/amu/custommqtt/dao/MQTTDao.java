package com.amu.custommqtt.dao;

public interface MQTTDao {
	
	public void startMqtt();

	public void publish(String topicSuffix, String content);
}
