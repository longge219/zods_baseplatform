package com.zods.plugins.zods.utils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

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
