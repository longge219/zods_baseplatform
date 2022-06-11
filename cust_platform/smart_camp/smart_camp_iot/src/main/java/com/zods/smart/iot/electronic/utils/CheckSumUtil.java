package com.zods.smart.iot.electronic.utils;
/**
 * @Description:校验位计算比较工具类
 * @create Author:jianglong
 * @create 2022-6-11
 */
public class CheckSumUtil {
	/**
	 * @Description: 生成GPRS报文校验
	 * @create Author:蒋龙
	 * @create 2016-4-14
	 * @return byte
	 */
	public static byte getCheckSum(byte[] checkSumBytes) {
		int byteRet = 0;
        if ((checkSumBytes != null) && (checkSumBytes.length > 0)) {
			for (int i = 0; i < checkSumBytes.length; i++) {
				byteRet = byteRet + checkSumBytes[i];
			}
			byteRet = byteRet % 256;
		}
        return (byte)byteRet;
	}

	/**
	 * @Description: 校验位校验
	 * @return boolean
	 */
	public static boolean isCheckSumValid(byte[] checkSumBytes, byte checkSum) {
		boolean isValid = false;
		int newCheckSum = 0;
		if ((checkSumBytes != null) && (checkSumBytes.length > 0)) {
			for (int i = 0; i < checkSumBytes.length; i++) {
				newCheckSum = newCheckSum + checkSumBytes[i];
			}
			newCheckSum = newCheckSum % 256;
		}
		if (newCheckSum == checkSum) {
			isValid = true;
		}
		return isValid;
	}
}
