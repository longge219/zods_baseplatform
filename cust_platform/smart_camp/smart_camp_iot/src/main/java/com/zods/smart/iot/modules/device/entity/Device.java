package com.zods.smart.iot.modules.device.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zods.plugins.db.curd.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @desc 设备实体类
 * @author jianglong
 * @date 2022-06-28
 **/
@Data
@TableName(keepGlobalPrefix=true, value = "object_device")
public class Device extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("设备编码")
    private String deviceCode;

    @ApiModelProperty("设备类型( 00:监控设备头；01:生物采集器；02；门禁；03一键报警器；04:动环设备；05:温湿度设备；06:智能钥匙柜；07:UPS电源；08:液压升降柱；09:照明大灯；10:烟感")
    private String deviceType;

    @ApiModelProperty("设备型号")
    private String deviceModel;

    @ApiModelProperty("设备厂商")
    private String deviceManu;

    @ApiModelProperty("设备型号")
    private String storeroomCode;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(name="isOnline",value = "是否在线（2:离线 1：在线,3:维修）",dataType = "Integer")
    private Integer isOnline;

    @ApiModelProperty("是否删除")
    private boolean isDelete;

    @ApiModelProperty("设备序列号")
    private String deviceSn;
}
