package com.zods.smart.iot.electronic.server.protocal;
/**
 * @desc 电子围栏布防撤防应答报文
 * @author jianglong
 * @date 2022-06-30
 **/
public class ElectronicSidedeFenseCmdReturn extends ElectronicPacketHead{
    //命令编码
    public int getCommandType() {
        return 0x85;
    }
}
