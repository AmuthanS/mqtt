# MQTT java Springboot Maven Project

A simple springboot example for connect MQTT and publish messages throw Rest Services 


## Dependencies

Paho MQTT v3

```
<dependency>
			<groupId>org.eclipse.paho</groupId>
			<artifactId>org.eclipse.paho.client.mqttv3</artifactId>
			<version>1.2.1</version>
</dependency>
```    
Springboot web

```
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## Configuration 

MQTTService.java class have the ip , port , credential configurations

## Run
 
Import the project into Eclipse
Click a Project  --> Click 'Run As' --> 'Spring Boot App'

Check the log  "Connection Status : " if connected it receive true "Connection Status : true"

## publish

http://localhost:8080/publish

{  "topic":"topicsuffix",
   "message":"hello MQTT"
}

## Subscribe

Subscribed messages are received in "messageArrived" method in "MQTTService.java"
