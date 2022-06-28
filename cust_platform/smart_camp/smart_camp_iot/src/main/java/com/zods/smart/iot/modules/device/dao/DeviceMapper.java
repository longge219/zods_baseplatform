package com.zods.smart.iot.modules.device.dao;
import com.zods.plugins.db.curd.mapper.ZodsBaseMapper;
import com.zods.smart.iot.modules.device.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @desc 设备-Dao
 * @author jianglong
 * @date 2022-06-28
 **/
@Mapper
public interface DeviceMapper extends ZodsBaseMapper<Device> {

    @Select("select A.* from  object_device as A where A.device_code = #{deviceCode}  AND  (A.device_type='15' OR A.device_type='18' OR A.device_type='19') AND A.is_delete =false")
    Device selectElecDevice(String deviceCode);

}
