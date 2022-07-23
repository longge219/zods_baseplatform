package com.zods.smart.iot.gunrfid.server.scan;

import com.zods.smart.iot.common.topic.GunRfidData;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description:缓存EPC数据
 * @create Author:jianglong
 * @create 2022-07-23
 */
@Slf4j
public class EpcManager {

	private final static EpcManager epcManager = new EpcManager();

	//观测数据
	private static ConcurrentMap<String, GunRfidData> epcMap;

	 private EpcManager(){
		 epcMap = new ConcurrentHashMap<String, GunRfidData>();
	}

	public static EpcManager getInstance() {
		 return epcManager;
	}

	/**添加天线识别的EPC信息*/
	public void addEpc(String deviceIP, String epc){
		if(epcMap.containsKey(deviceIP)){
			log.info("已添加数据，IP:" + deviceIP);
			GunRfidData gunRfidData = epcMap.get(deviceIP);
			gunRfidData.getEpcs().add(epc);
		}else{
			log.info("第一次添加数据，IP:" + deviceIP);
			GunRfidData gunRfidData = new GunRfidData();
			gunRfidData.setDeviceIP(deviceIP);
			Set<String> epcs =  new HashSet<String>();
			epcs.add(epc);
			gunRfidData.setEpcs(epcs);
			epcMap.put(deviceIP, gunRfidData);
		}
	}
	/**获取天线识别的EPC信息*/
	public List<GunRfidData> getGunRfidData(){
		List<GunRfidData>  gunRfidDataLList = new ArrayList<GunRfidData>();
		Iterator<Map.Entry<String, GunRfidData>> it = epcMap.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry<String, GunRfidData> entry = it.next();
			GunRfidData gunRfidData = entry.getValue();
			gunRfidDataLList.add(gunRfidData);
			it.remove();
		}
		return gunRfidDataLList;
	}
}
