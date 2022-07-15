package com.zods.smart.iot.gunrfid.server.code;

import com.zods.smart.iot.gunrfid.server.protocal.GunRfidPacketHead;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
/**
 * @Description:
 * @create Author:jianglong
 * @create Date:2022-06-11
 * @version V1.0
 */
public class GunRfidMessageEncoder extends MessageToByteEncoder<GunRfidPacketHead> {

	@Override
	protected void encode(ChannelHandlerContext ctx, GunRfidPacketHead gunRfidPacketHead, ByteBuf out) throws Exception {


	}
}
