
package com.sandu.pay.alipay.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
}
