package com.zods.smart.iot.electronic.command.controller.param;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * @desc 布防撤防命令请求参数
 * @author jianglong
 * @date 2022-06-30
 **/
@Data
public class ElectronicCommandParams implements Serializable {


    @ApiModelProperty(value = "设备编码")
    private String deviceCode;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

}
