package com.zods.smart.iot.electronic.server.reflect;

/**
 * @Description：获取对应报文类对象
 * @create Author:jianglong
 * @create Date:2022-06-11
 * @version V1.0
 */
public class ClassProcessImpl extends ClassMap {
	public ClassProcessImpl(){
		init();
	}
	/**
	 * @method:getClassByTag
	 * @Description: 通过tag拿到对应的Class
	 * @create Author:jiangl
	 * @create Date:2014-12-22
	 * @exception
	 * @Modification:	
	 * @param tag
	 * @return
	 */
	@Override
	public Class<?> getClassByTag(String tag) {
		ClassHandler handlerClass=map.get(tag);
		return handlerClass.getClazz();
	}
	/**
	 * @method:verifyTag
	 * @Description: 验证是否能够通过Tag取得到对应的Class
	 * @create Author:jiangl
	 * @create Date:2014-12-22
	 * @exception
	 * @Modification:	
	 * @param tag
	 * @return
	 */
	@Override
	public boolean verifyTag(String tag) {
		ClassHandler handlerClass=map.get(tag);
		if(handlerClass !=null){
			return true;	
			}else{
				return false;
			}
	}

}
