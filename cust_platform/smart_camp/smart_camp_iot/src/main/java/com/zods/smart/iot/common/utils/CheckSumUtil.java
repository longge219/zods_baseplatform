package com.zods.smart.iot.common.utils;
/**
 * @Description:校验位计算比较工具类
 * @create Author:jianglong
 * @create 2022-6-11
 */
public class CheckSumUtil {
	/**
	 * @Description:生成电子围栏校验码
	 * @return byte
	 */
	public static byte getElcCheckSum(byte[] checkSumBytes) {
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
	 * @Description: 判断电子围栏校验位校验
	 * @return boolean
	 */
	public static boolean isCheckElcSumValid(byte[] checkSumBytes, byte checkSum) {
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

	/**
	 * @Description:生成GUN-RFID校验码
	 * @return byte
	 */
	public static byte getGunRfidCheckSum(byte[] checkSumBytes) {
		int byteRet = 0;
		if ((checkSumBytes != null) && (checkSumBytes.length > 0)) {
			for (int i = 0; i < checkSumBytes.length; i++) {
				byteRet = byteRet + checkSumBytes[i];
			}
			//最低一个字节
			boolean[]  res = Bits.intToBits(byteRet,4);
			Bits.bitsToInt(res);
		}
		return (byte)byteRet;
	}

	public static void main(String[] args){
//		byte[] checkSumBytes  = new byte[4];
//		checkSumBytes[0] = (byte)0x00;
//		checkSumBytes[1] = (byte)0xA7;
//		checkSumBytes[2] = (byte)0x00;
//		checkSumBytes[3] = (byte)0x00;
//		System.out.print(UnsignedNumber.getUnsignedByte(getGunRfidCheckSum(checkSumBytes)));

	}
}
