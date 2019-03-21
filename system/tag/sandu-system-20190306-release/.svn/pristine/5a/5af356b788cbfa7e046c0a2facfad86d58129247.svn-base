package com.sandu.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/12/7 18:35
 * @since 1.8
 */
public class Dates {

    public static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";

    public static String dateForString(Date date, String pattern) {
        return new SimpleDateFormat(StringUtils.isEmpty(pattern) ? DEFAULT_DATE_PATTERN : pattern).format(date);
    }

    public static String dateForString(Date date) {
        return dateForString(date, null);
    }

    public static Long diff(Date val, Date now) {
        return (val != null) ? val.getTime() - (now == null ? new Date() : now).getTime() : null;
    }
}
