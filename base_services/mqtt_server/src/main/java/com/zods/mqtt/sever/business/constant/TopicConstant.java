package com.zods.mqtt.sever.business.constant;
import java.io.Serializable;
/**
 * @author jianglong
 * @description 主体常量
 * @create 2019-09-29
 **/
public class TopicConstant implements Serializable {

    /**下划线*/
    public static final String SLASH = "/";

    /**固件升级主体后缀*/
    public static final String FIRMWARE_TOPIC_SUFFIX = "/firmware";

    /**系统上传数据topic*/
    public static final String DP_TOPIC = "$dp";

    /**设备回复平台下发topic*/
    public static final String DR_TOPIC = "$dr";

    /**下行指令主题*/
    public static final String DOWNWARDS_CMD_TOPIC(String deviceId){
        return "$creq/"+deviceId+"/cmd";
    }

    /**表示平台下发固件升级指令主题*/
    public static final String UPGRADE_CMD_TOPIC(String deviceId){
        return "$creq/"+deviceId+"/upgrade";
    }

    /**设备响应升级的固件大小范围*/
    public static final String SUPPORTSIZE_CMD_TOPIC(String deviceId){
        return "$creq/"+deviceId+"/supportsize";
    }

    /**表示平台发送固件数据的主题*/
    public static final String FIRMWARE_CMD_TOPIC(String deviceId){
        return "$creq/"+deviceId+"/firmware";
    }
}
