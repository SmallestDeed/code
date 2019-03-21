package com.sandu.aes.util;

import com.sandu.common.properties.AesProperties;
import com.sandu.common.util.Utils;

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
    private static String PASSWORD_CRYPT_KEY = Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_KEY_FILEKEY, "41e5c74dd46e4ddcb942dc8ce6224a2e").trim();
    private static Cipher cipher = null;
    private static DESKeySpec desKeySpec = null;
    private static SecretKey secretKey = null;
    private static IvParameterSpec iv = null;


    /**
     * 解密数据
     *
     * @Description:
     * @author: yanghz
     * @Param: ${params}$
     * @Return: ${returns}$
     * @Date: ${YEAR}-${MONTH}-${DAY} ${TIME}
     */
    public static String decrypt(String message) throws Exception {
        if (PASSWORD_CRYPT_KEY.length() < 8) {
            PASSWORD_CRYPT_KEY = String.format("%1$0" + (8 - PASSWORD_CRYPT_KEY.length()) + "d", 0);
        } else {
            PASSWORD_CRYPT_KEY = PASSWORD_CRYPT_KEY.substring(0, 8);
        }
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


    /**
     * add by yanghz
     *
     * @param message 需要解密的内容
     * @param key     加密的key
     * @return
     * @throws Exception
     */
    public static String decrypt(String message, String key) throws Exception {
        PASSWORD_CRYPT_KEY = key;
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
        PASSWORD_CRYPT_KEY = token;
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
//            value = java.net.URLEncoder.encode(value, "utf-8");
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
            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }
            hexString.append(plainText);
        }
        return hexString.toString();
    }

    public static void main(String[] args) throws Exception {
        File file = new File("D:\\280036_20170621152602833_20170704150535638.txt");
        //System.out.println("密码："+PASSWORD_CRYPT_KEY.substring(0,8));
//        String sds = decrypt(miwen);
//        //System.out.println("解密后="+sds);
    }
}
