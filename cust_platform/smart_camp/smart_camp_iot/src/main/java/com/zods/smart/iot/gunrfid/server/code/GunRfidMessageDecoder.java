package com.zods.smart.iot.gunrfid.server.code;
import org.apache.log4j.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
/**
 * 
 * 自定义解码器
 *
 */
public class GunRfidMessageDecoder extends ByteToMessageDecoder {

	/**
	 * 日志
	 */
	private static Logger logger = Logger.getLogger(GunRfidMessageDecoder.class);


	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
          logger.info("开始解码.....");
	}



}
