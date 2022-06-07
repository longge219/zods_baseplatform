package com.zods.mqtt.sever.register.entity.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ：Liu cp
 * @Date : Created in 2021-08-27 11:08:29
 * @Description : 监测类型
 * Modify By : Liu cp
 * @Version : $
 */
@Data
public class MonitorContent implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private String desc;

}
