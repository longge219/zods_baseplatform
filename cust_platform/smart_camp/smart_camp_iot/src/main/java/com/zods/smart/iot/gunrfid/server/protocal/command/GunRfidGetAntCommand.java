package com.zods.smart.iot.gunrfid.server.protocal.command;
import com.zods.smart.iot.common.reflect.SubAnnotation;
import com.zods.smart.iot.gunrfid.server.protocal.GunRfidPacketHead;
import lombok.Data;

/**
 * @Description:GUN-RFID获取工作天线指令
 * @create Author:jianglong
 * @create Date:2022-07-20
 * @version V1.0
 */
@Data
public class GunRfidGetAntCommand extends GunRfidPacketHead {

    //命令编码类型
    public int getCommandType() {
        return 0x00;
    }

    //命令编码
    public int getCommand() {
        return 0xA7;
    }

    @SubAnnotation(len = "2", name = "", type = "short",mark="")
    private short pl = (short)0x00;//指令参数长度PL
}
