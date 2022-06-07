package com.zods.mqtt.sever.protocol.code.reflect;
import com.zods.mqtt.sever.protocol.code.protocol.Payload_1;
import com.zods.mqtt.sever.protocol.code.protocol.Payload_2;
import com.zods.mqtt.sever.protocol.code.protocol.Payload_3;
import java.util.HashMap;
import java.util.Map;
/**
 * @version V1.0
 * @Description:封装报文与报文id对应集合
 * @create Author:jianglong
 * @create Date:2019-09-11
 */
public abstract class ClassMap {
    protected static final Map<Integer, ClassHandler> map = new HashMap<Integer, ClassHandler>();

    protected void init() {
        map.put(1, new ClassHandler(Payload_1.class));
        map.put(2, new ClassHandler(Payload_2.class));
        map.put(3, new ClassHandler(Payload_3.class));
    }

}
