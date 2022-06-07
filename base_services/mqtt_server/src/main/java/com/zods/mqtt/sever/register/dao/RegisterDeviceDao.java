package com.zods.mqtt.sever.register.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zods.mqtt.sever.register.entity.device.Device;
import org.apache.ibatis.annotations.Mapper;

/**
 *设备
 */
@Mapper
public interface RegisterDeviceDao extends BaseMapper<Device> {

}