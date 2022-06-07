package com.zods.mqtt.sever.register.entity.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ：Liu cp
 * @Date : Created in 2021-08-27 11:08:38
 * @Description : 监测方法
 * Modify By : Liu cp
 * @Version : $
 */
@Data
public class MonitorMethod implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private String desc;

    private Long contentId;
}
