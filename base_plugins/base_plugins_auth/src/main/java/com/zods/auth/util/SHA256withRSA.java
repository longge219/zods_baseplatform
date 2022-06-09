package com.zods.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SHA256withRSA {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /**
     * RSA 位数 如果采用2048 上面最大加密和最大解密则须填写:  245 256
     */
    private static final int INITIALIZE_LENGTH = 1024;

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;


    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 使用RSA生成一对钥匙
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(INITIALIZE_LENGTH);
        //生成返回带有公钥和私钥的对象
        KeyPair generateKeyPair = keyPairGenerator.generateKeyPair();
        return generateKeyPair;
    }

    /**
     * 生成私钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @return 
     */
    public static PrivateKey getPrivateKey(KeyPair key) {
        PrivateKey generatePrivate = null;
        try {
            PrivateKey private1 = key.getPrivate();
            byte[] encoded = private1.getEncoded();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            generatePrivate = factory.generatePrivate(keySpec);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return generatePrivate;
    }

    /**
     * 获取公钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @return 
     */
    public static PublicKey getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = null;
        try {
            PublicKey public1 = keyPair.getPublic();
            byte[] encoded = public1.getEncoded();
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            publicKey = factory.generatePublic(keySpec);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return publicKey;
    }

    /**
     * Signature的用法
     * 数字签名
     *
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException 
     * @throws SignatureException 
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] signMethod(String str, PrivateKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        //初始化 MD5withRSA
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        //使用私钥
        signature.initSign(key);
        //需要签名或校验的数据
        signature.update(str.getBytes());
        //进行数字签名
        return signature.sign();
    }

    /**
     * Signature的用法
     * 数字校验
     *
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException 
     * @throws SignatureException 
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static boolean verifyMethod(String str, byte[] sign, PublicKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(key);
        signature.update(str.getBytes());
        return signature.verify(sign);
    }

    /**
     * 根据秘钥解析token
     *
     * @param keyValue
     * @param accessToken
     * @return
     */
    public static Claims parseToken(String keyValue, String accessToken) {
        // 将秘钥格式成可解析形式
        String[] values = keyValue.split("\n");
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            String value = values[i].replaceAll("\r", "").replace("\n", "");
            if (i == 0) {
                key.append(value + "\n");
            } else if (i == values.length - 1) {
                key.append("\n" + value);
            } else {
                key.append(value);
            }
        }
        // 解析token
        return Jwts.parser().setSigningKey(new SecretKeySpec(key.toString().getBytes(), SIGNATURE_ALGORITHM)).parseClaimsJws(accessToken).getBody();
    }

}
