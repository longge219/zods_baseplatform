package com.zods.smart.iot.gunrfid.server.code;
import com.zods.smart.iot.common.reflect.SubAnnotation;
import com.zods.smart.iot.gunrfid.server.protocal.GunRfidPacketHead;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
/**
 * @Description: 自定义QZ-RFID编码器
 * @create Author:jianglong
 * @create Date:2022-07-18
 * @version V1.0
 */
public class GunRfidMessageEncoder extends MessageToByteEncoder<GunRfidPacketHead> {

	@Override
	protected void encode(ChannelHandlerContext ctx, GunRfidPacketHead gunRfidPacketHead, ByteBuf outByteBuf) throws Exception {
		/**===========报文头start===============*/
		outByteBuf.writeByte((byte)gunRfidPacketHead.getHeader());//开始符号(0xAA)
		outByteBuf.writeByte((byte)gunRfidPacketHead.getType());//指令帧类型
		outByteBuf.writeByte((byte)gunRfidPacketHead.getCommand());//指令代码
		/**===========报文头end===============*/
		/**===========报文体编码区start===============*/
		Field[] fields =gunRfidPacketHead.getClass().getDeclaredFields();
		for (Field field : fields) {
			//获取该类成员变量的注释
			Annotation annotation = field.getAnnotation(SubAnnotation.class);
			//注释不为空并且属性名不为校验属性，将属性添加到buf中
			if(annotation != null){
				SubAnnotation subAnnotation = (SubAnnotation)annotation;
				field.setAccessible(true);
				// 得到此属性的值
				Object fileVaule = field.get(gunRfidPacketHead);
				fileldEncode(outByteBuf, subAnnotation.type(), subAnnotation.len(), fileVaule);
			}
		}
		/**===========报文体编码区end===============*/
		/**返回数据*/
	}

	/**
	 * @Title: encode
	 * @Description: java反射和递归思想实现报文体的编码
	 * @create Author:jianglong
	 * @create 2016-4-14
	 * @param out
	 * @param type
	 * @param length
	 * @return IoBuffer
	 * @throws Exception
	 */
	public void fileldEncode( ByteBuf out, String type, String length, Object value) throws Exception {
		int leng = Integer.parseInt(length);
		if (type.equalsIgnoreCase("byte")) {
			// 该属性为byte
			out.writeByte((Byte) value);
		} else if (type.equalsIgnoreCase("short")) {
			// 该属性为short
			out.writeShort((Short) value);
		} else if (type.equalsIgnoreCase("int")) {
			// 该属性为int
			out.writeInt((Integer) value);
		} else if (type.equalsIgnoreCase("long")) {
			// 该属性为long
			out.writeLong((Long) value);
		} else if (type.equalsIgnoreCase("byteArray")) {
			// 该属性为byteArray
			out.writeBytes((byte[]) value);
		}
	}
}
