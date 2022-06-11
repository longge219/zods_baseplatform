package com.zods.smart.iot.electronic.server.code;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import com.zods.smart.iot.electronic.server.protocal.PacketHead;
import com.zods.smart.iot.electronic.server.reflect.ClassProcessImpl;
import com.zods.smart.iot.electronic.server.reflect.SubAnnotation;
import com.zods.smart.iot.electronic.utils.UnsignedNumber;
import lombok.extern.slf4j.Slf4j;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
/**
 * @description 自定义解码器
 * @author jianglong
 * @create 2022-06-11
 **/
@Slf4j
public class MyMessageDecoder extends ByteToMessageDecoder {

	/**报文最小长度*/
	private static Integer packetHeadSize = 8;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		/**消息体长度判断*/
		if (in.readableBytes() < packetHeadSize) {
			/**报文长度小于报头最小长度(25字节) 内容不够，需要下一批发过来的内容*/
			return;
		}
		/**消息头开始解码*/
		//消息长度
		int msgLength = UnsignedNumber.getUnsignedByte(in.readByte());
		//主机地址
		int hostAddress = UnsignedNumber.getUnsignedByte(in.readByte());
		//设备地址
		int equipAddress = UnsignedNumber.getUnsignedByte(in.readByte());
		if(equipAddress ==1){
			log.info("设备编号："+hostAddress+equipAddress);
		}
		log.info("设备编号："+hostAddress+equipAddress);
		//用户组编号
		byte userGroupH  = in.readByte();
		byte userGroupL = in.readByte();
		//扩展备用
		byte extendedStandby = in.readByte();
		//命令类型
		int commandType = UnsignedNumber.getUnsignedByte(in.readByte());

		/**消息体开始解码*/
		ClassProcessImpl classProcessImpl = new ClassProcessImpl();
		PacketHead message = null;
		if (classProcessImpl.verifyTag(String.valueOf(commandType))){
			Class<?> messageClass = classProcessImpl.getClassByTag(String.valueOf(commandType));
			message = (PacketHead)getObjectByBuffer(messageClass,in,msgLength);
			message.setPakcetLen(msgLength);
			message.setHostAddress(hostAddress);
			message.setHostAddress(1);//默认地址是0
			message.setEquipAddress(equipAddress);
			message.setUserGroupH(userGroupH);
			message.setUserGroupL(userGroupL);
			message.setExtendedStandby(extendedStandby);
			out.add(message); // 输出
			return;
		} else {
			byte[] otherPacket = new byte[msgLength - 7];
			in.readBytes(otherPacket);
			log.error("现在识别在线和状态监控数据");
			return;
		}

	}


	public Object getObjectByBuffer(Class<?> clazz, ByteBuf in,int msgLength) throws Exception {

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
				Object v = getValues(sub.type(), sub.len(),sub.mark(), msgLength,in);
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
	public Object getValues(String type, String len, String mark,int msgLength,ByteBuf ioBuffer) throws Exception {
		int v = Integer.parseInt(len);
		/** 解码成byte属性 */
		if (type.equalsIgnoreCase("byte")) {
			if(mark.equals("")){
				return ioBuffer.readByte();
			}
			else if(mark.equals("byLeangth") && msgLength == 12){
				return ioBuffer.readByte();
			}else{
				return (byte)0;
			}

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
