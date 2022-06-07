package com.zods.mqtt.sever.business.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zods.mqtt.sever.business.exception.XcHandlerException;
import com.zods.mqtt.sever.business.service.AbstractPayloadService;
import com.zods.mqtt.sever.protocol.code.protocol.Payload_1;
import com.zods.mqtt.sever.protocol.code.protocol.ProtocolHead;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author jianglong
 * @description Payload3内容协议业务处理接口实现
 * @create 2019-10-24
 **/
@Service("payload1ServiceImpl")
@Slf4j
public class Payload1ServiceImpl extends AbstractPayloadService {

    /**
     * Payload_3消息体处理
     */
    @Override
    public JSONObject toTopicData(String deviceId, ProtocolHead protocolHead) {
        String publishDataStr = ((Payload_1) protocolHead).getPublishDataStr();
        if (StringUtils.isEmpty(publishDataStr)) {
            throw new XcHandlerException("设备deviceId[" + deviceId + "]上报消息类型[1]的消息体为空");
        }
        if (!JSON.isValid(publishDataStr)) {
            throw new XcHandlerException("设备deviceId[" + deviceId + "]上报消息类型[1]的消息体Json格式错误");
        }
        JSONObject topicData = toGnssStateTopicData(publishDataStr, deviceId);
        // 普通设备上报数据
        topicData.put("topicName", topicProperties.getDeviceDataTypeOneTopic());
        log.info("数据类型1发送到topic:{}",topicData);
        return topicData;
    }

}
