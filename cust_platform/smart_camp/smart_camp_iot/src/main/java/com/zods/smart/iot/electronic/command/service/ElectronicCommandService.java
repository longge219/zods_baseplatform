package com.zods.smart.iot.electronic.command.service;
import com.zods.smart.iot.electronic.command.controller.param.ElectronicCommandParams;
/**
 * @desc 电子围栏布防、撤防命令-服务接口
 * @author jianglong
 * @date 2022-06-30
 **/
public interface ElectronicCommandService {

    /**设备布防撤防控制*/
    boolean sidedefenseCmd(ElectronicCommandParams cmdParams);

}
