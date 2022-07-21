package com.zods.smart.iot.gunrfid.command.controller;
import com.zods.plugins.db.bean.ResponseBean;
import com.zods.smart.iot.gunrfid.command.controller.param.MultiplePollingParams;
import com.zods.smart.iot.gunrfid.command.controller.param.SetAntParams;
import com.zods.smart.iot.gunrfid.command.service.GunRfidCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @desc GUN-RFID读卡器-指令接口服务
 * @author jianglong
 * @date 2022-07-20
 **/
@RestController
@RequestMapping("/gunRfidCmd")
@Api(tags = "GUN-RFID读卡器-指令接口服务")
public class GunRfidCommandController {

    @Autowired
    private GunRfidCommandService gunRfidCommandService;

    @PostMapping("/setAnts")
    @ApiOperation("设置天线号集")
    public ResponseBean setAnts(@Validated @RequestBody SetAntParams setAntParams){
        return ResponseBean.builder().message("success").data((gunRfidCommandService.setAnts(setAntParams.getDeviceIp(),setAntParams.getAnts()))).build();
    }

    @PostMapping("/selectAnts")
    @ApiOperation("查询天线号集")
    public ResponseBean selectAnts(@RequestParam String deviceIp){
        return ResponseBean.builder().message("success").data((gunRfidCommandService.selectAnts(deviceIp))).build();
    }

    @PostMapping("/multiplePolling")
    @ApiOperation("开启多次轮询")
    public ResponseBean multiplePolling(@Validated @RequestBody MultiplePollingParams mpParams){
        return ResponseBean.builder().message("success").data((gunRfidCommandService.multiplePolling(mpParams.getDeviceIp(),mpParams.getCnt()))).build();
    }

    @PostMapping("/stopMultiplePolling")
    @ApiOperation("停止多次轮询")
    public ResponseBean stopMultiplePolling(@RequestParam String deviceIp){
        return ResponseBean.builder().message("success").data((gunRfidCommandService.stopMultiplePolling(deviceIp))).build();
    }
}
