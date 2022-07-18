package com.zods.smart.iot.gunrfid.server.protocal;
import lombok.Data;
/**
 * @Description:网络传输报文头封装
 * @create Author:jianglong
 * @create Date:2022-06-11
 * @version V1.0
 */
@Data
public abstract class GunRfidPacketHead {
	/**消息类型*/
	public abstract int getCommandType();

	/**开始符号(0xAA)*/
	public int header;

	/**
	 * 指令帧类型
	 * 0x00: 命令帧: 由上位机发送给 HZ9X 芯片
	 * 0x01 响应帧: 由 HZ9X 芯片发回给上位机
	 * 0x02 通知帧: 由 HZ9X 芯片发回给上位机
	 */
	private int type;

	/**指令代码*/
	private int command;
}
