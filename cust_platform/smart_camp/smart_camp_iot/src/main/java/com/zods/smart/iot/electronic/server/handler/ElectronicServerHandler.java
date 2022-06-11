package com.zods.smart.iot.electronic.server.handler;
import com.zods.smart.iot.electronic.server.protocal.PacketHead;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
/**
 * @description 消息解码后处理中心
 * @author jianglong
 * @create 2022-06-11
 **/
@ChannelHandler.Sharable
@Component
@Slf4j
public class ElectronicServerHandler extends SimpleChannelInboundHandler<PacketHead> {
    /**
     * 服务端业务接收到数据
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, PacketHead packetHead) throws Exception {
        log.info("MqttServerAddr【" + ctx.channel().localAddress() + "】,:" + packetHead.getEquipAddress() + ">>>>>>>> id: " + ctx.channel().id() + " , active: " + ctx.channel().isActive() + ", open: " + ctx.channel().isOpen() + ", remoteAddr: " + ctx.channel().remoteAddress());
    }

    /**客户端关闭连接*/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive >>>>> id: " + ctx.channel().id() + " , active: " + ctx.channel().isActive() + ", open: " + ctx.channel().isOpen() + ",remoteAddr: " + ctx.channel().remoteAddress());
        ctx.channel().close();
        super.channelInactive(ctx);
    }

    /**超时处理*/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("userEventTriggered >>>>> id: " + ctx.channel().id() + " ,heartBeat is overtime..............");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            Channel channel = ctx.channel();
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    channel.close();
                case WRITER_IDLE:
                    channel.close();
                case ALL_IDLE:
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
        
    }
}
