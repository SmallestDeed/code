package com.sandu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static long beginTime() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		Date beginDate = currentDate.getTime();
		long beginTime = beginDate.getTime();
		return beginTime;
	}

	public static long beginTime(String stepTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		long beginTime = 0l;
		try {
			date = sdf.parse(stepTime);
			DateUtils utils = new DateUtils();
			Calendar currentDate = utils.dateToCalendar(date);
			currentDate.set(Calendar.HOUR_OF_DAY, 0);
			currentDate.set(Calendar.MINUTE, 0);
			currentDate.set(Calendar.SECOND, 0);
			Date beginDate = currentDate.getTime();
			beginTime = beginDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return beginTime;
	}

	public static Date beginDate(String stepTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		Date beginDate = null;
		try {
			date = sdf.parse(stepTime);
			DateUtils utils = new DateUtils();
			Calendar currentDate = utils.dateToCalendar(date);
			currentDate.set(Calendar.HOUR_OF_DAY, 0);
			currentDate.set(Calendar.MINUTE, 0);
			currentDate.set(Calendar.SECOND, 0);
			beginDate = currentDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return beginDate;
	}

	private Calendar dateToCalendar(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;

	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatType = "yyyy-MM-dd HH:mm:ss";
		String beginTime = "2017-03-30 19:05:42";
		Date aa = stringToDate(beginTime, formatType);
		Date beginDate = beginDate(beginTime);
		//System.out.println("aa----------------->" + aa);
		//System.out.println("beginDate---------->" + beginDate);
		if (beginDate.getTime() <= aa.getTime()) {
			//System.out.println("-----yes-----");
		} else {
			//System.out.println("-----no-----");
		}

		Integer intsr = getDateBetweenDay(new Date(),getAfterDayTime(new Date(),10));
		System.out.println(intsr);
		System.out.println(sdf.format(getAfterMonthTime(new Date(),12)));
		Integer intsr1 = getDateBetweenMonth(new Date(),getAfterMonthTime(new Date(),12));
		System.out.println(intsr1);

	}

	public static Date stringToDate(String strTime, String formatType) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}

	/**
	 * 计算 day 天后的时间
	 * @author chenqiang
	 * @param date 当前时间
	 * @param day 天
	 * @return 返回day之后的时间
	 * @Date 2018/4/23 0023 15:35
	 */
	public static Date getAfterDayTime(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * 计算 day 天前的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getBeforeDayTime(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1*day);
		return calendar.getTime();
	}

	/**
	 * 计算 month 月后的时间(月：30天)
	 *
	 * @param date
	 * @return
	 */
	public static Date getAfterMonthTime(Date date,int month) {
		return getAfterDayTime(date,month*30);
	}

	/**
	 * 计算 month 月前的时间(月：30天)
	 *
	 * @param date
	 * @return
	 */
	public static Date getBeforeMonthTime(Date date,int month) {
		return getBeforeDayTime(date,month*30);
	}

	/**
	 * 计算两个时间的天数
	 * @author chenqiang
	 * @param early
	 * @param late
	 * @return
	 * @date 2018/5/4 0004 14:11
	 */
	public static final int getDateBetweenDay(Date early, Date late) {

		Calendar calst = Calendar.getInstance();
		Calendar caled = Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		//设置时间为0时
		calst.set(Calendar.HOUR_OF_DAY, 0);
		calst.set(Calendar.MINUTE, 0);
		calst.set(Calendar.SECOND, 0);
		caled.set(Calendar.HOUR_OF_DAY, 0);
		caled.set(Calendar.MINUTE, 0);
		caled.set(Calendar.SECOND, 0);
		//得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;

		return days;
	}

	/**
	 * 计算两个时间之间的月数
	 * @author chenqiang
	 * @param date1
	 * @param date2
	 * @return
	 * @date 2018/5/4 0004 14:14
	 */
	public static int getDateBetweenMonth(Date date1, Date date2){
		int result = 0;

		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();

		start.setTime(date1);
		end.setTime(date2);

		//判断是否是同一年
//		if(end.get(Calendar.YEAR) - start.get(Calendar.YEAR) == 0){
//			result = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
//		}else{
//			result = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12 + (end.get(Calendar.MONTH) - start.get(Calendar.MONTH));
//		}

		int day = getDateBetweenDay(date1,date2);
		result = day/30;

		return result;

	}

	/**
	 * 计算某个时间点后多少年的时间
	 * @param date 某个时间
	 * @param years 某个时间后多少年
	 * @return 时间
	 */
	public static Date dateAfterYear(Date date ,Integer years){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, years);
		Date time = calendar.getTime();
		return time;
	}

	/**
	 * 计算某个时间后多少个月的时间
	 * @param date 某个时间
	 * @param months 某时间后多少月
	 * @return 时间
	 */
	public static Date dateAfterMonth(Date date ,Integer months){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
        calendar.add(Calendar.MONTH,months);
		Date time = calendar.getTime();
		return time;
	}

	/**
	 * 计算某个时间后多少天的时间
	 * @param date 某个时间
	 * @param days 后多少天
	 * @return 时间
	 */
	public static Date dateAfterDay(Date date ,Integer days){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		Date time = calendar.getTime();
		return time;
	}


	/**
	 * 计算两个时间点之间相隔xx年xx月xx天
	 * @param fromDate 前一个时间点
	 * @param toDate 后一个时间点
	 * @return xx年xx月xx天
	 */
	public static String getYearMonthDayFromDateToDate(Date fromDate,Date toDate){
		StringBuffer validTimeStr=new StringBuffer();
		Calendar  from  =  Calendar.getInstance();
		from.setTime(fromDate);
		Calendar  to  =  Calendar.getInstance();
		to.setTime(toDate);
		int fromYear = from.get(Calendar.YEAR);
		int fromMonth = from.get(Calendar.MONTH);
		int fromDay = from.get(Calendar.DAY_OF_MONTH);
		int toYear = to.get(Calendar.YEAR);
		int toMonth = to.get(Calendar.MONTH);
		int toDay = to.get(Calendar.DAY_OF_MONTH);
		int year = toYear-fromYear;
		int month = toMonth-fromMonth;
		int day = toDay-fromDay;
		if (day<0){
			month=month-1;
			if (toMonth==1||toMonth==3||toMonth==5||toMonth==7||toMonth==8||toMonth==11){//Calendar的月从0开始
				day=day+31;
			}else if(toMonth==4||toMonth==6||toMonth==9||toMonth==10||toMonth==12){
				day=day+30;
			}else{
				day=day+28;
			}
		}
		if (month<0){
			year=year-1;
			month=month+12;
		}
		if (year>0){
			validTimeStr.append(year);
			validTimeStr.append("年");
		}if (month>0){
			validTimeStr.append(month);
			validTimeStr.append("个月");
		}if (day>0){
			validTimeStr.append(day);
			validTimeStr.append("天");
		}
		return validTimeStr.toString();
	}

	/*public static void main(String[] args) {

		Date date=new Date(System.currentTimeMillis());
		//Date date1 = dateAfterYear(date, 1);
		Date date2 = dateAfterMonth(date, 7);
		Date date3 = dateAfterDay(date2, 20);
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(format.format(date3));

		String toDate = getYearMonthDayFromDateToDate(date, date3);
		System.out.println(toDate);

	}*/

}