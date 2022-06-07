package com.zods.mqtt.sever.business.service;
import com.zods.mqtt.sever.business.entity.DeviceLogin;
import com.zods.mqtt.sever.business.response.ResponseModel;

public interface DeviceService {

    /**
     * 设备连接鉴权
     *
     * @param deviceLogin
     * @return
     * @throws Exception
     */
    ResponseModel<String> connectAuth(DeviceLogin deviceLogin) throws Exception;

}
