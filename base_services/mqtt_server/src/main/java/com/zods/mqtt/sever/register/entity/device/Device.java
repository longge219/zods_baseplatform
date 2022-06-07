package com.zods.mqtt.sever.register.entity.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

/**
 * @Author ：Liu cp
 * @Date : Created in 2021-04-06 13:04:45
 * @Description : 设备
 * Modify By : Liu cp
 * @Version : $
 */
@Data
@Builder
public class Device implements Serializable {

    @Tolerate
    public Device() {
    }

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 设备名称
     */
    @TableField(value = "name")
    private String deviceName;

    /**
     * 设备deviceId
     */
    private String deviceId;

    /**
     * 设备登录apiKey
     */
    @TableField(value = "device_key")
    private String deviceApiKey;

    /**
     * 设备出厂sn号
     */
    private String sn;

    /**IMEI号,从硬件读取*/
    private String imei;

    /**CCID编号,从硬件读取*/
    private String ccid;

    /**物联网卡号,从硬件读取*/
    private String iotid;

    /**设备型号，常量约定，详见设备型号表1*/
    private String model;

    /**经度，没有定位支持则不填写*/
    @TableField(value = "log")
    private String lon;

    /**纬度，没有定位支持则不填写*/
    private String lat;

    /**监测类型,按接入传感器约定对应,多种感知以逗号分隔，未识别不填*/
    private String type;

    /**接入协议（0:HTTP、1:MQTT、2:COAP；默认1）*/
    private String protocal;

    /**通讯方式（0:GPRS/3G/4G/5G、1:NB-iot;默认0）*/
    private String communication;
}
