package com.zods.mqtt.sever.business.entity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import java.io.Serializable;
/**
 * @Author ：jianglong
 * @Date : 2022-06-07
 * @Description :设备登陆鉴权信息
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
