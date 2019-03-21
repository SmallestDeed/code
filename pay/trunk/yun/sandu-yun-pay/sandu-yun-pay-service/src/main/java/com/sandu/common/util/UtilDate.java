
package com.sandu.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/* *
 *类名：UtilDate
 *功能：自定义订单类
 *详细：工具类，可以用作获取系统日期、订单编号等
 *版本：1.0
 *日期：2016-06-06
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class UtilDate {
	
    /** 年月日时分秒(无下划线) yyyyMMddHHmmss */
    public static final String dtLong                  = "yyyyMMddHHmmss";
    
    /** 完整时间 yyyy-MM-dd HH:mm:ss */
    public static final String simple                  = "yyyy-MM-dd HH:mm:ss";
    
    /** 年月日(无下划线) yyyyMMdd */
    public static final String dtShort                 = "yyyyMMdd";
	
    
    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     * @return
     *      以yyyyMMddHHmmss为格式的当前系统时间
     */
	public  static String getOrderNum(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtLong);
		return df.format(date);
	}
	
	/**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public  static String getDateFormatter(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(simple);
		return df.format(date);
	}
	
	/**
	 * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
	 * @return
	 */
	public static String getDate(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtShort);
		return df.format(date);
	}
	
	/**
	 * 产生随机的三位数
	 * @return
	 */
	public static String getThree(){
		Random rad=new Random();
		return rad.nextInt(1000)+"";
	}
	
	/**
	 * 把日期转为字符串  
	 * @return String
	 */
    public static String ConverToString(Date date)  
    {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        return df.format(date);  
    }  
	/**
	 * 把字符串转为日期  
	 * @return Date
	 */
    public static Date ConverToDate(String strDate) throws Exception  
    {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        return df.parse(strDate);  
    }  
	/**
	 * 获取当前系统时间:年月日时分秒 
	 */
    public static String getStringDate() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}

	/**
	 * 时间格式化
	 * @param date
	 * @return
	 */
	public static String getStringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		String dateString = formatter.format(date);
		return dateString;
	}
	/**
	 * 判断两个时间差并返回天数
	 *
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public static int getDayNumber(Date startDate, Date endDate) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fromDate = ConverToString(startDate);
    	String toDate = ConverToString(endDate);
		long from = 0;
		long to = 0;
		try {
			from = simpleFormat.parse(fromDate).getTime();
			to = simpleFormat.parse(toDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int days = (int) ((to - from)/(1000 * 60 * 60 * 24));
		return days;
	}

    /**
     * 某个时间若干月后
     *
     * @param date
     * @param monthNumber 月数
     * @return
     */
    public static String getDateStrByMouth(Date date, int monthNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, monthNumber);
        return getStringDate(calendar.getTime());
    }

	public static String getDateStrByMouth(Date date, int monthNumber, int remainDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, monthNumber);
		Date d = Utils.getDateAfter(calendar.getTime(), remainDay);
		return getStringDate(d);
	}

    /**
     * 某个时间若干年后
     *
     * @param date
     * @param yearNumber 年数
     * @return
     */
    public static String getDateStrByYear(Date date, int yearNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, yearNumber);
        return getStringDate(calendar.getTime());
    }

    public static String getDateStrByYear(Date date, int yearNumber, int remainDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, yearNumber);
        Date d = Utils.getDateAfter(calendar.getTime(), remainDay);
        return getStringDate(d);
    }

    public static void main(String[] args) {
		Calendar calendar = new GregorianCalendar();
		Date expiryTime = new Date();
		calendar.setTime(expiryTime);
		calendar.add(calendar.DATE, 1);// 把日期往后增加年份.整数往后推,负数往前移动
		System.out.println(getDayNumber(new Date(),calendar.getTime()));
	    System.out.println("当前时间5个月后是：" + getDateStrByMouth(new Date(), 5,2));
        System.out.println("当前时间3年后是：" + getDateStrByYear(new Date(), 3, 2));
    }

    /**
     * 把Date转换为yyyy-MM-dd
     *
     * @param date
     * @return
     */
	public static String ConverToStringYMD(Date date)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
}
