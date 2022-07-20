package com.zods.smart.iot.gunrfid.server.protocal.base;

import com.zods.smart.iot.gunrfid.server.protocal.GunRfidPacketHead;

/**
 * @Description:GUN-RFID设备登陆信息
 * @create Author:jianglong
 * @create Date:2022-07-18
 * @version V1.0
 */
public class GunRfidLogin extends GunRfidPacketHead {

    //命令编码
    public int getCommand() {
        return 0x44;
    }


    //命令编码类型
    public int getCommandType() {
        return 0x02;
    }
}
