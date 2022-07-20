package com.zods.smart.iot.common.reflect;
import com.zods.smart.iot.electronic.server.protocal.ElectronicEquipStatus;
import com.zods.smart.iot.electronic.server.protocal.ElectronicOnline;
import com.zods.smart.iot.electronic.server.protocal.ElectronicSidedeFenseCmdReturn;
import com.zods.smart.iot.gunrfid.server.protocal.base.GunRfidLogin;
import com.zods.smart.iot.gunrfid.server.protocal.base.GunRfidOnline;
import com.zods.smart.iot.gunrfid.server.protocal.notice.GunRfidMultiplePollingNotice;
import com.zods.smart.iot.gunrfid.server.protocal.response.GunRfidMultiplePollingResponse;
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
		                         /**电子围栏红外震动数据包对象*/
	     /**电子围栏红外震动-心跳信息*/
	    map.put("1", new ClassHandler(ElectronicOnline.class));
		/**电子围栏红外震动-状态报文*/
		map.put("2", new ClassHandler(ElectronicEquipStatus.class));
		/**布防撤防命令应答报文*/
		map.put("133", new ClassHandler(ElectronicSidedeFenseCmdReturn.class));
		                         /**GUN-RFID数据包对象*/
		/**GUN-RFID-心跳信息*/
		map.put("51", new ClassHandler(GunRfidOnline.class));
        /**GUN-RFID-登陆信息*/
		map.put("68", new ClassHandler(GunRfidLogin.class));
		/**GUN-RFID多次轮询指令通知*/
		map.put("34", new ClassHandler(GunRfidMultiplePollingNotice.class));
		/**GUN-RFID多次轮询指令响应*/
		map.put("255", new ClassHandler(GunRfidMultiplePollingResponse.class));
	}
}
