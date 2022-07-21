package com.zods.smart.iot.gunrfid.server.protocal.command;
import com.zods.smart.iot.common.reflect.SubAnnotation;
import com.zods.smart.iot.gunrfid.server.protocal.GunRfidPacketHead;
import lombok.Data;

/**
 * @Description:GUN-RFID多次轮询停止指令
 * @create Author:jianglong
 * @create Date:2022-07-18
 * @version V1.0
 */
@Data
public class GunRfidMultiplePollingStopCommand extends GunRfidPacketHead {


    //命令编码类型
    public int getCommandType() {
        return 0x00;
    }

    //命令编码
    public int getCommand() {
        return 0x28;
    }

    @SubAnnotation(len = "2", name = "", type = "short",mark="")
    private short command = 0x00;//指令参数长度

}
