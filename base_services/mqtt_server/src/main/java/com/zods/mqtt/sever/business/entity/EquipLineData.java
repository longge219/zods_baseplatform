package com.zods.mqtt.sever.business.entity;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
/**
 * @author jianglong
 * @description 设备上线下线事件数据
 * @create 2019-10-23
 **/
@Data
public class EquipLineData implements Serializable {

    //是否是上线
    private boolean connect;

    //设备注册deviceId
    private String deviceId;

    //主题名称
    private String topicName;

    //服务器接收数据时间
    private Date acqTime;

}
