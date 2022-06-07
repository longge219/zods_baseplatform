package com.zods.mqtt.sever.protocol.code.reflect;

public class ClassHandler {

	private Class<?> clazz;

	public ClassHandler() {
		super();
	}

	public ClassHandler(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}

   
	public Class<?> getClazz() {
	   return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	} 

}
