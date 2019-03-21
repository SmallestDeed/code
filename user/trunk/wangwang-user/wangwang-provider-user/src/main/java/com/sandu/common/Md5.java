package com.sandu.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author WangHaiLin
 * @date 2018/6/5  20:11
 */
public class Md5  {
    /**
     * md5加密(copy)
     * @author huangsongbo
     * @param plainText
     * @return
     */
    private String md5(String plainText) {
        String str="";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
//			//System.out.println("result: " + buf.toString());// 32位的加密
//			//System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
            str=buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 密码md5加密规则
     * @author huangsongbo
     * @param password 明文密码
     * @return 加密后的字符串
     */
    public String getMd5Password(String password){
        return md5(md5("WeB"+password));

    }


}
