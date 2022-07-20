package com.zods.smart.iot.gunrfid.server.protocal.base;

import com.zods.smart.iot.gunrfid.server.protocal.GunRfidPacketHead;

/**
 * @Description:GUN-RFID设备在线信息报文
 * @create Author:jianglong
 * @create Date:2022-06-11
 * @version V1.0
 */
public class GunRfidOnline extends GunRfidPacketHead {

    //命令编码
    public int getCommandType() {
        return 0x33;
    }
}
