package com.zods.smart.iot.gunrfid.server.handler;
import com.zods.smart.iot.gunrfid.server.protocal.GunRfidPacketHead;
import com.zods.smart.iot.gunrfid.service.GunRfidServerService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
/**
 * @author jianglong
 * @description QZ-RFID数据处理
 * @create 2022-07-13
 **/
@ChannelHandler.Sharable
@Component("gunRfidServerHandler")
@Slf4j
public class GunRfidServerHandler extends SimpleChannelInboundHandler<GunRfidPacketHead> {

    //业务处理模块
    @Resource
    private GunRfidServerService gunRfidServerServiceImpl;

    /**服务端业务接收到数据*/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GunRfidPacketHead gunRfidPacketHead) throws Exception {
        if(gunRfidPacketHead !=null){
            //数据包协议处理
            if(!this.gunRfidServerServiceImpl.successBusiness(ctx,gunRfidPacketHead)) {
                log.error("接收报文处理模块ElectronicServerHandler处理异常");
            }
        }
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
                    log.warn("读超时");
                    //channel.close();
                case WRITER_IDLE:
                    log.warn("写超时");
                    //channel.close();
                case ALL_IDLE:
                    log.warn("读写超时");
                    //channel.close();
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
}
