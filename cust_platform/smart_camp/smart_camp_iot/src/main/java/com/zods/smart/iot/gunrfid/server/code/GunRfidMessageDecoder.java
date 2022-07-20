package com.zods.smart.iot.gunrfid.server.code;
import com.zods.smart.iot.common.reflect.ClassProcessImpl;
import com.zods.smart.iot.common.reflect.SubAnnotation;
import com.zods.smart.iot.common.utils.UnsignedNumber;
import com.zods.smart.iot.gunrfid.server.protocal.GunRfidPacketHead;
import lombok.extern.slf4j.Slf4j;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
/**
 * @description 自定义QZ-RFID解码器
 * @author jianglong
 * @create 2022-07-18
 **/
@Slf4j
public class GunRfidMessageDecoder extends ByteToMessageDecoder {

	/**报文最小长度*/
	private static Integer packetHeadSize = 3;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception  {
		/**消息体长度判断*/
		if (in.readableBytes() >= packetHeadSize) {
			 /**解析报文头*/
			//开始符号(0xAA)
			int header = UnsignedNumber.getUnsignedByte(in.readByte());
			if(header != 0xAA){
				return;
			}
			/**
			 * 指令帧类型
			 * 0x00: 命令帧: 由上位机发送给 HZ9X 芯片
			 * 0x01 响应帧: 由 HZ9X 芯片发回给上位机
			 * 0x02 通知帧: 由 HZ9X 芯片发回给上位机
			 */
			int type = UnsignedNumber.getUnsignedByte(in.readByte());

			//指令代码
            int command  = UnsignedNumber.getUnsignedByte(in.readByte());
			//GUN-RFID多次轮询指令通知
			if(command == 34){
				//轮训指令通知为分包发送，以结束符为准。
				int end = UnsignedNumber.getUnsignedByte(in.getByte(in.readableBytes()));
				if(end != 0xDD){
					//报文不是以0xDD结束， 内容不够，需要下一批发过来的内容
					in.readerIndex(in.readerIndex() - 3);
					return;
				}
			}
			/**消息体开始解码*/
			ClassProcessImpl classProcessImpl = new ClassProcessImpl();
			GunRfidPacketHead message = null;
			if (classProcessImpl.verifyTag(String.valueOf(command))){
				Class<?> messageClass = classProcessImpl.getClassByTag(String.valueOf(command));
				message = (GunRfidPacketHead)getObjectByBuffer(messageClass,in);
			} else {
				log.warn("接收到未定义协议编码："+ String.valueOf(command));
			}
			/**校验码校验*/
			out.add(message);
		}else{
			/**包长度不够*/
			return;
		}
		return;
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
