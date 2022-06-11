package com.zods.smart.iot.electronic.server.protocal;

/**
 * @Description:设备在线信息回执报文
 * @create Author:jianglong
 * @create Date:2022-06-11
 * @version V1.0
 */
public class OnlineReturn extends PacketHead {

    //命令编码
    public int getCommandType() {
        return 0x81;
    }
}
