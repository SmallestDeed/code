package com.nork.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.nork.common.properties.AesProperties;
/**
 * 配置文件加解密  txt
 * Created by yanghz on 2017/6/24 0024.
 */
public class AESUtil2 {
//    private static String PASSWORD_CRYPT_KEY= Utils.getValueByFileKey(AesProperties.AES, AesProperties.AES_RESOURCES_ENCRYPT_KEY_FILEKEY, "41e5c74dd46e4ddcb942dc8ce6224a2e").trim();
    private static String PASSWORD_CRYPT_KEY= "41e5c74dd46e4ddcb942dc8ce6224a2e";

    // 加密长度
    private static Integer CRYPT_KEY_LENGTH = 8;
    private Cipher cipher =null;
    private DESKeySpec desKeySpec =null;
    private SecretKey secretKey =null;
    private IvParameterSpec iv =null;

    private AESUtil2() {
        try {
            // 配置文件加密key取前八位，不足在后面补零
            if (PASSWORD_CRYPT_KEY.length() < CRYPT_KEY_LENGTH) {
                PASSWORD_CRYPT_KEY = String.format("%1$0" + (CRYPT_KEY_LENGTH - PASSWORD_CRYPT_KEY.length()) + "d", 0);
            } else {
                PASSWORD_CRYPT_KEY = PASSWORD_CRYPT_KEY.substring(0, CRYPT_KEY_LENGTH);
            }
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            desKeySpec = new DESKeySpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));
            secretKey = SecretKeyFactory.getInstance("DES").generateSecret(desKeySpec);
            iv = new IvParameterSpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));
//			encrypt("test_init");
            // v1.0.10
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        } catch (Exception e) {
//            throw new BusinessException(false, ResponseEnum.ENCRYPT_ERROR, e);
        }
    }

    public static AESUtil2 getInstance() {
        return new AESUtil2();
    }

    private byte[] encrypt(String message, String key) throws Exception {
//		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    public String encrypt(String value) {
        // PASSWORD_CRYPT_KEY = token;
        String result = "";
        try {
            // 该编码去掉，要不然app端会解密不出来
            // value = java.net.URLEncoder.encode(value, "utf-8");
            result = toHexString(encrypt(value, PASSWORD_CRYPT_KEY)).toUpperCase();
        } catch (Exception ex) {
//            throw new BusinessException(false, ResponseEnum.ENCRYPT_ERROR, ex);
        }
        return result;
    }

    private String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }
        return hexString.toString();
    }

    /**
     * 解密
     * @param message 需要解密的内容
     * @return
     * @throws Exception
     */
    public String decrypt(String message){
        try {
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            desKeySpec = new DESKeySpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));
            secretKey = SecretKeyFactory.getInstance("DES").generateSecret(desKeySpec);
            iv = new IvParameterSpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));
            byte[] bytesrc = convertHexString(message);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] retByte = cipher.doFinal(bytesrc);
            return new String(retByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt2(String message, String key) throws Exception {
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
	
    private byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }

    /**
     * 加密
     * add by yanghz
     * @param value
     * @param token
     * @return
     */
    public String encryptFile(String value, String token) {
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

    public static void main(String[] args) {
        String content = FileUploadUtils.getFileContext("C:\\Users\\Administrator\\Desktop\\Tools\\11.txt");
        System.out.println(AESUtil2.getInstance().decrypt(content));
    }
}
