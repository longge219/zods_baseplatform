package com.zods.smart.iot.electronic.command.controller;
import com.zods.plugins.db.bean.ResponseBean;
import com.zods.smart.iot.electronic.command.controller.param.ElectronicCommandParams;
import com.zods.smart.iot.electronic.command.service.ElectronicCommandService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @desc 电子围栏布防、撤防命令
 * @author jianglong
 * @date 2022-06-30
 **/
@RestController
@RequestMapping("/electronicCmd")
@Api(tags = "电子围栏布防、撤防命令接口")
public class ElectronicCommandController {

    @Autowired
    private ElectronicCommandService electronicCommandService;

    /**设备布防撤防控制*/
    @PostMapping("/sidedefenseCmd")
    public ResponseBean sidedefenseCmd(@Validated @RequestBody ElectronicCommandParams cmdParams){
        return ResponseBean.builder().message("success").data((electronicCommandService.sidedefenseCmd(cmdParams))).build();
    }

}
