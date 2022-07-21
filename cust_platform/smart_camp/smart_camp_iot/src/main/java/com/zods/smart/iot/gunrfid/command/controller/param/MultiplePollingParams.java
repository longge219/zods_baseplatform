package com.zods.smart.iot.gunrfid.command.controller.param;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @desc GUN-RFID多次轮询指令参数
 * @author jianglong
 * @date 2022-07-20
 **/
@Data
public class MultiplePollingParams {

    @ApiModelProperty(value = "RFID读卡器IP")
    private String deviceIp;

    @ApiModelProperty(value = "轮询次数 CNT(范围0-65535，当值为65535则为无限循环)")
    private int cnt;
}
