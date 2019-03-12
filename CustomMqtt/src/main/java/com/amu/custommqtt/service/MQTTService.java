package com.amu.custommqtt.service;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MQTTService implements MqttCallback {

	private MqttClient client = null;
	private String mqttUserName = "username", mqttPassword = "password";
	private String mqttIpAddress = "localhost";
	// String mqttIpAddress = "iot.eclipse.org";
	private boolean mqttHaveCredential = false;
	private String mqttPort = "1883";
	private String mqttTopic = "topic/rest/project/";
	Logger LOG = LoggerFactory.getLogger(getClass());

	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		LOG.info("connectionLost :" + arg0.getMessage()+" :"+arg0.toString());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		try {
			LOG.info("deliveryCompleted " );
//			LOG.info("deliveryComplete : " + arg0.getMessage().getPayload().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		// TODO Auto-generated method stub
		try {
			LOG.info("arg0 :" + arg0 + " arg1 :" + arg1.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void publish(String topicSuffix, String content) {
		MqttMessage message = new MqttMessage();
		message.setPayload(content.getBytes());
		message.setQos(2);
		try {
			String topic = mqttTopic + topicSuffix;
			if (client.isConnected()) {
				LOG.info("Connection Status :" + client.isConnected());
			}
			client.publish(topic, message);
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void startMqtt() {

		MemoryPersistence persistence = new MemoryPersistence();
		try {
			client = new MqttClient("tcp://" + mqttIpAddress + ":" + mqttPort, MqttClient.generateClientId(),
					persistence);
			// tcp://iot.eclipse.org:1883
		} catch (MqttException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MqttConnectOptions connectOptions = new MqttConnectOptions();
		connectOptions.setCleanSession(true);
		connectOptions.setMaxInflight(3000);
		connectOptions.setAutomaticReconnect(true);
		if (mqttHaveCredential) {
			connectOptions.setUserName(mqttUserName);
			connectOptions.setPassword(mqttPassword.toCharArray());
		}

		client.setCallback(this);

		// client.connect();
		// MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		try {
			IMqttToken mqttConnectionToken = client.connectWithResult(connectOptions);

			LOG.info(" Connection Status :" + mqttConnectionToken.isComplete());
			client.subscribe("#");

		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
