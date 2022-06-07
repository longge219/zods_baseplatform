package com.zods.mqtt.sever.register.controller;
import com.zods.mqtt.sever.business.response.ResponseModel;
import com.zods.mqtt.sever.register.entity.deviceRegister.BaseDevice;
import com.zods.mqtt.sever.register.entity.deviceRegister.DeviceRegister;
import com.zods.mqtt.sever.register.service.RegisterDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2020/9/28 10:05
 */
@Slf4j
@RestController
@RequestMapping("/api/devices")
@Api(tags = "设备")
@AllArgsConstructor
public class RegisterDeviceController {

    private final RegisterDeviceService registerDeviceService;

    /**
     * 设备注册
     *
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "设备注册")
    @ApiImplicitParams({@ApiImplicitParam(name = "registerCode", value = "授权码，默认：orieange", dataType = "string", paramType = "query")
            , @ApiImplicitParam(name = "deviceRegister", value = "设备注册实体", dataType = "DeviceRegister", paramType = "body"),
            @ApiImplicitParam(name = "appkey", dataType = "string", value = "appkey", paramType = "header")})
    public ResponseModel registerDevice(@RequestParam(value = "registerCode", required = false) String registerCode, @RequestBody DeviceRegister deviceRegister, @RequestHeader(value = "appkey", required = false) String appKey) throws Exception {
        return registerDeviceService.registerDevice(deviceRegister, registerCode, appKey);
    }

    /**
     * 设备注册
     *
     * @return
     */
    @PostMapping("/query")
    @ApiOperation(value = "设备查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "registerCode", value = "授权码，默认：orieange", dataType = "string", paramType = "query")
            , @ApiImplicitParam(name = "baseDevice", value = "设备查询实体", dataType = "BaseDevice", paramType = "body"),
            @ApiImplicitParam(name = "appkey", dataType = "string", value = "appkey", paramType = "header")})
    public ResponseModel queryDevice(@RequestParam(value = "registerCode", required = false) String registerCode, @RequestBody(required = false) BaseDevice baseDevice, @RequestHeader(value = "appkey", required = false) String appKey) throws Exception {
        return registerDeviceService.queryDevice(baseDevice, registerCode, appKey);
    }

}
