package com.zods.mqtt.sever.register.entity.deviceRegister;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ：Liu cp
 * @Date : Created in 2021-04-06 13:04:53
 * @Description : 设备注册
 * Modify By : Liu cp
 * @Version : $
 */
@Data
public class BaseDevice implements Serializable {

    /**设备烧录sn号*/
    private String sn;

}
