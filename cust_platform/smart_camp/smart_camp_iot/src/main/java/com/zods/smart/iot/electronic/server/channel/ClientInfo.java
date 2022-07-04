package com.zods.smart.iot.electronic.server.channel;
import io.netty.channel.Channel;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
/**
 * @Description:客户端连接信息
 * @create Author:jianglong
 * @create 2022-07-01
 */
@Data
@Builder
public class ClientInfo implements Serializable {
	
	//ip地址
	private String ip;
	
	//端口号
	private String port;
	
	//连接会话
	private Channel channel;
}
