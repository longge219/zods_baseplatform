package com.zods.smart.iot.electronic.server.code;
import com.zods.smart.iot.common.reflect.SubAnnotation;
import com.zods.smart.iot.common.utils.CheckSumUtil;
import com.zods.smart.iot.electronic.server.protocal.ElectronicPacketHead;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
/**
 * @description 自定义编码器
 * @author jianglong
 * @create 2022-06-11
 **/
public class ElectronicMessageEncoder  extends MessageToMessageEncoder<ElectronicPacketHead> {

	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, ElectronicPacketHead electronicPacketHead, List<Object> outList) throws Exception {
		 /**返回-ByteBuf*/
		ByteBuf outByteBuf = ByteBufAllocator.DEFAULT.buffer();
		/**===========报文头start===============*/
		outByteBuf.writeByte((byte)electronicPacketHead.getPakcetLen());//包长度
		outByteBuf.writeByte((byte)electronicPacketHead.getHostAddress());//主机地址
		outByteBuf.writeByte((byte)electronicPacketHead.getEquipAddress());//设备地址
		outByteBuf.writeByte(electronicPacketHead.getUserGroupH());//用户组编号高字节
		outByteBuf.writeByte(electronicPacketHead.getUserGroupL()); //用户组编号高字节
		outByteBuf.writeByte(electronicPacketHead.getExtendedStandby());//扩展命令
		outByteBuf.writeByte((byte)electronicPacketHead.getCommandType());//命令类型
		/**===========报文头end===============*/
		/**===========报文体编码区start===============*/
		Field[] fields =electronicPacketHead.getClass().getDeclaredFields();
		for (Field field : fields) {
			//获取该类成员变量的注释
			Annotation annotation = field.getAnnotation(SubAnnotation.class);
			//注释不为空并且属性名不为校验属性，将属性添加到buf中
			if(annotation != null){
				SubAnnotation subAnnotation = (SubAnnotation)annotation;
				field.setAccessible(true);
				// 得到此属性的值
				Object fileVaule = field.get(electronicPacketHead);
				fileldEncode(outByteBuf, subAnnotation.type(), subAnnotation.len(), fileVaule);
			}
		}
		//channelHandlerContext.write(out);
		/**===========报文体编码区end===============*/
		/**===========验证码start===============*/
		ByteBuf checkBuf = outByteBuf.copy(0,outByteBuf.writerIndex());
		byte[] checkData = new byte[checkBuf.readableBytes()];
		checkBuf.readBytes(checkData);
		//验证码
		byte chekCode =  CheckSumUtil.getElcCheckSum(checkData);
		outByteBuf.writeByte(chekCode);
		/**===========验证码end===============*/
		/**返回数据*/
		DatagramPacket datagramPacket  = new DatagramPacket(outByteBuf, electronicPacketHead.getRemoteAddress());
		outList.add(datagramPacket);
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
