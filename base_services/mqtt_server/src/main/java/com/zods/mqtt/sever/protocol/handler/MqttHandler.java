package com.zods.mqtt.sever.protocol.handler;
import com.zods.mqtt.sever.business.service.BusinessService;
import com.zods.mqtt.sever.protocol.channel.MqttChannelService;
import com.zods.mqtt.sever.protocol.channel.impl.AbstractChannelService;
import com.zods.mqtt.sever.protocol.handler.service.MqttHandlerService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;
/**
 * @author jianglong
 * @description MQTT处理
 * @create 2019-09-09
 **/
@ChannelHandler.Sharable
@Component
@Slf4j
public class MqttHandler extends SimpleChannelInboundHandler<MqttMessage> {


    @Autowired
    private MqttChannelService mqttChannelServiceImpl;

    @Autowired
    private MqttHandlerService mqttHandlerServiceImpl;

    @Autowired
    private BusinessService businessServiceImpl;

    /**
     * 服务端业务接收到数据
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, MqttMessage mqttMessage) throws Exception {
        MqttFixedHeader mqttFixedHeader = mqttMessage.fixedHeader();
        log.info("MqttServerAddr【" + ctx.channel().localAddress() + "】,read >>>>>>>>mqttType:" + mqttFixedHeader.messageType().name() + ">>>>>>>> id: " + ctx.channel().id() + " , active: " + ctx.channel().isActive() + ", open: " + ctx.channel().isOpen() + ", remoteAddr: " + ctx.channel().remoteAddress());
        Optional.ofNullable(mqttFixedHeader).ifPresent((value) -> doMessage(ctx, mqttMessage));
    }

    /**
     * 客户端关闭连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive >>>>> id: " + ctx.channel().id() + " , active: " + ctx.channel().isActive() + ", open: " + ctx.channel().isOpen() + ",remoteAddr: " + ctx.channel().remoteAddress());
        /**同步设备离线信息*/
        AttributeKey<String> deviceIdAttr = AbstractChannelService._deviceId;
        String deviceId = ctx.channel().attr(deviceIdAttr).get();
        if (StringUtils.isNotEmpty(deviceId)) {
            businessServiceImpl.doLinePacket(ctx.channel().id().asShortText(), deviceId, false);
            log.info(".....................equipCode is " + deviceId + " ,the channel is closed , this equipment is downLine ......................");
        }
        ctx.channel().close();
        super.channelInactive(ctx);
    }

    /**
     * 超时处理-
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("userEventTriggered >>>>> id: " + ctx.channel().id() + " ,heartBeat is overtime..............");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            Channel channel = ctx.channel();
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    mqttChannelServiceImpl.closeSuccess(channel, false);
                    channel.close();
                case WRITER_IDLE:
                    mqttChannelServiceImpl.closeSuccess(channel, false);
                    channel.close();
                case ALL_IDLE:
                    mqttChannelServiceImpl.closeSuccess(channel, false);
                    channel.close();
            }
        }
        super.userEventTriggered(ctx, evt);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }

    /**
     * 处理消息
     */
    private void doMessage(ChannelHandlerContext channelHandlerContext, MqttMessage mqttMessage) {
        Channel channel = channelHandlerContext.channel();
        MqttFixedHeader mqttFixedHeader = mqttMessage.fixedHeader();
        //登录控制报文业务处理

        if (mqttFixedHeader.messageType().equals(MqttMessageType.CONNECT)) {
            if (!mqttHandlerServiceImpl.login(channel, (MqttConnectMessage) mqttMessage)) {
                channel.close();
            }
            return;
        }
        //未登录--控制报文处理
        if (StringUtils.isBlank(mqttChannelServiceImpl.getDeviceId(channel))) {
            channel.close();
            return;
        }
        //登录后--控制报文处理
        if (mqttChannelServiceImpl.getMqttChannel(mqttChannelServiceImpl.getDeviceId(channel)).isLogin()) {
            switch (mqttFixedHeader.messageType()) {
                case DISCONNECT:
                    mqttHandlerServiceImpl.disconnect(channel);
                    break;
                case PINGREQ:
                    mqttHandlerServiceImpl.pong(channel);
                    break;
                case SUBSCRIBE:
                    mqttHandlerServiceImpl.subscribe(channel, (MqttSubscribeMessage) mqttMessage);
                    break;
                case UNSUBSCRIBE:
                    mqttHandlerServiceImpl.unsubscribe(channel, (MqttUnsubscribeMessage) mqttMessage);
                    break;
                case PUBLISH:
                    mqttHandlerServiceImpl.publish(channel, (MqttPublishMessage) mqttMessage);
                    break;
                case PUBACK:
                    mqttHandlerServiceImpl.puback(channel, mqttMessage);
                    break;
                case PUBREC:
                    mqttHandlerServiceImpl.pubrec(channel, mqttMessage);
                    break;
                case PUBREL:
                    mqttHandlerServiceImpl.pubrel(channel, mqttMessage);
                    break;
                case PUBCOMP:
                    mqttHandlerServiceImpl.pubcomp(channel, mqttMessage);
                    break;
                default:
                    break;
            }
        }
    }
}
