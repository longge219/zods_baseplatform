package com.zods.mqtt.sever.register.entity.deviceRegister;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author ：Liu cp
 * @Date : Created in 2021-04-06 13:04:53
 * @Description : 子网设备
 * Modify By : Liu cp
 * @Version : $
 */
@Data
public class ChildDevice extends BaseDevice implements Serializable {

    /**子网设备设备名称*/
    private String deviceName;

    /**子网设备监测方法*/
    private List<MonitorType> monitorTypes;

}
