package com.zods.smart.iot.electronic.server.protocal;
import lombok.Data;
import java.net.InetSocketAddress;

/**
 * @Description:网络传输报文头封装
 * @create Author:jianglong
 * @create Date:2022-06-11
 * @version V1.0
 */
@Data
public abstract class ElectronicPacketHead {
	//消息类型
	public abstract int getCommandType();

	//报文长度
	public int pakcetLen;

	//主机地址
	public  int  hostAddress;
	//设备地址
	public  int equipAddress;

	//用户组编号高字节
	public byte userGroupH;

	//用户组编号高字节
	public byte userGroupL;

	//扩展命令
	public byte extendedStandby;

	//UDP远程地址格式为：IP:PORT
	public InetSocketAddress remoteAddress;


}
