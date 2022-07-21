package com.zods.smart.iot.gunrfid.server.protocal.response;
import com.zods.smart.iot.common.reflect.SubAnnotation;
import com.zods.smart.iot.gunrfid.server.protocal.GunRfidPacketHead;
import lombok.Data;

/**
 * @Description:GUN-RFID多次轮询响应
 * @create Author:jianglong
 * @create Date:2022-07-18
 * @version V1.0
 */
@Data
public class GunRfidMultiplePollingResponse extends GunRfidPacketHead {

    //命令编码类型
    public int getCommandType() {
        return 0x01;
    }

    //命令编码
    public int getCommand() {
        return 0xFF;
    }


    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte ant;//天线号 ANT

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte pl ;//指令参数长度 PL--0x0011

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte parameter ;//指令参数 Parameter

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte checkSum; //校验位Checksum

    @SubAnnotation(len = "1", name = "", type = "byte",mark="")
    private byte end; //结束符
}
