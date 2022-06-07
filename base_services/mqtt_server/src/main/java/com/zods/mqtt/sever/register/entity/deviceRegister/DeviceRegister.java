package com.zods.mqtt.sever.register.entity.deviceRegister;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author ：Liu cp
 * @Date : Created in 2021-04-06 13:04:53
 * @Description : 设备注册
 * Modify By : Liu cp
 * @Version : $
 */
@Data
public class DeviceRegister extends BaseDevice implements Serializable {

    /***************************协议必传参数*****************************************/
    /**设备名称*/
    private String deviceName;

    /**0: 单参数  1: 多参数  2: 本地组网*/
    private String deviceType;

    /**通讯方式（0:GPRS/3G/4G/5G、1:NB-iot;默认0）*/
    private String network;

    /**接入协议（0:HTTP、1:MQTT、2:COAP；默认1）*/
    private String protocal;

    /**监测方法*/
    private List<MonitorType> monitorTypes;

    /**子网设备*/
    private List<ChildDevice> childDevices;

    /*****************************以下参数为扩展参数********************************************/

    /**设备PN号,烧录后获取*/
    private String pn;

    /**IMEI号,从硬件读取*/
    private String imei;

    /**CCID编号,从硬件读取*/
    private String ccid;

    /**物联网卡号,从硬件读取*/
    private String iotid;

    /**设备型号，常量约定，详见设备型号表1*/
    private String model;

    /**经度，没有定位支持则不填写*/
    private String lon;

    /**纬度，没有定位支持则不填写*/
    private String lat;

}
