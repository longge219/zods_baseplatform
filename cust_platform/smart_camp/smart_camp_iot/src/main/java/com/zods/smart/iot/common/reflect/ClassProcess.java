package com.zods.smart.iot.common.reflect;
/**
 * @Description：获取对应报文类对象
 * @create Author:jianglong
 * @create Date:2022-06-11
 * @version V1.0
 */
public interface ClassProcess {
	/**
	 * @method:getClassByTag
	 * @Description: 通过tag拿到对应的Class
	 * @create Author:jianglong
	 * @create Date:2015-12-21
	 * @exception
	 * @Modification:	
	 * @param tag
	 * @return
	 */
	public Class<?> getClassByTag(String tag);

	/**
	 * @method:verifyTag
	 * @Description: 验证是否能够通过Tag取得到对应的Class
	 * @create Author:jianglong
	 * @create Date:2015-12-21
	 * @exception
	 * @Modification:	
	 * @param tag
	 * @return
	 */
	public boolean verifyTag(String tag);

 

}
