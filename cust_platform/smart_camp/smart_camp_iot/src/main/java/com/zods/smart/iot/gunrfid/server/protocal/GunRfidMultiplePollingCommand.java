package com.zods.smart.iot.gunrfid.server.protocal;
import com.zods.smart.iot.common.reflect.SubAnnotation;
/**
 * @Description:GUN-RFID多次轮询指令
 * @create Author:jianglong
 * @create Date:2022-07-18
 * @version V1.0
 */
public class GunRfidMultiplePollingCommand extends GunRfidPacketHead {

    //命令编码
    public int getCommandType() {
        return 0x27;
    }

    @SubAnnotation(len = "2", name = "", type = "short",mark="")
    private short command = 0x03;//指令参数长度

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private short reserved = 0x22;//保留位

    @SubAnnotation(len = "2", name = "", type = "short",mark="")
    private int cnt = 65535;//轮询次数 CNT(范围0-65535，当值为65535则为无限循环)

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private short checksum = 0x83; //校验位

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private short end = 0xDD; //结束符
}