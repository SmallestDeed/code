package com.sandu.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.log4j.Logger;

import com.sandu.common.constant.SystemConfigEnum;

/**
 * 配置文件进行加密 Created by yanghz on 2017/6/24 0024.
 */
public class AESUtil {
	private static Logger logger = Logger.getLogger(AESUtil.class);
	private static String PASSWORD_CRYPT_KEY = Utils.getValue(SystemConfigEnum.AES_RESOURCES_ENCRYPT_KEY.getKey());
	// 加密长度
	private static Integer CRYPT_KEY_LENGTH = 8;
	private static Cipher cipher = null;
	private static DESKeySpec desKeySpec = null;
	private static SecretKey secretKey = null;
	private static IvParameterSpec iv = null;
	private static AESUtil instance = new AESUtil();

	private AESUtil() {
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
			logger.error(e);
			e.printStackTrace();
		}
	}

	public static AESUtil getInstance() {
		return instance;
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
			logger.error(value, ex);
			ex.printStackTrace();
			return "";
		}
		return result;
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
