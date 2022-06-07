package com.zods.mqtt.sever.protocol.common.util;
import io.netty.buffer.ByteBuf;

/**
 *@description byteBuf 需要转换成byte[]
 * @author jianglong
 * @create 2019-09-09
 **/
public class ByteBufUtil {
    public  static byte[]  copyByteBuf(ByteBuf byteBuf){
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return bytes;
    }
}
