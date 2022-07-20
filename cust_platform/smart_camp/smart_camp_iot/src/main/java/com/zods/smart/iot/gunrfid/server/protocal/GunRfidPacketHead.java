package com.zods.smart.iot.gunrfid.server.protocal;
/**
 * @Description:网络传输报文头封装
 * @create Author:jianglong
 * @create Date:2022-06-11
 * @version V1.0
 */
public abstract class GunRfidPacketHead {
	/**消息指令*/
	public abstract int getCommand();

	/**
	 * 指令帧类型
	 * 0x00: 命令帧: 由上位机发送给 HZ9X 芯片
	 * 0x01 响应帧: 由 HZ9X 芯片发回给上位机
	 * 0x02 通知帧: 由 HZ9X 芯片发回给上位机
	 */
	public abstract int getCommandType();
}
