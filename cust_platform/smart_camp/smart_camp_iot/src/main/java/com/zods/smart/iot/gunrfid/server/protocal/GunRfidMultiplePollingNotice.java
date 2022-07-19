package com.zods.smart.iot.gunrfid.server.protocal;
import com.zods.smart.iot.common.reflect.SubAnnotation;

/**
 * @Description:GUN-RFID多次轮询通知
 * @create Author:jianglong
 * @create Date:2022-07-18
 * @version V1.0
 */
public class GunRfidMultiplePollingNotice extends GunRfidPacketHead {

    //命令编码
    public int getCommandType() {
        return 0x27;
    }

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte ant;//天线号 ANT

    @SubAnnotation(len = "2", name = "", type = "short",mark="")
    private short pl ;//指令参数长度 PL--0x0011

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte rssi ;//RSSI-0xC9

    @SubAnnotation(len = "2", name = "", type = "short",mark="")
    private short pc ;//pc

    @SubAnnotation(len = "12", name = "", type = "byteArray",mark="")
    private byte[] epc; //EPC

    @SubAnnotation(len = "2", name = "", type = "short",mark="")
    private short crc ;//CRC

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte checkSum; //校验位Checksum

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte end; //结束符
}
