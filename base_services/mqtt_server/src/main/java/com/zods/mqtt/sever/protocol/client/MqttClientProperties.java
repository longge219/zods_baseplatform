package com.zods.mqtt.sever.protocol.client;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * @author jianglong
 * @description MQTT客户端配置参数
 * @create 2019-03-01
 **/
@ConfigurationProperties(prefix = "mqttclient.properties")
@Component
@Data
@Order(-1)
public class MqttClientProperties {

    /**客户端应用编码*/
    private  String appCode;

    /**mqtt服务器ip*/
    private  String mqttServerIp;

    /**连接超时时间*/
    private int connectionTimeout;

    /**心跳时间*/
    private int keepAliveInterval;
}
