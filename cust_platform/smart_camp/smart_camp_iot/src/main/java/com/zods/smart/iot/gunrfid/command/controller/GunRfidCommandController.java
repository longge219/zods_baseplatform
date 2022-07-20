package com.zods.smart.iot.gunrfid.command.controller;
import com.zods.plugins.db.bean.ResponseBean;
import com.zods.smart.iot.gunrfid.command.controller.param.SetAntParams;
import com.zods.smart.iot.gunrfid.command.service.GunRfidCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        String res =  gunRfidCommandService.setAnts(setAntParams.getDeviceIp(),setAntParams.getAnts());
        return ResponseBean.builder().message(res).build();
    }

}
