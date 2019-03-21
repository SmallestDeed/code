package com.sandu.service.servicepurchase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	
    public final static String format_yyyy_MM_dd = "yyyy-MM-dd";
    public static long millionSecondsOfDay = 86400000;
    /**
     * 取生效结束时间
     * @param year
     * @param day
     * @return
     */
    public static Date getEffectiveEnd(int year,int day) {
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.YEAR, year);
    	cal.add(Calendar.DAY_OF_WEEK, day);
    	return cal.getTime();
    }
    /**
     * 获取两个日期相差的毫秒数
     * @param date1
     * @param date2
     * @return
     */
    public static int getDay(Date date1, Date date2) {
        if (date1 == null || date2 == null)
            return 0;
        date1 = getDate(date1);
        date2 = getDate(date2);
        return (int) ((date2.getTime() - date1.getTime()) / millionSecondsOfDay);
    }


    public static long getMills(Date date1, Date date2) {
        if (date1 == null || date2 == null)
            return 0;
        date1 = getDate(date1);
        date2 = getDate(date2);
        return date2.getTime() - date1.getTime();
    }
    
    public static Date getDate(Date date) {
        if (date == null)
            return null;
        return getDate(DateUtil.dateToString(date));
    }
    
    public static String dateToString(Date date) {
        if (date == null)
            return "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    
    public static Date getDate(String str) {
        if (str == null || str.equals(""))
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 判断传入的日期是否在指定的日期之间
     * @param date
     * @param start
     * @param end
     * @return
     */
    public static boolean between(Date date, Date start, Date end) {        
        return getDay(start, date) >= 0 && getDay(end, date) <= 0+1;
    }
    
    public static void main(String[] args) {
		System.out.println(getDate(new Date()));
	}
}
