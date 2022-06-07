package com.zods.mqtt.sever.business.service;
import com.alibaba.fastjson.JSONObject;
import com.zods.mqtt.sever.business.config.TopicProperties;
import com.zods.mqtt.sever.protocol.code.protocol.ProtocolHead;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
/**
 * @description payload抽象类
 * @author jianglong
 * @create 2019-10-09
 **/
public abstract class AbstractPayloadService implements PayloadService {

    @Autowired
    public TopicProperties topicProperties;

    @Override
    public abstract JSONObject toTopicData(String deviceId, ProtocolHead protocolHead);

    public JSONObject toGnssStateTopicData(String publishDataStr, String deviceId) {
        JSONObject topicData = new JSONObject();
        topicData.put("acqTime", new Date());
        topicData.put("deviceId", deviceId);
        topicData.put("body", publishDataStr);
        return topicData;
    }
}
