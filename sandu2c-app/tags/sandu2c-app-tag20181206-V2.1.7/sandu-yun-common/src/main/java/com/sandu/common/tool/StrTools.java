package com.sandu.common.tool;

import java.util.Random;

/**
 * 字符串工具
 */
public class StrTools {

    /**
     * 生成随机字符串
     *
     * @param length 生成字符串的长度
     * @return
     */
    public static String generadeRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
