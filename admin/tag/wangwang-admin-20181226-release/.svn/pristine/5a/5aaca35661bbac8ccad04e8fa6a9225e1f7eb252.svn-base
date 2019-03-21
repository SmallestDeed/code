package com.sandu.util;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BrandUtil {

    /**
     * 获取系统当前时间
     * @param localDateTime
     * @param format
     * @return
     */
    public static String getgetCurrentDateTime(LocalDateTime localDateTime,String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    /**
     * 产生指定长度的无规律数字字符串
     *
     * @param aLength
     *            生成的随机数的长度
     * @return 生成的随机字符串 throws 卡号生成异常
     */
    public static String generateRandomDigitString(int aLength) {
        SecureRandom tRandom = new SecureRandom();
        long tLong;
        String aString = "";

        tRandom.nextLong();
        tLong = Math.abs(tRandom.nextLong());
        aString = (String.valueOf(tLong)).trim();
        while (aString.length() < aLength) {
            tLong = Math.abs(tRandom.nextLong());
            aString += (String.valueOf(tLong)).trim();
        }
        aString = aString.substring(0, aLength);

        return aString;
    }
}
