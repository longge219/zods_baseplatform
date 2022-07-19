package com.zods.smart.iot.gunrfid.server.protocal;
import com.zods.smart.iot.common.reflect.SubAnnotation;

/**
 * @Description:GUN-RFID多次轮询停止指令响应
 * @create Author:jianglong
 * @create Date:2022-07-18
 * @version V1.0
 */
public class GunRfidMultiplePollingStopCommandResponse extends GunRfidPacketHead {

    //命令编码
    public int getCommandType() {
        return 0x28;
    }

    @SubAnnotation(len = "2", name = "", type = "short",mark="")
    private short command = 0x01;//指令参数长度


    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte parameter = 0x00;//指令参数 Parameter 0x00

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private short checksum = 0x2A; //校验位

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private short end = 0xDD; //结束符
}
