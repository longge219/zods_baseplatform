package com.zods.mqtt.sever.register.entity.deviceRegister;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ：Liu cp
 * @Date : Created in 2021-04-06 13:04:53
 * @Description : 监测类型
 * Modify By : Liu cp
 * @Version : $
 */
@Data
public class MonitorType implements Serializable {

    /**监测方法类型*/
    private String type;

    /**监测方法序号*/
    private String sid;

}
