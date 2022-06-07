package com.zods.mqtt.sever.protocol.channel.impl;
import com.zods.mqtt.sever.protocol.bean.WillMeaasge;
import com.zods.mqtt.sever.protocol.channel.MqttChannelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @author jianglong
 * @description 遗嘱消息处理
 * @create 2019-09-09
 **/
@Slf4j
public class WillService {

    @Autowired
    MqttChannelService mqttChannelServiceImpl;


    //缓存的遗言消息
    private static ConcurrentHashMap<String, WillMeaasge> willMeaasges = new ConcurrentHashMap<>();

    /**
     * 保存遗嘱消息
     */
    public void save(String deviceid, WillMeaasge build) {
        willMeaasges.put(deviceid, build);
    }

    /**
     * 删除遗嘱消息
     */
    public void del(String deviceid) {
        willMeaasges.remove(deviceid);
    }

    /**
     * 客户端断开连接后 开启遗嘱消息发送
     */
    public void doSend(String deviceId) {
        if (StringUtils.isNotBlank(deviceId) && (willMeaasges.get(deviceId)) != null) {
            WillMeaasge willMeaasge = willMeaasges.get(deviceId);
            mqttChannelServiceImpl.sendWillMsg(willMeaasge);
            if (!willMeaasge.isRetain()) {
                willMeaasges.remove(deviceId);
                log.info("will message[" + willMeaasge.getWillMessage() + "] is removed");
            }
        }
    }

}
