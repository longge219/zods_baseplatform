package com.zods.mqtt.sever.register.entity.deviceLogin;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

/**
 * @Author ：Liu cp
 * @Date : Created in 2021-04-06 14:04:19
 * @Description : 设备登录参数
 * Modify By : Liu cp
 * @Version : $
 */
@Data
@Builder
public class DeviceLogin implements Serializable {

    @Tolerate
    public DeviceLogin() {
    }

    private String deviceId;

    private String deviceApiKey;
}
