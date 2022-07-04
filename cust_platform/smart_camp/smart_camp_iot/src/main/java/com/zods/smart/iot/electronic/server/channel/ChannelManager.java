package com.zods.smart.iot.electronic.server.channel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * @Description:通道管理
 * @create Author:jianglong
 * @create 2022-07-01
 */
public class ChannelManager {
	
	private ChannelManager() {

	}
	private static ChannelManager channelManager;

	//key:设备编号, ClientInfo:客户端连接信息
	private static ConcurrentMap<String, ClientInfo> serverClientMap = new ConcurrentHashMap<String, ClientInfo>();
    
	public static ChannelManager getInstance() {
		if (channelManager == null){
			channelManager = new ChannelManager();
		}
		return channelManager;
	}


	/**获取连接对话*/
	public ClientInfo getClientInfo(String debiceCode){
		return serverClientMap.get(debiceCode);
	}
	/**保存连接对话*/
	public void addClientInfo(String debiceCode, ClientInfo clientInfo){
		serverClientMap.put(debiceCode,clientInfo);
	}



}
