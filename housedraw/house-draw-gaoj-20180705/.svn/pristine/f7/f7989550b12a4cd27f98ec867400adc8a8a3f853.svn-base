package com.sandu.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

public class MD5 {

    static final String DEFAULTS_CHARSET = "UTF-8";

    public static String encrypt(String data) throws UnsupportedEncodingException {
        return encrypt(data, DEFAULTS_CHARSET);
    }

    public static String encrypt(String data, String charset) throws UnsupportedEncodingException {
        if (!StringUtils.isNotBlank(data)) {
            throw new NullPointerException();
        }

        StringBuilder buf = new StringBuilder();
        DigestUtils.appendMd5DigestAsHex(data.getBytes(charset), buf);
        return buf.toString();
    }
}
