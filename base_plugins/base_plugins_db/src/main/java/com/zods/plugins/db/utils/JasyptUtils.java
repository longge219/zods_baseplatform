package com.zods.plugins.db.utils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class JasyptUtils {
    public JasyptUtils() {
    }


    public static String encrypt(String str, String secret) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword(secret);
        return standardPBEStringEncryptor.encrypt(str);
    }

    public static String decrypt(String str, String secret) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword(secret);
        return standardPBEStringEncryptor.decrypt(str);
    }
}
