package com.zods.mqtt.sever.protocol.handler.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.zods.mqtt.sever.business.service.DeviceCmdInfoService;
import com.zods.mqtt.sever.protocol.bean.SendMqttMessage;
import com.zods.mqtt.sever.protocol.channel.MqttChannelService;
import com.zods.mqtt.sever.protocol.common.enums.ConfirmStatus;
import com.zods.mqtt.sever.protocol.handler.service.MqttHandlerService;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * @author jianglong
 * @description MQTT控制报文业务处理
 * @create 2019-09-09
 **/
@Service("mqttHandlerServiceImpl")
@Slf4j
public class MqttHandlerServiceImpl implements MqttHandlerService {

    @Autowired
    private MqttChannelService mqttChannelServiceImpl;

    @Autowired
    private DeviceCmdInfoService deviceCmdInfoService;

    /**
     * @param channel            通道
     * @param mqttConnectMessage 登录报文
     * @return 登录是否成功
     * @description 收到登录报文处理
     */

    @SuppressWarnings("incomplete-switch")
    @Override
    public boolean login(Channel channel, MqttConnectMessage mqttConnectMessage) {
        log.info("login>>>>> id: " + channel.id() + " , active: " + channel.isActive() + ", open: " + channel.isOpen());
        // 客户端全局ID
        String deviceId = mqttConnectMessage.payload().clientIdentifier();
        // 登录失败业务处理
        if (StringUtils.isBlank(deviceId)) {
            return false;
        }
        // 删除历史升级记录
        deviceCmdInfoService.removePastUpgradeData(deviceId);
        return Optional.ofNullable(mqttChannelServiceImpl.getMqttChannel(deviceId))
                //已登录过且保存到了全局变量中
                .map(value -> {
                    try {
                        switch (value.getSessionStatus()) {
                            case OPEN:
                                //断电后再登录，将历史channel通讯信息挂在当前channel，并删除掉之前channel
                                mqttChannelServiceImpl.repeatLogin(channel, deviceId, mqttConnectMessage);
                                return true;
                        }
                        mqttChannelServiceImpl.loginSuccess(channel, deviceId, mqttConnectMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                    return true;
                })
                //首次登录
                .orElseGet(() -> {
                    try {
                        mqttChannelServiceImpl.loginSuccess(channel, deviceId, mqttConnectMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                    return true;
                });

    }

    /**
     * @param channel 通道
     * @return void
     * @description 收到断开连接报文处理
     */
    @Override
    public void disconnect(Channel channel) {
        mqttChannelServiceImpl.closeSuccess(channel, true);
    }

    /**
     * @param channel 通道
     * @return void
     * @description 收到心跳请求控制报文处理
     */
    @Override
    public void pong(Channel channel) {
        log.info("........................接受到心跳，通道id为：" + channel.id() + "...........................");
        if (channel.isOpen() && channel.isActive() && channel.isWritable()) {
            MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.PINGRESP, false, MqttQoS.AT_MOST_ONCE, false, 0);
            channel.writeAndFlush(new MqttMessage(fixedHeader));
        }
    }

    /**
     * @param channel              通道
     * @param mqttSubscribeMessage 订阅消息
     * @return void
     * @description 收到订阅控制报文业务处理
     */
    @Override
    public void subscribe(Channel channel, MqttSubscribeMessage mqttSubscribeMessage) {
        Set<String> topics = mqttSubscribeMessage.payload().topicSubscriptions().stream()
                .map(mqttTopicSubscription -> mqttTopicSubscription.topicName())
                .collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(topics)) {
            topics.stream().forEach(topic -> {
                log.info("subscribe topic is >>>>>>>>>>>>>>>>>>>>>>>>>> " + topic + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            });
        }
        mqttChannelServiceImpl.suscribeSuccess(mqttChannelServiceImpl.getDeviceId(channel), topics);
        /**返回订阅确认*/
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.SUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0);
        MqttMessageIdVariableHeader variableHeader = MqttMessageIdVariableHeader.from(mqttSubscribeMessage.variableHeader().messageId());
        List<Integer> grantedQoSLevels = new ArrayList<>(topics.size());
        for (int i = 0; i < topics.size(); i++) {
            grantedQoSLevels.add(mqttSubscribeMessage.payload().topicSubscriptions().get(i).qualityOfService().value());
        }
        MqttSubAckPayload payload = new MqttSubAckPayload(grantedQoSLevels);
        MqttSubAckMessage mqttSubAckMessage = new MqttSubAckMessage(mqttFixedHeader, variableHeader, payload);
        channel.writeAndFlush(mqttSubAckMessage);
    }

    /**
     * @param channel     通道
     * @param mqttMessage 取消订阅消息
     * @return void
     * @description 收到取消订阅控制报文处理
     */
    @Override
    public void unsubscribe(Channel channel, MqttUnsubscribeMessage mqttMessage) {
        List<String> topics1 = mqttMessage.payload().topics();
        mqttChannelServiceImpl.unsubscribe(mqttChannelServiceImpl.getDeviceId(channel), topics1);
        /**取消订阅确认*/
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.UNSUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0x02);
        MqttMessageIdVariableHeader variableHeader = MqttMessageIdVariableHeader.from(mqttMessage.variableHeader().messageId());
        MqttUnsubAckMessage mqttUnsubAckMessage = new MqttUnsubAckMessage(mqttFixedHeader, variableHeader);
        channel.writeAndFlush(mqttUnsubAckMessage);
    }

    /**
     * @param channel            通道
     * @param mqttPublishMessage 发布消息
     * @return void
     * @description 收到发布消息业务处理
     */
    @Override
    public void publish(Channel channel, MqttPublishMessage mqttPublishMessage) {
        mqttChannelServiceImpl.publishSuccess(channel, mqttPublishMessage);
    }

    /**
     * @param channel
     * @param mqttMessage
     * @return void
     * @description 收到消息回复确认控制报文处理
     */
    @Override
    public void puback(Channel channel, MqttMessage mqttMessage) {
        String deviceId = mqttChannelServiceImpl.getDeviceId(channel);
        JSONObject jsonObject = deviceCmdInfoService.getUpgradeData(deviceId);
        if (jsonObject != null && jsonObject.getInteger("number") != null && jsonObject.getLong("size") != null && jsonObject.getInteger("number") <= jsonObject.getLong("size")) {
            try {
                log.info(".................................设备{}升级固件,第{}个分片升级成功.....................................", deviceId, jsonObject.getInteger("number"));
                // 1.计算下次升级参数
                deviceCmdInfoService.countNextUpgrade(deviceId);
                log.info("222222222222");
                // 2.开始下一次升级
                deviceCmdInfoService.publishFirmware(deviceId);
            } catch (Exception e) {
                log.error("puback error", e);
                e.printStackTrace();
            }
        }
//        mqttChannelServiceImpl.getMqttChannel(mqttChannelServiceImpl.getDeviceId(channel)).getSendMqttMessage(messageId).setConfirmStatus(ConfirmStatus.COMPLETE);
    }

    /**
     * @param channel
     * @param mqttMessage
     * @return void
     * @description 收到 qos2发布收到控制报文处理
     */
    @Override
    public void pubrec(Channel channel, MqttMessage mqttMessage) {
        MqttMessageIdVariableHeader messageIdVariableHeader = (MqttMessageIdVariableHeader) mqttMessage.variableHeader();
        int messageId = messageIdVariableHeader.messageId();
        mqttChannelServiceImpl.getMqttChannel(mqttChannelServiceImpl.getDeviceId(channel)).getSendMqttMessage(messageId).setConfirmStatus(ConfirmStatus.PUBREL);
        mqttChannelServiceImpl.doPubrec(channel, messageId);
    }

    /**
     * @param channel
     * @param mqttMessage
     * @return void
     * @description 收到qos2发布释放控制报文处理
     */
    @Override
    public void pubrel(Channel channel, MqttMessage mqttMessage) {
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = (MqttMessageIdVariableHeader) mqttMessage.variableHeader();
        int messageId = mqttMessageIdVariableHeader.messageId();
        // mqttChannelServiceImpl.getMqttChannel(mqttChannelServiceImpl.getDeviceId(channel)).getSendMqttMessage(messageId).setConfirmStatus(ConfirmStatus.COMPLETE); // 复制为空
        mqttChannelServiceImpl.doPubrel(channel, messageId);

    }

    /**
     * @param channel
     * @param mqttMessage
     * @return void
     * @description 收到qos2发布完成控制报文
     */
    @Override
    public void pubcomp(Channel channel, MqttMessage mqttMessage) {
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = (MqttMessageIdVariableHeader) mqttMessage.variableHeader();
        int messageId = mqttMessageIdVariableHeader.messageId();
        SendMqttMessage sendMqttMessage = mqttChannelServiceImpl.getMqttChannel(mqttChannelServiceImpl.getDeviceId(channel)).getSendMqttMessage(messageId);
        sendMqttMessage.setConfirmStatus(ConfirmStatus.COMPLETE); // 复制为空
    }

}