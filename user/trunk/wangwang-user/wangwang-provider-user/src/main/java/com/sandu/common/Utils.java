package com.sandu.common;

import java.util.Calendar;
import java.util.Date;

public class Utils {

	/**
	 * 获取当前日期对应的本月份最开始的日期
	 * eg: 2018.12.15 -> 2018.12.1
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date getMonthBeginDate(Date date) {
		if(date == null) {
			return null;
		}
		date.setDate(0);
		date.setHours(24);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}

}
