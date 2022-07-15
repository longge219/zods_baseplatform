package com.zods.smart.iot.gunrfid.service;
import com.zods.smart.iot.gunrfid.server.protocal.GunRfidPacketHead;
import io.netty.channel.ChannelHandlerContext;
/**
 * @description 服务端报文业务处理接口
 * @author jianglong
 * @create 2018-03-20
 **/
public interface GunRfidServerService {
    /**
     * @method:消息正确接收业务处理
     * @param:ctx 通道
     * @param:packetHead 解译后数据包
     * @result: boolean
     */
    public boolean successBusiness(ChannelHandlerContext ctx, GunRfidPacketHead packetHead) throws Exception;


}
