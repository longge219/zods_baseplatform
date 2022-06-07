package com.zods.mqtt.sever.business.service;
import com.alibaba.fastjson.JSONObject;
import com.zods.mqtt.sever.protocol.code.protocol.ProtocolHead;
/**
 * @author jianglong
 * @description Payload3内容协议业务处理接口
 * @create 2019-10-24
 **/
public interface PayloadService {
    JSONObject toTopicData(String deviceId, ProtocolHead protocolHead);
}
