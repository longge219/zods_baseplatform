package com.zods.smart.iot.electronic.command.service.impl;
import com.zods.smart.iot.electronic.command.controller.param.ElectronicCommandParams;
import com.zods.smart.iot.electronic.command.service.ElectronicCommandService;
import com.zods.smart.iot.electronic.server.protocal.ElectronicSidedeFenseCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
/**
 * @desc 电子围栏布防、撤防命令-服务接口实现
 * @author jianglong
 * @date 2022-06-30
 **/
@Service
@Slf4j
public class ElectronicCommandServiceImpl implements ElectronicCommandService {
    /**设备布防撤防控制*/
    public boolean sidedefenseCmd(ElectronicCommandParams cmdParams) {
        ElectronicSidedeFenseCmd sidedeFenseCmd = new ElectronicSidedeFenseCmd();
        sidedeFenseCmd.setPakcetLen(12);
        sidedeFenseCmd.setHostAddress(0);
        sidedeFenseCmd.setEquipAddress(1);
        sidedeFenseCmd.setUserGroupH((byte)0);
        sidedeFenseCmd.setUserGroupL((byte)0);
        sidedeFenseCmd.setExtendedStandby((byte)0);
        sidedeFenseCmd.setSecretOne();
        sidedeFenseCmd.setSecretTwo();
        sidedeFenseCmd.setSecretThree();
        sidedeFenseCmd.setSidedeFenseCmd();
         return true;
    }
}
