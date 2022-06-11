package com.zods.smart.iot.electronic.server.reflect;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * @Description：报文数据注释
 * @create Author:jiangl
 * @create Date:2022-06-11
 * @version V1.0
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface SubAnnotation {
	public String name(); //报文中数据名称
	public String type(); //报文中数据类型
	public String len();  //报文中数据长度
	public String mark();  //特殊标志位
}
