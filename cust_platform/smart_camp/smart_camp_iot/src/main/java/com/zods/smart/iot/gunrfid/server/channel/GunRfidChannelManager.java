package com.zods.smart.iot.gunrfid.server.channel;
import io.netty.channel.Channel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * @Description:通道管理
 * @create Author:jianglong
 * @create 2022-07-01
 */
public class GunRfidChannelManager {

	private GunRfidChannelManager() {

	}
	private static GunRfidChannelManager gunRfidChannelManager;

	//key:设备编号, ClientInfo:客户端连接信息
	private static ConcurrentMap<String, Channel> serverClientMap = new ConcurrentHashMap<String, Channel>();
    
	public static GunRfidChannelManager getInstance() {
		if (gunRfidChannelManager == null){
			gunRfidChannelManager = new GunRfidChannelManager();
		}
		return gunRfidChannelManager;
	}


	/**获取连接对话*/
	public Channel getChannel(String debiceIP){
		return serverClientMap.get(debiceIP);
	}

	/**保存连接对话*/
	public void addChannel(String debiceIP, Channel channel){
		serverClientMap.put(debiceIP,channel);
	}

	/**删除连接*/
	public void delChannel(String debiceIP){
		serverClientMap.remove(debiceIP);
	};

	/**是否存在*/
	public boolean hasChannel(String debiceIP){
		if(serverClientMap.containsKey(debiceIP)){
			if(serverClientMap.get(debiceIP).isActive()){
				return true;
			}
		}
		return false;
	}

}
