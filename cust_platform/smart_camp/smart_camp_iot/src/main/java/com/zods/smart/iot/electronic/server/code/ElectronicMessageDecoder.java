package com.zods.smart.iot.electronic.server.code;
import com.zods.smart.iot.common.reflect.ClassProcessImpl;
import com.zods.smart.iot.common.reflect.SubAnnotation;
import com.zods.smart.iot.common.utils.CheckSumUtil;
import com.zods.smart.iot.common.utils.UnsignedNumber;
import com.zods.smart.iot.electronic.server.protocal.ElectronicPacketHead;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.List;
/**
 * @description 自定义解码器
 * @author jianglong
 * @create 2022-06-11
 **/
@Slf4j
public class ElectronicMessageDecoder extends MessageToMessageDecoder<DatagramPacket> {

	/**协议类型*/
	private String protocolType;

	public ElectronicMessageDecoder(String protocolType) {
		this.protocolType=protocolType;
	}

	/**报文最小长度*/
	private static Integer packetHeadSize = 8;

	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) throws Exception {
		/**获取接收到的数据流*/
		ByteBuf inByteBuf = datagramPacket.content();
		InetSocketAddress remoteAddress = datagramPacket.sender();
		/**消息体长度判断*/
		if (inByteBuf.readableBytes() >= packetHeadSize) {
			/**消息头开始解码*/
			//消息长度
			int msgLength = UnsignedNumber.getUnsignedByte(inByteBuf.readByte());
			//主机地址
			int hostAddress = UnsignedNumber.getUnsignedByte(inByteBuf.readByte());
			//设备地址
			int equipAddress = UnsignedNumber.getUnsignedByte(inByteBuf.readByte());
			//用户组编号
			byte userGroupH  = inByteBuf.readByte();
			byte userGroupL = inByteBuf.readByte();
			//扩展备用
			byte extendedStandby = inByteBuf.readByte();
			//命令类型
			int commandType = UnsignedNumber.getUnsignedByte(inByteBuf.readByte());
			/**消息体开始解码*/
			ClassProcessImpl classProcessImpl = new ClassProcessImpl();
			ElectronicPacketHead message = null;
			if (classProcessImpl.verifyTag(String.valueOf(commandType))){
				Class<?> messageClass = classProcessImpl.getClassByTag(String.valueOf(commandType));
				message = (ElectronicPacketHead)getObjectByBuffer(messageClass,inByteBuf);
				message.setPakcetLen(msgLength);
				message.setHostAddress(hostAddress);
				message.setEquipAddress(equipAddress);
				message.setUserGroupH(userGroupH);
				message.setUserGroupL(userGroupL);
				message.setExtendedStandby(extendedStandby);
			} else {
				log.warn("接收到未定义协议编码："+ String.valueOf(commandType));
				inByteBuf.release();
			}
			/**校验码校验*/
			 ByteBuf checkBuf = inByteBuf.copy(0,inByteBuf.readerIndex());
			 byte[] checkData = new byte[checkBuf.readableBytes()];
			 checkBuf.readBytes(checkData);
			 if(CheckSumUtil.isCheckElcSumValid(checkData,inByteBuf.readByte())){
				 //远程地址
				 message.setRemoteAddress(remoteAddress);
			     out.add(message);
			 }else{
				 log.error("接收到数据包的校验码不匹配");
				 inByteBuf.release();
			 }
		}else{
			/**包长度不够*/
			inByteBuf.release();
		}
	}

	private Object getObjectByBuffer(Class<?> clazz, ByteBuf inByteBuf) throws Exception {
		// 实例化类
		Object obj = clazz.newInstance();
		// 得到类中private 的属性
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			// 得到属性对应的注释
			Annotation ano = field.getAnnotation(SubAnnotation.class);
			if (ano != null) {
				SubAnnotation sub = (SubAnnotation) ano;
				field.setAccessible(true);
				Object v = getValues(sub.type(), sub.len(),sub.mark(),inByteBuf);
				if (v == null) {
					log.error("属性值为空NULL");
				} else {
					field.set(obj, v);// 得到此属性设值
				}
			}
		}
		return obj;
	}

	/**
	 * @Title: getValues
	 * @Description: 根据长度从buf里取得字节，通过类型进行解码，返回解码后的数据
	 * @create Author:jianglong
	 * @create 2015-12-23
	 * @param type  类型
	 * @param len  长度
	 * @param ioBuffer
	 * @return Object
	 * @throws Exception
	 */
	private Object getValues(String type, String len, String mark,ByteBuf ioBuffer) throws Exception {
		//数组长度
		int v = Integer.parseInt(len);
		/** 解码成byte属性 */
		if (type.equalsIgnoreCase("byte")) {
			return ioBuffer.readByte();
		}
		/** 解码成short属性 */
		else if (type.equalsIgnoreCase("short")) {
			return ioBuffer.readShort();
		}
		/** 解码成int属性 */
		else if (type.equalsIgnoreCase("int")) {
			return ioBuffer.readInt();
		}
		/** 解码成byteArray属性 */
		else if (type.equalsIgnoreCase("byteArray")) {
			byte[] bytesValue = new byte[v];
			ioBuffer.readBytes(bytesValue);
			return bytesValue;
		}
		/** 解码没有标注类型 */
		else {
			log.error("没有找到对应的类型");
			return null;
		}
	}

}
