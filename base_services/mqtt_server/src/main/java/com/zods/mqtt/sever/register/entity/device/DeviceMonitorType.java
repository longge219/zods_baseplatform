package com.zods.mqtt.sever.register.entity.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ：Liu cp
 * @Date : Created in 2021-08-27 11:08:41
 * @Description : 设备监测方法
 * Modify By : Liu cp
 * @Version : $
 */
@Data
public class DeviceMonitorType implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String deviceCode;

    private String monitorTypeCode;

    private Integer sn;
}
