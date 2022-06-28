package com.zods.smart.iot.modules.device.service;
import com.zods.plugins.db.curd.service.ZodsBaseService;
import com.zods.smart.iot.modules.device.entity.Device;
import com.zods.smart.iot.modules.device.params.DeviceParam;
/**
 * @desc 设备管理-service
 * @author jianglong
 * @date 2022-06-28
 **/
public interface DeviceService extends ZodsBaseService<DeviceParam, Device> {

     Device selectElecDevice(String deviceCode) throws Exception;

     Integer updateDeviceOnlineStatusByDeviceCode(String deviceCode, Integer isOnline) throws Exception;

}
