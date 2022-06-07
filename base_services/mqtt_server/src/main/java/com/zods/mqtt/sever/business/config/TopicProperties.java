package com.zods.mqtt.sever.business.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "topic")
@Data
public class TopicProperties {

    /**普通设备数据类型3上报主题*/
    private String deviceDataTypeOneTopic;

    /**普通设备数据类型4上报主题*/
    private String deviceDataTypeTwoTopic;

    /**普通设备数据类型5上报主题*/
    private String deviceDataTypeThreeTopic;

    /**下行命令主题*/
    private String cmdDownWardsTopic;

    /**下行命令响应主题*/
    private String cmdDownWardsResponseTopic;

    /**平台下发升级固件主题*/
    private String firmwareUpgradeTopic;

    /**设备发送待升级固件大小主题*/
    private String firmwareSupportSizeTopic;

}
