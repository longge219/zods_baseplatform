package com.zods.smart.iot.common.utils;
/**
 * @Description:数字型转换成对应16进制字符串,16进制字符串转换成数字型
 * @create Author:jiangl
 * @create Date:2015-12-21
 * @version V1.0
 */
public class NumberToHexString {
	/** 
     * @功能: byte类型转换成对应的16进制字符串
     * @参数: byte num
     * @结果: String
     */ 
	public static String getByteHexString(byte num){
		StringBuffer sb=new StringBuffer();
		String hexstr = Integer.toHexString(num);
		if(hexstr.length()<2){
			sb.append("0");
			sb.append(hexstr);
		}
		if(hexstr.length()>2){
			sb.append(hexstr.substring(hexstr.length()-2, hexstr.length()));	
		}
		if(hexstr.length()==2){
			sb.append(hexstr);
		}
		return sb.toString();
	 }
	/** 
     * @功能: short类型转换成对应的16进制字符串
     * @参数: short num
     * @结果: String
     */
	public static  String getShortHexString(short num){
		StringBuffer sb=new StringBuffer();
		String hexstr = Integer.toHexString(num);
		if(hexstr.length()<4){
			  for(int i=0;i<4-hexstr.length();i++){
				   sb.append("0"); 
			   }
			  sb.append(hexstr);
			 }
		if(hexstr.length()>4){
			sb.append(hexstr.substring(hexstr.length()-4, hexstr.length()));
		}
		if(hexstr.length()==4){
			sb.append(hexstr);
		}
		 return sb.toString();
	 }
	/** 
     * @功能: int类型转换成对应的16进制字符串
     * @参数: int num
     * @结果: String
     */
	public static  String getIntegerHexString(int num){
		StringBuffer sb=new StringBuffer();
		String hexstr = Integer.toHexString(num);
		if(hexstr.length()<8){
		  for(int i=0;i<8-hexstr.length();i++){
			   sb.append("0"); 
		   }
		  sb.append(hexstr);
		 }
		if(hexstr.length()>8){
			sb.append(hexstr.substring(hexstr.length()-8, hexstr.length()));
		}
		if(hexstr.length()==8){
			sb.append(hexstr);
		}
		return sb.toString();
	}
	/** 
     * @功能: long类型转换成对应的16进制字符串
     * @参数: long num
     * @结果: String
     */
	public static  String getLongHexString(long num){
		StringBuffer sb=new StringBuffer();
		String hexstr = Long.toHexString(num);
		if(hexstr.length()<8){
		  for(int i=0;i<8-hexstr.length();i++){
			   sb.append("0"); 
		   }
		  sb.append(hexstr);
		 }
		if(hexstr.length()>8){
			sb.append(hexstr.substring(hexstr.length()-8, hexstr.length()));
		}
		if(hexstr.length() ==8){
			sb.append(hexstr);
		}
		return sb.toString();
	}
   /** 
     * @功能: 16进制字符串转换成int型数字型
     * @参数: String str
     * @结果: byte
     */
	public static int getHexString(String str){
		int intParam = 0;
		try {
			intParam = Integer.valueOf(str, 16);
		} catch (Exception e) {
			return 0;
		}
		return intParam;
	}
   /** 
     * @功能: 16进制字符串转换成long型数字型
     * @参数: String str
     * @结果: byte
     */
	public static long getLongHexString(String str){
		long intParam = 0;
		try {
			intParam = Long.valueOf(str, 16);
		} catch (Exception e) {
			return (long)0;
		}
		return intParam;
	}
}
