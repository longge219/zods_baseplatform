package com.zods.smart.iot.gunrfid.server.protocal.command;
import com.zods.smart.iot.common.reflect.SubAnnotation;
import com.zods.smart.iot.gunrfid.server.protocal.GunRfidPacketHead;
import lombok.Data;

/**
 * @Description:GUN-RFID设置工作天线指令
 * @create Author:jianglong
 * @create Date:2022-07-20
 * @version V1.0
 */
@Data
public class GunRfidSetAntCommand extends GunRfidPacketHead {

    //命令编码
    public int getCommand() {
        return 0xA8;
    }

    //命令编码类型
    public int getCommandType() {
        return 0x00;
    }

    @SubAnnotation(len = "2", name = "", type = "short",mark="")
    private short pl = (short)0x03;//指令参数长度PL

    @SubAnnotation(len = "1", name = "", type = "short",mark="")
    private short ants;//天线号集 ANT，位表示天线是否启用(0:不启用，1启用)，共16位，最多16个天线

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte antstaytime = (byte)0x01;//天线驻留时间 Antstaytime，01(1~5 个时间段)，默认01
}
