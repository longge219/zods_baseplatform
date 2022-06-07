package com.zods.mqtt.sever.register.service;
import com.zods.mqtt.sever.business.response.ResponseModel;
import com.zods.mqtt.sever.register.entity.deviceRegister.BaseDevice;
import com.zods.mqtt.sever.register.entity.deviceRegister.DeviceRegister;

public interface RegisterDeviceService {

    /**
     * 设备注册
     * @param deviceRegister
     * @param registerCode
     * @throws Exception
     */
    ResponseModel registerDevice(DeviceRegister deviceRegister, String registerCode, String appKey) throws Exception;

    /**
     * 查询设备最新登录参数
     * @param baseDevice
     * @return
     * @throws Exception
     */
    ResponseModel queryDevice(BaseDevice baseDevice, String registerCode, String appKey)throws Exception;
}
