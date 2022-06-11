package com.zods.smart.iot.electronic.utils;
/**
 * @Description:将数字型数据转换成无符号数字型
 * @create Author:jianglong
 * @create Date:2022-06-11
 * @version V1.0
 */
public class UnsignedNumber {
	//将data字节型数据转换为0~255 (0xFF)。
	public static int getUnsignedByte (byte data){
		return data&0x0FF;
		}
	//将data字节型数据转换为0~65535 (0xFFFF)。
	public static int getUnsignedShort (short data){
		return data&0x0FFFF;
		}
	//将int数据转换为0~4294967295
    public static long getUnsignedInt (int data){     
		return data&0x0FFFFFFFFl;
	  }
 }
