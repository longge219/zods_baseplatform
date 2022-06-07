package com.zods.mqtt.sever.business.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.zods.mqtt.sever.business.config.TopicProperties;
import com.zods.mqtt.sever.business.service.BusinessService;
import com.zods.mqtt.sever.business.service.PayloadService;
import com.zods.mqtt.sever.protocol.client.MqttClientProperties;
import com.zods.mqtt.sever.protocol.code.PayloadDecode;
import com.zods.mqtt.sever.protocol.code.protocol.Payload_1;
import com.zods.mqtt.sever.protocol.code.protocol.Payload_2;
import com.zods.mqtt.sever.protocol.code.protocol.Payload_3;
import com.zods.mqtt.sever.protocol.code.protocol.ProtocolHead;
import com.zods.mqtt.sever.protocol.common.util.HexStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * @author jianglong
 * @description MQTT控制报文业务处理
 * @create 2019-10-09
 **/
@Service("businessServiceImpl")
@Slf4j
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private MqttClientProperties consoleProperties;

    @Resource(name = "payload1ServiceImpl")
    private PayloadService payload1Service;

    @Resource(name = "payload2ServiceImpl")
    private PayloadService payload2Service;

    @Resource(name = "payload3ServiceImpl")
    private PayloadService payload3Service;

    @Resource
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private TopicProperties topicProperties;

    /**
     * @param channelId 通道id
     * @param deviceId  设备ID
     * @param isConnect 是上线还是掉线
     * @return boolean
     * @description: connect、diconnect报文业务处理
     */
    @Override
    public void doLinePacket(String channelId, String deviceId, boolean isConnect) {
        if (!deviceId.equalsIgnoreCase(consoleProperties.getAppCode())) {
            log.info("channelId【{}】, deviceId【{}】  is {}", channelId, deviceId, isConnect == false ? "downLine" : "online");
        }
    }

    /**
     * @param topic   主题
     * @param payload 消息内容
     * @return boolean
     * @description: publish报文业务处理
     */
    @Override
    public boolean doPublishPacket(String deviceId, String topic, byte[] payload) {
        log.info("deviceId【{}】 publish message 【{}】 to topic【{}】", deviceId, new String(payload, Charset.forName("UTF-8")), topic);
        log.info(HexStringUtils.parseByte2HexStr(payload));
        log.info("----------------------------------payload解码前------------------------------------------");
        if (StringUtils.isBlank(topic) || payload == null || payload.length == 0 || deviceId.equalsIgnoreCase(consoleProperties.getAppCode())) {
            return false;
        } else {
            PayloadDecode payloadDecode = new PayloadDecode();
            try {
                ProtocolHead protocolHead = payloadDecode.decode(payload);
                log.info("-------------------------------------------------payload解码后----------------------------------------------");
                if (!Objects.isNull(protocolHead)) {
                    JSONObject topicData = null;
                    if (protocolHead instanceof Payload_1) {
                        topicData = payload1Service.toTopicData(deviceId, protocolHead);
                    }
                    if (protocolHead instanceof Payload_2) {
                        topicData = payload2Service.toTopicData(deviceId, protocolHead);
                    }
                    if (protocolHead instanceof Payload_3) {
                        topicData = payload3Service.toTopicData(deviceId, protocolHead);
                    }
                    if (!topicData.isEmpty()) {
                        // 异步顺序发送
                        String topicName = topicData.getString("topicName");
                        topicData.remove("topicName");
                        log.info("send data to kafka {}",topicData);
                        kafkaTemplate.send(topicName, deviceId, topicData.toString());
                    }
                } else {
                    log.error("未定义的消息类型");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
