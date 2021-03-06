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
 * @description MQTT????????????channel??????
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
     * @param channel               ??????
     * @param deviceId              ???????????????ID
     * @param mqttConnectMessage    ??????????????????
     * @param mqttConnectReturnCode ?????????????????????
     * @return void
     * @description:????????????channel????????????
     */
    @Override
    public void loginFail(Channel channel, String deviceId, MqttConnectMessage mqttConnectMessage, MqttConnectReturnCode mqttConnectReturnCode) {
        MqttFixedHeader mqttConnectFixedHeader = mqttConnectMessage.fixedHeader();//????????????
        MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(mqttConnectReturnCode, true);//???????????????
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNACK, mqttConnectFixedHeader.isDup(), MqttQoS.AT_MOST_ONCE, mqttConnectFixedHeader.isRetain(), 0x02);
        MqttConnAckMessage connAck = new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
        channel.writeAndFlush(connAck);
    }

    /**
     * @param channel            ??????
     * @param deviceId           ???????????????ID
     * @param mqttConnectMessage ??????????????????
     * @return void
     * @description:????????????channel????????????
     */
    @SuppressWarnings({"unchecked", "incomplete-switch"})
    @Override
    public void loginSuccess(Channel channel, String deviceId, MqttConnectMessage mqttConnectMessage) throws Exception {
        /**??????channel????????????*/
        channel.attr(_login).set(true);
        channel.attr(_deviceId).set(deviceId);

        MqttFixedHeader mqttConnectFixedHeader = mqttConnectMessage.fixedHeader();//????????????
        MqttConnectVariableHeader mqttConnectVariableHeader = mqttConnectMessage.variableHeader();//????????????
        final MqttConnectPayload payload = mqttConnectMessage.payload();//????????????
        /**????????????????????????**/
        String clientid = payload.clientIdentifier();
        if (!clientid.equals(consoleProperties.getAppCode())) {
            String password = new String(payload.passwordInBytes(), "UTF-8");
            DeviceLogin deviceLogin = new DeviceLogin();
            deviceLogin.setDeviceId(clientid);
            deviceLogin.setDeviceApiKey(password);
            ResponseModel<String> responseModel = deviceService.connectAuth(deviceLogin);
            if (responseModel.getResult() != 200) {
                throw new ConnectionException("???????????????" + responseModel.getResult());
            }
        }
        /**????????????????????????????????????*/
        executorService.execute(() -> {
            /**?????????????????????channel(????????????????????????)*/
            MqttChannel build = MqttChannel.builder()
                    .channel(channel) //channel
                    .cleanSession(mqttConnectVariableHeader.isCleanSession())//??????true???channel close????????????????????????channel
                    .deviceId(payload.clientIdentifier()) //?????????ID
                    .sessionStatus(SessionStatus.OPEN) //????????????
                    .isWill(mqttConnectVariableHeader.isWillFlag()) //?????????????????????
                    .subStatus(SubStatus.NO) //?????????????????????
                    .topic(new CopyOnWriteArraySet<>()) //???????????????
                    .message(new ConcurrentHashMap<>()) //???????????????
                    .receive(new CopyOnWriteArraySet<>()) //????????????ID
                    .build();
            /**????????????channel??????*/
            if (connectSuccess(deviceId, build)) {
                /**??????????????????*/
                if (mqttConnectVariableHeader.isWillFlag()) {
                    boolean b = doIf(mqttConnectVariableHeader
                            , mqttConnectVariableHeader1 -> (payload.willMessageInBytes() != null)
                            , mqttConnectVariableHeader1 -> (payload.willTopic() != null)
                    );
                    if (!b) {
                        throw new ConnectionException("will message and will topic is not null");
                    }
                    //??????????????????
                    final WillMeaasge buildWill = WillMeaasge.builder()
                            .qos(mqttConnectVariableHeader.willQos())
                            .willMessage(new String(payload.willMessageInBytes()))
                            .willTopic(payload.willTopic())
                            .isRetain(mqttConnectVariableHeader.isWillRetain())
                            .build();
                    willService.save(payload.clientIdentifier(), buildWill);
                }
                /**??????????????????*/
                else {
                    willService.del(payload.clientIdentifier());
                    boolean b = doIf(mqttConnectVariableHeader
                            , mqttConnectVariableHeader1 -> (!mqttConnectVariableHeader1.isWillRetain())
                            , mqttConnectVariableHeader1 -> (mqttConnectVariableHeader1.willQos() == 0));
                    if (!b) {
                        throw new ConnectionException("will retain should be  null and will QOS equal 0");
                    }
                }
                /**???????????????CONNACK????????????*/
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
                /**??????session????????????*/
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
            /**??????????????????*/
            businessServiceImpl.doLinePacket(channel.id().asShortText(), deviceId, true);
        });
    }

    /**
     * @param channel      ???????????????ID
     * @param isDisconnect ???????????????????????????????????????????????????
     * @return void
     * @description:????????????????????????channel????????????
     */
    @Override
    public void closeSuccess(Channel channel, boolean isDisconnect) {
        String deviceId = getDeviceId(channel);
        if (StringUtils.isNotBlank(deviceId)) {
            executorService.execute(() -> {
                MqttChannel mqttChannel = mqttChannels.get(deviceId);
                Optional.ofNullable(mqttChannel).ifPresent(mqttChannel1 -> {
                    mqttChannel1.setSessionStatus(SessionStatus.CLOSE); //????????????
                    mqttChannel1.close(); //??????channel
                    mqttChannel1.setChannel(null);
                    /**
                     * ????????????????????????????????????????????????
                     */
                    if (!mqttChannel1.isCleanSession()) {
                        /**??????qos1???????????????---????????????????????????session???*/
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
                    /**??????sub topic-??????*/
                    else {
                        //??????channelId  ???????????????????????????,???????????????????????????connect?????????
                        mqttChannels.remove(deviceId);
                        switch (mqttChannel1.getSubStatus()) {
                            case YES:
                                deleteSubTopic(mqttChannel1);
                                break;
                            case NO:
                                break;
                        }
                    }
                    /**????????????*/
                    if (mqttChannel1.isWill()) {
                        //??????disconnection??????
                        if (!isDisconnect) {
                            willService.doSend(deviceId);
                        }
                    }
                });
                /**??????????????????*/
                //   businessServiceImpl.doLinePacket(channel.id().asShortText(),deviceId, false);

            });
        }
    }

    /**
     * @param deviceId ???????????????ID
     * @param topics   ??????????????????
     * @return void
     * @description ????????????????????????channel????????????
     */
    @Override
    public void suscribeSuccess(String deviceId, Set<String> topics) {
        doIfElse(topics, topics1 -> !CollectionUtils.isEmpty(topics1), strings -> {
            MqttChannel mqttChannel = mqttChannels.get(deviceId);
            mqttChannel.setSubStatus(SubStatus.YES); //????????????????????????
            mqttChannel.addTopic(strings);
            //   executorService.execute(() -> {
            Optional.ofNullable(mqttChannel).ifPresent(mqttChannel1 -> {
                if (mqttChannel1.isLogin()) {
                    strings.parallelStream().forEach(topic -> {
                        addChannel(topic, mqttChannel);
                        sendRetain(topic, mqttChannel); // ??????????????????
                    });
                }
            });
        });
        // });
    }

    /**
     * @description ??????????????????????????????channel????????????
     * @param: deviceId ???????????????ID
     * @param: topics ????????????????????????
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
     * @param channel            ??????
     * @param mqttPublishMessage ????????????????????????
     * @return void
     * @description ??????????????????????????????channel????????????
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
            log.info("---------------------------------------payload????????????---------------------------------------------");
            log.info(HexStringUtils.parseByte2HexStr(bytes));
            String msg = new String(bytes, CHARSET);
            byte[] gbkBytes = msg.getBytes(Charset.forName("GBK"));
            String topic = mqttPublishVariableHeader.topicName();
            int messageId = mqttPublishVariableHeader.packetId();
            String message = new String(gbkBytes, CHARSET);
            /**????????????**/
            executorService.execute(() -> {
                try {
                    //?????????????????????
                    if (channel.hasAttr(_login) && mqttChannel != null) {
                        String deviceId = getDeviceId(channel);
                        log.info("................. deviceId:{},topic:{},qos:{},message:{} :...............", deviceId, topic, mqttFixedHeader.qosLevel().value(), message);
                        //??????????????????????????????topic
                        if (topic.equals(TopicConstant.DR_TOPIC)) {
                            log.info(".................?????????????????????????????????topic:{}???qos:{} :...............", topic, mqttFixedHeader.qosLevel().value());
                            downWardsResponseToTopic(deviceId, message, topicProperties.getCmdDownWardsResponseTopic());
                        }
                        if (topic.equals(TopicConstant.DP_TOPIC)) {
                            log.info(".................?????????????????????topic:{}???qos:{} :...............", topic, mqttFixedHeader.qosLevel().value());
                            //????????????????????????
                            businessServiceImpl.doPublishPacket(deviceId, topic, bytes);
                        }
                        if (topic.equals(TopicConstant.SUPPORTSIZE_CMD_TOPIC(deviceId))) {
                            log.info(".................??????????????????????????????????????????????????????????????????topic:{}???qos:{} :...............", topic, mqttFixedHeader.qosLevel().value());
                            supportSizeToTopic(deviceId, message, topicProperties.getFirmwareSupportSizeTopic());
                        }
                        //????????????????????????
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
                        //???????????????qos>0
                        if (mqttFixedHeader.isRetain() && mqttFixedHeader.qosLevel() != MqttQoS.AT_MOST_ONCE) {
                            saveRetain(mqttPublishVariableHeader.topicName(),
                                    RetainMessage.builder()
                                            .byteBuf(bytes)
                                            .qoS(mqttFixedHeader.qosLevel())
                                            .build(), false);
                        }
                        //???????????????qos=0????????????????????????????????????
                        else if (mqttFixedHeader.isRetain() && mqttFixedHeader.qosLevel() == MqttQoS.AT_MOST_ONCE) {
                            saveRetain(mqttPublishVariableHeader.topicName(),
                                    RetainMessage.builder()
                                            .byteBuf(bytes)
                                            .qoS(mqttFixedHeader.qosLevel())
                                            .build(), true);
                        }
                    }
                } catch (Exception e) {
                    log.warn("???????????????:{}",e.getMessage());
                }
            });
        } catch (Exception e) {
            log.warn("????????????:{}",e.getMessage());
        }
    }

    private byte[] judgeAudioCharset(String cmd, byte[] bytes) {
        if (cmd.startsWith(EquipCmdConstant.BROAD_CAST)) {
            return cmd.getBytes(GBK_CHARSET);
        }
        return bytes;
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param deviceId
     * @param message
     */
    private void downWardsResponseToTopic(String deviceId, String message, String topic) {
        if (!StringUtils.isEmpty(message) && !StringUtils.isEmpty(deviceId)) {
            if (message.startsWith(EquipCmdConstant.FIRMWARE_MQTT_UPGRADE)) {
                // 1.????????????????????????????????????
                deviceCmdInfoService.removePastUpgradeData(deviceId);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("body", message);
            jsonObject.put("deviceId", deviceId);
            log.info(".....................?????????????????????kafka:{}......................", message);
            executorService.execute(() -> {
                try {
                    kafkaTemplate.send(topic, deviceId, jsonObject.toJSONString());
                } catch (Exception e) {
                    log.error("??????" + deviceId + "?????????????????????kafka??????", e);
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * ????????????????????????????????????
     *
     * @param deviceId
     * @param message
     */
    private void supportSizeToTopic(String deviceId, String message, String topic) {
        if (!StringUtils.isEmpty(message) && !StringUtils.isEmpty(deviceId)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deviceId", deviceId);
            jsonObject.put("body", message);
            log.info(".....................???????????????????????????????????????kafka:{}......................", jsonObject);
            executorService.execute(() -> {
                try {
                    kafkaTemplate.send(topic, deviceId, jsonObject.toJSONString());
                } catch (Exception e) {
                    log.error("??????" + deviceId + "?????????????????????kafka??????", e);
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * @param channel   ??????
     * @param messageId ??????ID
     * @return void
     * @description qos2 ?????????
     */
    public void doPubrel(Channel channel, int messageId) {
        MqttChannel mqttChannel = getMqttChannel(getDeviceId(channel));
        doIfElse(mqttChannel, mqttChannel1 -> mqttChannel1.isLogin(), mqttChannel1 -> {
            mqttChannel1.removeRecevice(messageId);
            sendToPubComp(channel, messageId);
        });
    }

    /**
     * @param channel   ??????
     * @param messageId ??????ID
     * @return void
     * @description qos2 ?????????
     */
    @Override
    public void doPubrec(Channel channel, int messageId) {
        sendPubRel(channel, false, messageId);
    }

    /**
     * @description ??????????????????(??????channel???????????? ??????????????? session ????????????session ?????????)
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
     * ??????????????????
     */
    private boolean connectSuccess(String deviceId, MqttChannel build) {
        return Optional.ofNullable(mqttChannels.get(deviceId))
                //??????????????????????????????????????????
                .map(mqttChannel -> {
                    switch (mqttChannel.getSessionStatus()) {
                        case OPEN:
                            return false;
                        case CLOSE:
                            switch (mqttChannel.getSubStatus()) {
                                case YES:
                                    deleteSubTopic(mqttChannel);//???????????????????????????
                                case NO:
                            }
                    }
                    mqttChannels.put(deviceId, build);
                    return true;
                })
                //????????????,??????channel??????
                .orElseGet(() -> {
                    mqttChannels.put(deviceId, build);
                    return true;
                });
    }

    /**
     * ??????????????????
     */
    @SuppressWarnings("incomplete-switch")
    private void push(String topic, MqttQoS qos, byte[] bytes) {
        Collection<MqttChannel> subChannels = getChannels(topic.substring(0, topic.lastIndexOf("/") + 1) + "+", topic1 -> cacheMap.getData(getTopic(topic1)));
        if (!CollectionUtils.isEmpty(subChannels)) {
            subChannels.parallelStream().forEach(subChannel -> {
                switch (subChannel.getSessionStatus()) {
                    case OPEN: // ??????
                        if (subChannel.isActive()) { // ??????channel??????  ???????????????????????????
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
                    case CLOSE: // ?????? ????????? clean session =false
                        sessionManager.saveSessionMsg(subChannel.getDeviceId(),
                                SessionMessage.builder().byteBuf(bytes).qoS(qos).topic(topic).build());
                        break;
                }
            });
        }
    }

    /**
     * ??????channel ????????????
     */
    private void deleteSubTopic(MqttChannel mqttChannel) {
        Set<String> topics = mqttChannel.getTopic();
        topics.parallelStream().forEach(topic -> {
            mqttChannelCache.invalidate(topic);
            cacheMap.delete(getTopic(topic), mqttChannel);
        });
    }


    /**
     * ??????????????????
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
     * ??????????????????
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
     * ?????????????????????
     */
    @Override
    public void repeatLogin(Channel channel, String deviceId, MqttConnectMessage mqttConnectMessage) throws Exception {
        /**????????????????????????**/
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
                throw new ConnectionException("???????????????" + responseModel.getResult());
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

                    /**??????channel????????????*/
                    channel.attr(_login).set(true);
                    channel.attr(_deviceId).set(deviceId);

                    /**???????????????CONNACK????????????*/
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

                    /**??????session????????????*/
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
                    /**????????????????????????????????????*/
                    executorService.execute(() -> {
                        businessServiceImpl.doLinePacket(id.asShortText(), deviceId, true);
                    });
                }
            } catch (Exception e) {
                System.out.println("..............???????????????????????????.............");
            } finally {
                oldChannel.disconnect();
            }
        }
    }

}
