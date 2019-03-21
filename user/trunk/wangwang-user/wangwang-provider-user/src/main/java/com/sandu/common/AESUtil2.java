package com.sandu.common;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;

/**
 * 配置文件加解密  txt
 * Created by yanghz on 2017/6/24 0024.
 */
public class AESUtil2 {
    private static Cipher cipher = null;
    private static DESKeySpec desKeySpec = null;
    private static SecretKey secretKey = null;
    private static IvParameterSpec iv = null;

    /**
     * add by yanghz
     *
     * @param message 需要解密的内容
     * @param key     加密的key
     * @return
     * @throws Exception
     */
    public static String decrypt(String message, String key) throws Exception {
        String PASSWORD_CRYPT_KEY = key;
        try {
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            desKeySpec = new DESKeySpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));
            secretKey = SecretKeyFactory.getInstance("DES").generateSecret(desKeySpec);
            iv = new IvParameterSpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] bytesrc = convertHexString(message);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }

    private static byte[] encrypt(String message, String key) throws Exception {

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    /**
     * 加密
     * add by yanghz
     *
     * @param value
     * @param token
     * @return
     */
    public static String encryptFile(String value, String token) {
        String PASSWORD_CRYPT_KEY = token;
        try {
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            desKeySpec = new DESKeySpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));
            secretKey = SecretKeyFactory.getInstance("DES").generateSecret(desKeySpec);
            iv = new IvParameterSpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = "";
        try {
            //该编码去掉，要不然app端会解密不出来
            result = toHexString(encrypt(value, PASSWORD_CRYPT_KEY)).toUpperCase();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        return result;
    }

    private static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }

    private static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }
        return hexString.toString();
    }

}
