/**
 *
 */
package com.sandu.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * aes 加密解密的工具类。 代码是网上 copy 来的
 *
 * @author louxinhua
 */
public class AESUtil {


    private static final Logger logger = LogManager.getLogger(AESUtil.class);


    /**
     * 加密的 key
     */
    private static final String p_w_a_ = "#41^#$*sdhsfgdjn111[];;;;q#41^#$*sdhsfgdjn111[];;;;q#41^#$*sdhsfgdjn111[];;;;q#41^#$*";


    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static String encrypt(String content, String password) {

        try {
            password = p_w_a_; // 先写死

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());

            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return parseByte2HexStr(result); // 加密


        } catch (NoSuchAlgorithmException e) {

            logger.error("encrypt '" + content + "' fail, error : ", e);

        } catch (NoSuchPaddingException e) {

            logger.error("encrypt '" + content + "' fail, error : ", e);

        } catch (InvalidKeyException e) {

            logger.error("encrypt '" + content + "' fail, error : ", e);

        } catch (UnsupportedEncodingException e) {

            logger.error("encrypt '" + content + "' fail, error : ", e);

        } catch (IllegalBlockSizeException e) {

            logger.error("encrypt '" + content + "' fail, error : ", e);

        } catch (BadPaddingException e) {

            logger.error("encrypt '" + content + "' fail, error : ", e);

        }
        return null;
    }

    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static String decrypt(String enCodecontent, String password) {


        try {
            password = p_w_a_; // 先写死
            KeyGenerator kgen = KeyGenerator.getInstance("AES");

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());

            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] decryptFrom = parseHexStr2Byte(enCodecontent);
            byte[] result = cipher.doFinal(decryptFrom);
            return new String(result); // 加密

        } catch (NoSuchAlgorithmException e) {
            logger.error("decrypt '" + enCodecontent + "' fail, error : ", e);
        } catch (NoSuchPaddingException e) {
            logger.error("decrypt '" + enCodecontent + "' fail, error : ", e);
        } catch (InvalidKeyException e) {
            logger.error("decrypt '" + enCodecontent + "' fail, error : ", e);
        } catch (IllegalBlockSizeException e) {
            logger.error("decrypt '" + enCodecontent + "' fail, error : ", e);
        } catch (BadPaddingException e) {
            logger.error("decrypt '" + enCodecontent + "' fail, error : ", e);
        }
        return null;
    }


    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            } else {
            }
            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }


    // 测试
    public static void main(String[] args) {

        //System.out.println("need encode : a2jakdsjfklja");

        String encode = AESUtil.encrypt("a2jakdsjfklja", "");

        //System.out.println(encode);

        String deCode = AESUtil.decrypt(encode, "");

        //System.out.println(deCode);
    }
}
