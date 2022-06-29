package com.zods.smart.iot.common.reflect;
/**
 * @Description:报文标识类对应类
 * @create Author:jianglong
 * @create Date:2022-06-11
 * @version V1.0
 */
public class ClassHandler {
	
	public ClassHandler() {
		super();
	}
	public ClassHandler(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}
   
	private Class<?> clazz; //对应的类
	
   
	public Class<?> getClazz() {
	   return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	} 

}
