package com.zods.mqtt.sever.business.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zods.mqtt.sever.business.entity.Device;
import org.apache.ibatis.annotations.Mapper;
/**
 * @author jianglong
 * @description 设备信息Dao
 * @create 2019-09-29
 **/
@Mapper
public interface DeviceDao extends BaseMapper<Device> {

}