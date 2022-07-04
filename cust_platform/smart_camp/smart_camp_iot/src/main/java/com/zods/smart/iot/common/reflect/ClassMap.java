package com.zods.smart.iot.common.reflect;
import com.zods.smart.iot.electronic.server.protocal.ElectronicEquipStatus;
import com.zods.smart.iot.electronic.server.protocal.ElectronicOnline;
import com.zods.smart.iot.electronic.server.protocal.ElectronicSidedeFenseCmdReturn;
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
	     /**电子围栏红外震动-心跳信息*/
	    map.put("1", new ClassHandler(ElectronicOnline.class));
		/**电子围栏红外震动-状态报文*/
		map.put("2", new ClassHandler(ElectronicEquipStatus.class));
		/**布防撤防命令应答报文*/
		map.put("133", new ClassHandler(ElectronicSidedeFenseCmdReturn.class));
	}
}
