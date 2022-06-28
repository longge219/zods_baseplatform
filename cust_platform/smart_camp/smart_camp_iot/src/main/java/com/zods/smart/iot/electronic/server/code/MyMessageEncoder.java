package com.zods.smart.iot.electronic.server.code;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import com.zods.smart.iot.electronic.server.protocal.PacketHead;
import com.zods.smart.iot.electronic.server.reflect.SubAnnotation;
import com.zods.smart.iot.electronic.utils.CheckSumUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
/**
 * @description 自定义编码器
 * @author jianglong
 * @create 2022-06-11
 **/
public class MyMessageEncoder extends MessageToByteEncoder<PacketHead> {

	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, PacketHead packetHead, ByteBuf out) throws Exception {
		/**===========报文头start===============*/
		out.writeByte((byte)packetHead.getPakcetLen());//包长度
		out.writeByte((byte)packetHead.getHostAddress());//主机地址
		out.writeByte((byte)packetHead.getEquipAddress());//设备地址
		out.writeByte(packetHead.getUserGroupH());//用户组编号高字节
		out.writeByte(packetHead.getUserGroupL()); //用户组编号高字节
		out.writeByte(packetHead.getExtendedStandby());//扩展命令
		out.writeByte((byte)packetHead.getCommandType());//命令类型
		/**===========报文头end===============*/
		/**===========报文体编码区start===============*/
		Field[] fields =packetHead.getClass().getDeclaredFields();
		for (Field field : fields) {
			//获取该类成员变量的注释
			Annotation annotation = field.getAnnotation(SubAnnotation.class);
			//注释不为空并且属性名不为校验属性，将属性添加到buf中
			if(annotation != null){
				SubAnnotation subAnnotation = (SubAnnotation)annotation;
				field.setAccessible(true);
				// 得到此属性的值
				Object fileVaule = field.get(packetHead);
				fileldEncode(out, subAnnotation.type(), subAnnotation.len(), fileVaule);
			}
		}
		//channelHandlerContext.write(out);
		/**===========报文体编码区end===============*/
		/**===========验证码start===============*/
		ByteBuf checkBuf = out.copy(0,out.writerIndex());
		byte[] checkData = new byte[checkBuf.readableBytes()];
		checkBuf.readBytes(checkData);
		//验证码
		byte chekCode =  CheckSumUtil.getCheckSum(checkData);
		out.writeByte(chekCode);
		/**===========验证码end===============*/
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
