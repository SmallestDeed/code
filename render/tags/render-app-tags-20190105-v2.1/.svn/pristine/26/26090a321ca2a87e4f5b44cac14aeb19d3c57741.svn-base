package com.nork.common.util.comparator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 处理日期
 * @author huangsongbo
 *
 */
public class DateUtil {

	/**得到指定日期的周一
	 * @throws ParseException */
	public static Date getMondayByDate(Date time) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		//return cal.getTime();
		return clearTheMinutes(cal.getTime());
	}
	
	/**得到指定日期的周日
	 * @throws ParseException */
	public static Date getSundayByDate(Date time) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, 6);
		//return cal.getTime();
		return clearTheMinutes(cal.getTime());
	}
	
	/**
	 * 清楚时分秒
	 * @param date 指定时间
	 * @return
	 * @throws ParseException 
	 */
	private static Date clearTheMinutes(Date date) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
		String str=sdf2.format(date);
		str+=" 00-00-00";
		Date returnDate =  sdf.parse(str) ;
		return returnDate;
	}
	
	/*得到下一天的日期*/
	public static Date nextDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}
	
	public static void main(String[] args) throws ParseException {
		//////System.out.println("hehe\nhaha");
		Date date=new Date();
		Date date2=getMondayByDate(date);
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//////System.out.println(simpleDateFormat.format(date2));
		date2=getSundayByDate(date);
		//System.out.println(clearTheMinutes(date));
		//////System.out.println(simpleDateFormat.format(date2));
	}
}
