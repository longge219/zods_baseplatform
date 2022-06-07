package com.zods.mqtt.sever.business.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import java.io.Serializable;
/**
 * @Author ：jianglong
 * @Date : 2022-06-07
 * @Description :设备信息
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
}
