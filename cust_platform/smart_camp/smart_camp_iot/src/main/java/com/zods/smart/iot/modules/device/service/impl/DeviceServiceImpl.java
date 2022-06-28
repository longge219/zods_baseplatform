package com.zods.smart.iot.modules.device.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zods.plugins.db.curd.mapper.ZodsBaseMapper;
import com.zods.smart.iot.modules.device.dao.DeviceMapper;
import com.zods.smart.iot.modules.device.entity.Device;
import com.zods.smart.iot.modules.device.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @desc 文件管理-service实现
 * @author jianglong
 * @date 2022-06-28
 **/
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public ZodsBaseMapper<Device> getMapper() {
        return deviceMapper;
    }

    @Override
    public Device selectElecDevice(String deviceCode) throws Exception{
        return deviceMapper.selectElecDevice(deviceCode);
    }

    public Integer updateDeviceOnlineStatusByDeviceCode(String deviceCode, Integer isOnline) throws Exception {
        UpdateWrapper<Device> wrapper = new UpdateWrapper<>();
        wrapper.eq("device_code", deviceCode);
        wrapper.set("is_online", isOnline);
        return deviceMapper.update(null, wrapper);
    }


}
