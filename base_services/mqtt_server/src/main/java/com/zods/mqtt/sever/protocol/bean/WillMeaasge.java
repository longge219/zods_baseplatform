package com.zods.mqtt.sever.protocol.bean;
import lombok.Builder;
import lombok.Data;

/**
 * @description 遗嘱消息
 * @author jianglong
 * @create 2019-09-09
 **/
@Builder
@Data
public class WillMeaasge {

    private String willTopic;

    private String willMessage;
    
    private  boolean isRetain;

    private int qos;

}
