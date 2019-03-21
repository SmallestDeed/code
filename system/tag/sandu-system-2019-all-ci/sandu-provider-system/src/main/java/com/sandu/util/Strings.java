package com.sandu.util;

import org.apache.commons.lang3.StringUtils;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/12/11 20:24
 * @since 1.8
 */
public class Strings {

    public static final String DEFAULT_REPLACE = "*";

    public static String secrecy(String val, int idx, int len, String replace) {
        if (StringUtils.isEmpty(val)) {
            return null;
        }

        StringBuilder buf = new StringBuilder();
        buf.append(val, 0, idx - 1);

        len = (len <= -1 ? val.length() : len);
        for (int i = 0; i < len; i++) {
            buf.append(replace == null ? DEFAULT_REPLACE : replace);
        }
        buf.append(val, idx + len - 1, val.length());

        return buf.toString();
    }
}
