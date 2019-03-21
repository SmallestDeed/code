package com.sandu.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static String getDateStr(Date date, String dateFormatStr) {
		DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
		String str = "";
		try {
			str = dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
}
