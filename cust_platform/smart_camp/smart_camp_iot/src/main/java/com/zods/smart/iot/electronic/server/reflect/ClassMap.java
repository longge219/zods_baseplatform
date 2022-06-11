package com.zods.smart.iot.electronic.server.reflect;
import com.zods.smart.iot.electronic.server.protocal.EquipStatus;
import com.zods.smart.iot.electronic.server.protocal.Online;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description:封装报文与报文id对应集合
 * @create Author:jianglong
 * @create Date:2022-06-11
 * @version V1.0
 */
public abstract class ClassMap implements ClassProcess {
	protected static final Map<String, ClassHandler> map=new HashMap<String, ClassHandler>();
	protected void init(){
	     /**心跳信息*/
	    map.put("1", new ClassHandler(Online.class));
		/**状态报文*/
		map.put("2", new ClassHandler(EquipStatus.class));
	}
}
