package com.zods.mqtt.sever.protocol.client;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;
/**
 * @author jianglong
 * @description 应用MQTT客户端
 * @create 2019-03-01
 **/
@Slf4j
@Component
public class AppMqttClient {

    private MqttClientProperties mqttClientProperties;

    private static MqttClient mqttClient = null;

    private MemoryPersistence memoryPersistence = null;

    private MqttConnectOptions mqttConnectOptions = null;

    public void setConsoleProperties(MqttClientProperties mqttClientProperties) {
        this.mqttClientProperties = mqttClientProperties;
    }

    /**连接到mqtt服务器*/
    public void connect() {
        //初始化连接设置对象
        mqttConnectOptions = new MqttConnectOptions();
        //初始化MqttClient
        if (null != mqttConnectOptions) {
            //true可以安全地使用内存持久性作为客户端断开连接时清除的所有状态
            mqttConnectOptions.setCleanSession(true);
            //设置连接超时
            mqttConnectOptions.setConnectionTimeout(mqttConnectOptions.getConnectionTimeout());
            //设置心跳时间
            mqttConnectOptions.setKeepAliveInterval(mqttConnectOptions.getKeepAliveInterval());
            mqttConnectOptions.setAutomaticReconnect(true);
            //设置持久化方式
            memoryPersistence = new MemoryPersistence();
            if (null != memoryPersistence && null != mqttClientProperties.getAppCode()) {
                try {
                    mqttClient = new MqttClient(mqttClientProperties.getMqttServerIp(), mqttClientProperties.getAppCode(), memoryPersistence);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            } else {

            }
        } else {
            log.error("mqttConnectOptions对象为空");
        }
        //设置连接和回调
        if (null != mqttClient) {
            if (!mqttClient.isConnected()) {
                try {
                    log.info("创建连接:" + mqttClient.isConnected());
                    mqttClient.connect(mqttConnectOptions);
                    log.info("................id为" + mqttClient.getClientId() + "的xcnet控制台已经连接到mqtt服务器...................");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            log.error("mqttClient为空");
        }
    }

    /**关闭连接*/
    public void closeConnect() {
        //关闭存储方式
        if (null != memoryPersistence) {
            try {
                memoryPersistence.close();
            } catch (MqttPersistenceException e) {
                e.printStackTrace();
            }
        } else {
            log.error("memoryPersistence is null");
        }
        //关闭连接
        if (null != mqttClient) {
            if (mqttClient.isConnected()) {
                try {
                    mqttClient.disconnect();
                    mqttClient.close();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            } else {
                log.error("mqttClient is not connect");
            }
        } else {
            log.error("mqttClient is null");
        }
    }

    /**发布消息*/
    public void publishMessage(String pubTopic, byte[] bytes, int qos) {
        if (null != mqttClient && mqttClient.isConnected()) {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(qos);
            mqttMessage.setPayload(bytes);
            MqttTopic topic = mqttClient.getTopic(pubTopic);
            if (null != topic) {
                try {
                    MqttDeliveryToken publish = topic.publish(mqttMessage);
                    if (!publish.isComplete()) {
                        //log.info("消息发布成功");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        } else {
            reConnect();
            publishMessage(pubTopic, bytes, qos);
        }
    }

    /**重新连接*/
    public void reConnect() {
        if (null != mqttClient) {
            if (!mqttClient.isConnected()) {
                if (null != mqttConnectOptions) {
                    try {
                        mqttClient.connect(mqttConnectOptions);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                } else {
                    log.error("mqttConnectOptions is null");
                }
            } else {
                log.error("mqttClient is null or connect");
            }
        } else {
            connect();
        }

    }

    /**订阅主题*/
    public void subTopic(String topic) {
        if (null != mqttClient && mqttClient.isConnected()) {
            try {
                mqttClient.subscribe(topic, 1);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            log.error("mqttClient is error");
        }
    }


    /**清空主题*/
    public void cleanTopic(String topic) {
        if (null != mqttClient && !mqttClient.isConnected()) {
            try {
                mqttClient.unsubscribe(topic);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            log.error("mqttClient is error");
        }
    }

}
