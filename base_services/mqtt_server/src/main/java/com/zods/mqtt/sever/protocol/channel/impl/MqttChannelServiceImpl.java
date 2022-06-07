package com.zods.mqtt.sever.protocol.channel.impl;
import com.alibaba.fastjson.JSONObject;
import com.zods.mqtt.sever.business.config.TopicProperties;
import com.zods.mqtt.sever.business.constant.EquipCmdConstant;
import com.zods.mqtt.sever.business.constant.TopicConstant;
import com.zods.mqtt.sever.business.entity.DeviceLogin;
import com.zods.mqtt.sever.business.response.ResponseModel;
import com.zods.mqtt.sever.business.service.BusinessService;
import com.zods.mqtt.sever.business.service.DeviceCmdInfoService;
import com.zods.mqtt.sever.business.service.DeviceService;
import com.zods.mqtt.sever.protocol.bean.*;
import com.zods.mqtt.sever.protocol.client.MqttClientProperties;
import com.zods.mqtt.sever.protocol.client.MqttResponseCache;
import com.zods.mqtt.sever.protocol.common.enums.ConfirmStatus;
import com.zods.mqtt.sever.protocol.common.enums.SessionStatus;
import com.zods.mqtt.sever.protocol.common.enums.SubStatus;
import com.zods.mqtt.sever.protocol.common.exception.ConnectionException;
import com.zods.mqtt.sever.protocol.common.util.ByteBufUtil;
import com.zods.mqtt.sever.protocol.common.util.HexStringUtils;
import com.zods.mqtt.sever.protocol.scan.ScanRunnable;
import com.zods.mqtt.sever.protocol.session.SessionManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.mqtt.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import static io.netty.handler.codec.mqtt.MqttQoS.AT_LEAST_ONCE;
import static io.netty.handler.codec.mqtt.MqttQoS.EXACTLY_ONCE;

/**
 * @author jianglong
 * @description MQTT控制报文channel处理
 * @create 2019-09-09
 **/
@Component("mqttChannelServiceImpl")
@Slf4j
public class MqttChannelServiceImpl extends AbstractChannelService {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    private static final Charset GBK_CHARSET = Charset.forName("GBK");

    private WillService willService = new WillService();

    private SessionManager sessionManager = new SessionManager();

    @Autowired
    private BusinessService businessServiceImpl;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceCmdInfoService deviceCmdInfoService;

    @Autowired
    private MqttClientProperties consoleProperties;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private TopicProperties topicProperties;

    @Autowired
    private MqttResponseCache mqttResponseCache;

    public MqttChannelServiceImpl(ScanRunnable scanRunnable) {
        super(scanRunnable);
    }

