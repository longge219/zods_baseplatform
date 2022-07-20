package com.zods.smart.iot.gunrfid.command.controller.param;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @desc GUN-RFID获取工作天线指令参数
 * @author jianglong
 * @date 2022-07-20
 **/
@Data
public class SetAntParams {

    @ApiModelProperty(value = "RFID读卡器IP")
    private String deviceIp;

    @ApiModelProperty(value = "天线号集，位表示天线是否启用(0:不启用，1启用)，共16位，最多16个天线。例:0000000000000011代表12号天线启用")
    private String ants;
}
