package com.sandu.common.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtil extends org.apache.commons.lang3.time.DateUtils{
	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM" };
	/**
	 * 在给定时间的基础上增加指定天数
	 * @param date
	 * @param datenum
	 * @return
	 */
	public static Date getAddDateString(Date date,int datenum) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, datenum);
		return cal.getTime();
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (date != null) {
			if (pattern != null && pattern.length > 0) {
				formatDate = DateFormatUtils.format(date, pattern[0].toString());
			} else {
				formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
			}
		}
		return formatDate;
	}
	
	/**
	 * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
	 * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 日期型字符串转化为日期 (格式:"yyyy-MM-dd HH:mm:ss")
	 */
	public static Date parseDate(String str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			return null;
		}
	}
}