    /**
     * @param channel               通道
     * @param deviceId              客户端全局ID
     * @param mqttConnectMessage    登录控制报文
     * @param mqttConnectReturnCode 登录失败返回码
     * @return void
     * @description:登录失败channel业务处理
     */
    @Override
    public void loginFail(Channel channel, String deviceId, MqttConnectMessage mqttConnectMessage, MqttConnectReturnCode mqttConnectReturnCode) {
        MqttFixedHeader mqttConnectFixedHeader = mqttConnectMessage.fixedHeader();//固定报头
        MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(mqttConnectReturnCode, true);//可变报文头
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNACK, mqttConnectFixedHeader.isDup(), MqttQoS.AT_MOST_ONCE, mqttConnectFixedHeader.isRetain(), 0x02);
        MqttConnAckMessage connAck = new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
        channel.writeAndFlush(connAck);
    }

    /**
     * @param channel            通道
     * @param deviceId           客户端全局ID
     * @param mqttConnectMessage 登录控制报文
     * @return void
     * @description:登录成功channel业务处理
     */
    @SuppressWarnings({"unchecked", "incomplete-switch"})
    @Override
    public void loginSuccess(Channel channel, String deviceId, MqttConnectMessage mqttConnectMessage) throws Exception {
        /**设置channel的特性值*/
        channel.attr(_login).set(true);
        channel.attr(_deviceId).set(deviceId);

        MqttFixedHeader mqttConnectFixedHeader = mqttConnectMessage.fixedHeader();//固定报头
        MqttConnectVariableHeader mqttConnectVariableHeader = mqttConnectMessage.variableHeader();//可变报头
        final MqttConnectPayload payload = mqttConnectMessage.payload();//有效载荷
        /**查询设备是否存在**/
        String clientid = payload.clientIdentifier();
        if (!clientid.equals(consoleProperties.getAppCode())) {
            String password = new String(payload.passwordInBytes(), "UTF-8");
            DeviceLogin deviceLogin = new DeviceLogin();
            deviceLogin.setDeviceId(clientid);
            deviceLogin.setDeviceApiKey(password);
            ResponseModel<String> responseModel = deviceService.connectAuth(deviceLogin);
            if (responseModel.getResult() != 200) {
                throw new ConnectionException("鉴权失败：" + responseModel.getResult());
            }
        }
        /**启用线程处理登录业务操作*/
        executorService.execute(() -> {
            /**封装一个自己的channel(缓存在全局变量中)*/
            MqttChannel build = MqttChannel.builder()
                    .channel(channel) //channel
                    .cleanSession(mqttConnectVariableHeader.isCleanSession())//当为true时channel close时从缓存中删除此channel
                    .deviceId(payload.clientIdentifier()) //客户端ID
                    .sessionStatus(SessionStatus.OPEN) //会话状态
                    .isWill(mqttConnectVariableHeader.isWillFlag()) //是否是遗言消息
                    .subStatus(SubStatus.NO) //是否订阅过主题
                    .topic(new CopyOnWriteArraySet<>()) //订阅的主题
                    .message(new ConcurrentHashMap<>()) //待确认消息
                    .receive(new CopyOnWriteArraySet<>()) //接收消息ID
                    .build();
            /**缓存全局channel信息*/
            if (connectSuccess(deviceId, build)) {
                /**遗言消息标志*/
                if (mqttConnectVariableHeader.isWillFlag()) {
                    boolean b = doIf(mqttConnectVariableHeader
                            , mqttConnectVariableHeader1 -> (payload.willMessageInBytes() != null)
                            , mqttConnectVariableHeader1 -> (payload.willTopic() != null)
                    );
                    if (!b) {
                        throw new ConnectionException("will message and will topic is not null");
                    }
                    //处理遗嘱消息
                    final WillMeaasge buildWill = WillMeaasge.builder()
                            .qos(mqttConnectVariableHeader.willQos())
                            .willMessage(new String(payload.willMessageInBytes()))
                            .willTopic(payload.willTopic())
                            .isRetain(mqttConnectVariableHeader.isWillRetain())
                            .build();
                    willService.save(payload.clientIdentifier(), buildWill);
                }
                /**没有遗嘱消息*/
                else {
                    willService.del(payload.clientIdentifier());
                    boolean b = doIf(mqttConnectVariableHeader
                            , mqttConnectVariableHeader1 -> (!mqttConnectVariableHeader1.isWillRetain())
                            , mqttConnectVariableHeader1 -> (mqttConnectVariableHeader1.willQos() == 0));
                    if (!b) {
                        throw new ConnectionException("will retain should be  null and will QOS equal 0");
                    }
                }
                /**返回客户端CONNACK回执消息*/
                doIfElse(mqttConnectVariableHeader
                        , mqttConnectVariableHeader1 -> (mqttConnectVariableHeader1.isCleanSession())
                        , mqttConnectVariableHeader1 -> {
                            MqttConnectReturnCode connectReturnCode = MqttConnectReturnCode.CONNECTION_ACCEPTED;
                            MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(connectReturnCode, false);
                            MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNACK, mqttConnectFixedHeader.isDup(), MqttQoS.AT_MOST_ONCE, mqttConnectFixedHeader.isRetain(), 0x02);
                            MqttConnAckMessage connAck = new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
                            channel.writeAndFlush(connAck);
                        }
                        , mqttConnectVariableHeader1 -> {
                            MqttConnectReturnCode connectReturnCode = MqttConnectReturnCode.CONNECTION_ACCEPTED;
                            MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(connectReturnCode, true);
                            MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNACK, mqttConnectFixedHeader.isDup(), MqttQoS.AT_MOST_ONCE, mqttConnectFixedHeader.isRetain(), 0x02);
                            MqttConnAckMessage connAck = new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
                            channel.writeAndFlush(connAck);
                        });
                /**发送session缓存消息*/
                ConcurrentLinkedQueue<SessionMessage> sessionMessages = sessionManager.getByteBuf(payload.clientIdentifier());
                doIfElse(sessionMessages
                        , messages -> messages != null && !messages.isEmpty()
                        , byteBufs -> {
                            SessionMessage sessionMessage;
                            while ((sessionMessage = byteBufs.poll()) != null) {
                                switch (sessionMessage.getQoS()) {
                                    case EXACTLY_ONCE:
                                        sendQosConfirmMsg(EXACTLY_ONCE, getMqttChannel(deviceId), sessionMessage.getTopic(), sessionMessage.getByteBuf());
                                        break;
                                    case AT_MOST_ONCE:
                                        sendQos0Msg(channel, sessionMessage.getTopic(), sessionMessage.getByteBuf());
                                        break;
                                    case AT_LEAST_ONCE:
                                        sendQosConfirmMsg(AT_LEAST_ONCE, getMqttChannel(deviceId), sessionMessage.getTopic(), sessionMessage.getByteBuf());
                                        break;
                                }
                            }
                        }
                );
            }
            /**上线业务处理*/
            businessServiceImpl.doLinePacket(channel.id().asShortText(), deviceId, true);
        });
    }

    /**
     * @param channel      客户端全局ID
     * @param isDisconnect 是否是客户端发送的断开连接控制报文
     * @return void
     * @description:断开连接控制报文channel业务处理
     */
    @Override
    public void closeSuccess(Channel channel, boolean isDisconnect) {
        String deviceId = getDeviceId(channel);
        if (StringUtils.isNotBlank(deviceId)) {
            executorService.execute(() -> {
                MqttChannel mqttChannel = mqttChannels.get(deviceId);
                Optional.ofNullable(mqttChannel).ifPresent(mqttChannel1 -> {
                    mqttChannel1.setSessionStatus(SessionStatus.CLOSE); //设置关闭
                    mqttChannel1.close(); //关闭channel
                    mqttChannel1.setChannel(null);
                    /**
                     * 关闭连接时是否清除之前的历史消息
                     */
                    if (!mqttChannel1.isCleanSession()) {
                        /**处理qos1未确认数据---把待确认数据转入session中*/
                        ConcurrentHashMap<Integer, SendMqttMessage> message = mqttChannel1.getMessage();
                        Optional.ofNullable(message).ifPresent(integerConfirmMessageConcurrentHashMap -> {
                            integerConfirmMessageConcurrentHashMap.forEach((integer, confirmMessage) ->
                                    doIfElse(confirmMessage, sendMqttMessage ->
                                                    sendMqttMessage.getConfirmStatus() == ConfirmStatus.PUB, sendMqttMessage -> {
                                                sessionManager.saveSessionMsg(mqttChannel.getDeviceId(), SessionMessage.builder()
                                                        .byteBuf(sendMqttMessage.getByteBuf())
                                                        .qoS(sendMqttMessage.getQos())
                                                        .topic(sendMqttMessage.getTopic())
                                                        .build());
                                            }
                                    ));

                        });
                    }
                    /**删除sub topic-消息*/
                    else {
                        //移除channelId  不保持会话直接删除,保持会话旧的在重新connect时替换
                        mqttChannels.remove(deviceId);
                        switch (mqttChannel1.getSubStatus()) {
                            case YES:
                                deleteSubTopic(mqttChannel1);
                                break;
                            case NO:
                                break;
                        }
                    }
                    /**发送遗言*/
                    if (mqttChannel1.isWill()) {
                        //不是disconnection操作
                        if (!isDisconnect) {
                            willService.doSend(deviceId);
                        }
                    }
                });
                /**上线业务处理*/
                //   businessServiceImpl.doLinePacket(channel.id().asShortText(),deviceId, false);

            });
        }
    }

    /**
     * @param deviceId 客户端全局ID
     * @param topics   订阅主题集合
     * @return void
     * @description 收到订阅控制报文channel业务处理
     */
    @Override
    public void suscribeSuccess(String deviceId, Set<String> topics) {
        doIfElse(topics, topics1 -> !CollectionUtils.isEmpty(topics1), strings -> {
            MqttChannel mqttChannel = mqttChannels.get(deviceId);
            mqttChannel.setSubStatus(SubStatus.YES); //设置订阅主题标识
            mqttChannel.addTopic(strings);
            //   executorService.execute(() -> {
            Optional.ofNullable(mqttChannel).ifPresent(mqttChannel1 -> {
                if (mqttChannel1.isLogin()) {
                    strings.parallelStream().forEach(topic -> {
                        addChannel(topic, mqttChannel);
                        sendRetain(topic, mqttChannel); // 发送保留消息
                    });
                }
            });
        });
        // });
    }

    /**
     * @description 收到取消订阅控制报文channel业务处理
     * @param: deviceId 客户端全局ID
     * @param: topics 取消订阅主题集合
     * @return: void
     */
    public void unsubscribe(String deviceId, List<String> topics1) {
        Optional.ofNullable(mqttChannels.get(deviceId)).ifPresent(mqttChannel -> {
            topics1.forEach(topic -> {
                deleteChannel(topic, mqttChannel);
            });
        });
    }

    /**
     * @param channel            通道
     * @param mqttPublishMessage 发布消息控制报文
     * @return void
     * @description 收到发布消息控制报文channel业务处理
     */
    @SuppressWarnings("incomplete-switch")
    @Override
    public void publishSuccess(Channel channel, MqttPublishMessage mqttPublishMessage) {
        try {
            MqttFixedHeader mqttFixedHeader = mqttPublishMessage.fixedHeader();
            MqttPublishVariableHeader mqttPublishVariableHeader = mqttPublishMessage.variableHeader();
            MqttChannel mqttChannel = getMqttChannel(getDeviceId(channel));
            ByteBuf payload = mqttPublishMessage.payload();
            byte[] bytes = ByteBufUtil.copyByteBuf(payload);
            log.info("---------------------------------------payload入口编码---------------------------------------------");
            log.info(HexStringUtils.parseByte2HexStr(bytes));
            String msg = new String(bytes, CHARSET);
            byte[] gbkBytes = msg.getBytes(Charset.forName("GBK"));
            String topic = mqttPublishVariableHeader.topicName();
            int messageId = mqttPublishVariableHeader.packetId();
            String message = new String(gbkBytes, CHARSET);
            /**业务处理**/
            executorService.execute(() -> {
                try {
                    //已登录连接存在
                    if (channel.hasAttr(_login) && mqttChannel != null) {
                        String deviceId = getDeviceId(channel);
                        log.info("................. deviceId:{},topic:{},qos:{},message:{} :...............", deviceId, topic, mqttFixedHeader.qosLevel().value(), message);
                        //下行返回的消息发送到topic
                        if (topic.equals(TopicConstant.DR_TOPIC)) {
                            log.info(".................设备下行响应数据发送到topic:{}，qos:{} :...............", topic, mqttFixedHeader.qosLevel().value());
                            downWardsResponseToTopic(deviceId, message, topicProperties.getCmdDownWardsResponseTopic());
                        }
                        if (topic.equals(TopicConstant.DP_TOPIC)) {
                            log.info(".................设备上报数据到topic:{}，qos:{} :...............", topic, mqttFixedHeader.qosLevel().value());
                            //消息解码业务处理
                            businessServiceImpl.doPublishPacket(deviceId, topic, bytes);
                        }
                        if (topic.equals(TopicConstant.SUPPORTSIZE_CMD_TOPIC(deviceId))) {
                            log.info(".................设备下行响应下次需要升级的固件分片大小参数到topic:{}，qos:{} :...............", topic, mqttFixedHeader.qosLevel().value());
                            supportSizeToTopic(deviceId, message, topicProperties.getFirmwareSupportSizeTopic());
                        }
                        //根据消息等级回复
                        switch (mqttFixedHeader.qosLevel()) {
                            case AT_MOST_ONCE:
                                log.info(".................publish qos:0 , topic:{} :...............", topic);
                                break;
                            case AT_LEAST_ONCE:
                                log.info(".................publish qos:1 , topic:{}...............", topic);
                                sendPubBack(channel, messageId);
                                push(mqttPublishVariableHeader.topicName(), mqttFixedHeader.qosLevel(), judgeAudioCharset(msg, bytes));
                                break;
                            case EXACTLY_ONCE:
                                log.info(".................publish qos:2 , topic:{}...............", topic);
                                sendPubRec(mqttChannel, messageId);
                                // push(mqttPublishVariableHeader.topicName(), mqttFixedHeader.qosLevel(), gbkBytes);
                                break;
                        }
                        //是保留消息qos>0
                        if (mqttFixedHeader.isRetain() && mqttFixedHeader.qosLevel() != MqttQoS.AT_MOST_ONCE) {
                            saveRetain(mqttPublishVariableHeader.topicName(),
                                    RetainMessage.builder()
                                            .byteBuf(bytes)
                                            .qoS(mqttFixedHeader.qosLevel())
                                            .build(), false);
                        }
                        //是保留消息qos=0清除之前保留消息保留现在
                        else if (mqttFixedHeader.isRetain() && mqttFixedHeader.qosLevel() == MqttQoS.AT_MOST_ONCE) {
                            saveRetain(mqttPublishVariableHeader.topicName(),
                                    RetainMessage.builder()
                                            .byteBuf(bytes)
                                            .qoS(mqttFixedHeader.qosLevel())
                                            .build(), true);
                        }
                    }
                } catch (Exception e) {
                    log.warn("发生了异常:{}",e.getMessage());
                }
            });
        } catch (Exception e) {
            log.warn("发生异常:{}",e.getMessage());
        }
    }

    private byte[] judgeAudioCharset(String cmd, byte[] bytes) {
        if (cmd.startsWith(EquipCmdConstant.BROAD_CAST)) {
            return cmd.getBytes(GBK_CHARSET);
        }
        return bytes;
    }

    /**
     * 同步设备下行命令的响应消息到消息队列
     *
     * @param deviceId
     * @param message
     */
    private void downWardsResponseToTopic(String deviceId, String message, String topic) {
        if (!StringUtils.isEmpty(message) && !StringUtils.isEmpty(deviceId)) {
            if (message.startsWith(EquipCmdConstant.FIRMWARE_MQTT_UPGRADE)) {
                // 1.升级成功删除历史升级记录
                deviceCmdInfoService.removePastUpgradeData(deviceId);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("body", message);
            jsonObject.put("deviceId", deviceId);
            log.info(".....................发送下行响应到kafka:{}......................", message);
            executorService.execute(() -> {
                try {
                    kafkaTemplate.send(topic, deviceId, jsonObject.toJSONString());
                } catch (Exception e) {
                    log.error("设备" + deviceId + "发送下行响应到kafka失败", e);
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 设备上报固件升级片段大小
     *
     * @param deviceId
     * @param message
     */
    private void supportSizeToTopic(String deviceId, String message, String topic) {
        if (!StringUtils.isEmpty(message) && !StringUtils.isEmpty(deviceId)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deviceId", deviceId);
            jsonObject.put("body", message);
            log.info(".....................设备上报固件升级分别大小到kafka:{}......................", jsonObject);
            executorService.execute(() -> {
                try {
                    kafkaTemplate.send(topic, deviceId, jsonObject.toJSONString());
                } catch (Exception e) {
                    log.error("设备" + deviceId + "发送下行响应到kafka失败", e);
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * @param channel   通道
     * @param messageId 报文ID
     * @return void
     * @description qos2 第二步
     */
    public void doPubrel(Channel channel, int messageId) {
        MqttChannel mqttChannel = getMqttChannel(getDeviceId(channel));
        doIfElse(mqttChannel, mqttChannel1 -> mqttChannel1.isLogin(), mqttChannel1 -> {
            mqttChannel1.removeRecevice(messageId);
            sendToPubComp(channel, messageId);
        });
    }

    /**
     * @param channel   通道
     * @param messageId 报文ID
     * @return void
     * @description qos2 第一步
     */
    @Override
    public void doPubrec(Channel channel, int messageId) {
        sendPubRel(channel, false, messageId);
    }

    /**
     * @description 发送遗嘱消息(有的channel已经关闭 但是保持了 session 此时加入session 数据中)
     */
    @Override
    public void sendWillMsg(WillMeaasge willMeaasge) {
        Collection<MqttChannel> mqttChannels = getChannels(willMeaasge.getWillTopic(), topic -> cacheMap.getData(getTopic(topic)));
        if (!CollectionUtils.isEmpty(mqttChannels)) {
            mqttChannels.forEach(mqttChannel -> {
                switch (mqttChannel.getSessionStatus()) {
                    case CLOSE:
                        sessionManager.saveSessionMsg(mqttChannel.getDeviceId(),
                                SessionMessage.builder()
                                        .topic(willMeaasge.getWillTopic())
                                        .qoS(MqttQoS.valueOf(willMeaasge.getQos()))
                                        .byteBuf(willMeaasge.getWillMessage().getBytes()).build());
                        break;
                    case OPEN:
                        writeWillMsg(mqttChannel, willMeaasge);
                        break;
                }
            });
        }
    }

    /**
     * 成功连接处理
     */
    private boolean connectSuccess(String deviceId, MqttChannel build) {
        return Optional.ofNullable(mqttChannels.get(deviceId))
                //已登录过且保存到了全局变量中
                .map(mqttChannel -> {
                    switch (mqttChannel.getSessionStatus()) {
                        case OPEN:
                            return false;
                        case CLOSE:
                            switch (mqttChannel.getSubStatus()) {
                                case YES:
                                    deleteSubTopic(mqttChannel);//删除已订阅主题消息
                                case NO:
                            }
                    }
                    mqttChannels.put(deviceId, build);
                    return true;
                })
                //首次登录,缓存channel信息
                .orElseGet(() -> {
                    mqttChannels.put(deviceId, build);
                    return true;
                });
    }

    /**
     * 推送订阅消息
     */
    @SuppressWarnings("incomplete-switch")
    private void push(String topic, MqttQoS qos, byte[] bytes) {
        Collection<MqttChannel> subChannels = getChannels(topic.substring(0, topic.lastIndexOf("/") + 1) + "+", topic1 -> cacheMap.getData(getTopic(topic1)));
        if (!CollectionUtils.isEmpty(subChannels)) {
            subChannels.parallelStream().forEach(subChannel -> {
                switch (subChannel.getSessionStatus()) {
                    case OPEN: // 在线
                        if (subChannel.isActive()) { // 防止channel失效  但是离线状态没更改
                            switch (qos) {
                                case AT_LEAST_ONCE:
                                    sendQosConfirmMsg(AT_LEAST_ONCE, subChannel, topic, bytes);
                                    break;
                                case AT_MOST_ONCE:
                                    //sendQos0Msg(subChannel.getChannel(),topic,bytes);
                                    break;
                                case EXACTLY_ONCE:
                                    sendQosConfirmMsg(EXACTLY_ONCE, subChannel, topic, bytes);
                                    break;
                            }
                        } else {
                            if (!subChannel.isCleanSession()) {
                                sessionManager.saveSessionMsg(subChannel.getDeviceId(),
                                        SessionMessage.builder().byteBuf(bytes).qoS(qos).topic(topic).build());
                                break;
                            }
                        }
                        break;
                    case CLOSE: // 连接 设置了 clean session =false
                        sessionManager.saveSessionMsg(subChannel.getDeviceId(),
                                SessionMessage.builder().byteBuf(bytes).qoS(qos).topic(topic).build());
                        break;
                }
            });
        }
    }

    /**
     * 清除channel 订阅主题
     */
    private void deleteSubTopic(MqttChannel mqttChannel) {
        Set<String> topics = mqttChannel.getTopic();
        topics.parallelStream().forEach(topic -> {
            mqttChannelCache.invalidate(topic);
            cacheMap.delete(getTopic(topic), mqttChannel);
        });
    }


    /**
     * 保存保留消息
     */
    private void saveRetain(String topic, RetainMessage retainMessage, boolean isClean) {
        ConcurrentLinkedQueue<RetainMessage> retainMessages = retain.getOrDefault(topic, new ConcurrentLinkedQueue<>());
        if (!retainMessages.isEmpty() && isClean) {
            retainMessages.clear();
        }
        boolean flag;
        do {
            flag = retainMessages.add(retainMessage);
        }
        while (!flag);
        retain.put(topic, retainMessages);
    }

    /**
     * 发送保留消息
     */
    @SuppressWarnings("incomplete-switch")
    private void sendRetain(String topic, MqttChannel mqttChannel) {
        retain.forEach((_topic, retainMessages) -> {
            if (StringUtils.startsWith(_topic, topic)) {
                Optional.ofNullable(retainMessages).ifPresent(pubMessages1 -> {
                    retainMessages.parallelStream().forEach(retainMessage -> {
                        switch (retainMessage.getQoS()) {
                            case AT_MOST_ONCE:
                                sendQos0Msg(mqttChannel.getChannel(), _topic, retainMessage.getByteBuf());
                                break;
                            case AT_LEAST_ONCE:
                                sendQosConfirmMsg(AT_LEAST_ONCE, mqttChannel, _topic, retainMessage.getByteBuf());
                                break;
                            case EXACTLY_ONCE:
                                sendQosConfirmMsg(EXACTLY_ONCE, mqttChannel, _topic, retainMessage.getByteBuf());
                                break;
                        }
                    });
                });
            }
        });

    }

    /**
     * 断电后重复登录
     */
    @Override
    public void repeatLogin(Channel channel, String deviceId, MqttConnectMessage mqttConnectMessage) throws Exception {
        /**查询设备是否存在**/
        MqttConnectPayload payload = mqttConnectMessage.payload();
        String clientid = payload.clientIdentifier();
        if (!clientid.equals(consoleProperties.getAppCode())) {
            String username = payload.userName();
            String password = new String(payload.passwordInBytes(), "UTF-8");
            DeviceLogin deviceLogin = new DeviceLogin();
            deviceLogin.setDeviceId(clientid);
            deviceLogin.setDeviceApiKey(password);
            ResponseModel<String> responseModel = deviceService.connectAuth(deviceLogin);
            if (responseModel.getResult() != 200) {
                throw new ConnectionException("鉴权失败：" + responseModel.getResult());
            }
        }

        MqttConnectVariableHeader mqttConnectVariableHeader = mqttConnectMessage.variableHeader();
        MqttFixedHeader mqttConnectFixedHeader = mqttConnectMessage.fixedHeader();
        MqttChannel mqttChannel = mqttChannels.get(deviceId);
        Channel oldChannel = mqttChannel.getChannel();
        if (mqttChannel != null && oldChannel != null) {
            try {
                ChannelId id = channel.id();
                ChannelId oldId = oldChannel.id();
                if (oldChannel != channel && oldId != id) {
                    mqttChannel.setChannel(channel);
                    ConcurrentHashMap<Integer, SendMqttMessage> message = mqttChannel.getMessage();
                    message.forEach((messageId, mqttMessage) -> {
                        mqttMessage.setChannel(channel);
                    });

                    /**设置channel的特性值*/
                    channel.attr(_login).set(true);
                    channel.attr(_deviceId).set(deviceId);

                    /**返回客户端CONNACK回执消息*/
                    doIfElse(mqttConnectVariableHeader
                            , mqttConnectVariableHeader1 -> (mqttConnectVariableHeader1.isCleanSession())
                            , mqttConnectVariableHeader1 -> {
                                MqttConnectReturnCode connectReturnCode = MqttConnectReturnCode.CONNECTION_ACCEPTED;
                                MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(connectReturnCode, false);
                                MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNACK, mqttConnectFixedHeader.isDup(), MqttQoS.AT_MOST_ONCE, mqttConnectFixedHeader.isRetain(), 0x02);
                                MqttConnAckMessage connAck = new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
                                channel.writeAndFlush(connAck);
                            }
                            , mqttConnectVariableHeader1 -> {
                                MqttConnectReturnCode connectReturnCode = MqttConnectReturnCode.CONNECTION_ACCEPTED;
                                MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(connectReturnCode, true);
                                MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNACK, mqttConnectFixedHeader.isDup(), MqttQoS.AT_MOST_ONCE, mqttConnectFixedHeader.isRetain(), 0x02);
                                MqttConnAckMessage connAck = new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
                                channel.writeAndFlush(connAck);
                            });

                    /**发送session缓存消息*/
                    ConcurrentLinkedQueue<SessionMessage> sessionMessages = sessionManager.getByteBuf(payload.clientIdentifier());
                    doIfElse(sessionMessages
                            , messages -> messages != null && !messages.isEmpty()
                            , byteBufs -> {
                                SessionMessage sessionMessage;
                                while ((sessionMessage = byteBufs.poll()) != null) {
                                    switch (sessionMessage.getQoS()) {
                                        case EXACTLY_ONCE:
                                            sendQosConfirmMsg(EXACTLY_ONCE, getMqttChannel(deviceId), sessionMessage.getTopic(), sessionMessage.getByteBuf());
                                            break;
                                        case AT_MOST_ONCE:
                                            sendQos0Msg(channel, sessionMessage.getTopic(), sessionMessage.getByteBuf());
                                            break;
                                        case AT_LEAST_ONCE:
                                            sendQosConfirmMsg(AT_LEAST_ONCE, getMqttChannel(deviceId), sessionMessage.getTopic(), sessionMessage.getByteBuf());
                                            break;
                                    }
                                }
                            }
                    );
                    /**设备上线状态保存到数据库*/
                    executorService.execute(() -> {
                        businessServiceImpl.doLinePacket(id.asShortText(), deviceId, true);
                    });
                }
            } catch (Exception e) {
                System.out.println("..............设备离线重登陆异常.............");
            } finally {
                oldChannel.disconnect();
            }
        }
    }

}
