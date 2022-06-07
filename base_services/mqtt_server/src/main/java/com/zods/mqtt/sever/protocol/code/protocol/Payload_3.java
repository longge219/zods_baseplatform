package com.zods.mqtt.sever.protocol.code.protocol;
import com.zods.mqtt.sever.protocol.code.reflect.SubAnnotation;
import lombok.Data;
/**
 * @author jianglong
 * @description 消息类型为3的数据
 * @create 2019-09-11
 **/
@Data
public class Payload_3 implements ProtocolHead {

    @Override
    public int getProtocolHead() {
        return 5;
    }

    /**
     * 字符串长度
     */
    @SubAnnotation(type = "uint", startPos = 1, len = 2, mark = "nextLength", className = "")
    private int jsonStrLength;

    /**
     * 字符串长度
     */
    @SubAnnotation(type = "json", startPos = 3, len = 0, mark = "lenByNextLength", className = "")
    private String publishDataStr;

}
