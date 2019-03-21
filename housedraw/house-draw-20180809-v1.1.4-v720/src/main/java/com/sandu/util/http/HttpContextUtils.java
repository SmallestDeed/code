package com.sandu.util.http;

import javax.servlet.http.HttpServletRequest;

import com.sandu.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * <p>
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 * <p>
 * 2018年3月20日
 */

@Slf4j
public class HttpContextUtils {

    /**
     * 获取当前请求的HttpServletRequest
     *
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前请求的参数
     *
     * @return
     */
    public static String getRequestParams() {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return Utils.VOID_VALUE;
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap == null || parameterMap.isEmpty()) {
            return Utils.VOID_VALUE;
        }

        StringBuilder buf = new StringBuilder();
        for (String key : parameterMap.keySet()) {
            buf.append(", ").append(key).append("=").append(Arrays.toString(parameterMap.get(key)));
        }

        return buf.toString().replaceFirst(", ", "");
    }

    public static String getIpAddress() {
        String clientIP = getHttpServletRequest().getParameter("Defaults-Real-IP");
        return Objects.toString(clientIP, "");
    }

    public static String getRequestURI() {
        return getHttpServletRequest().getRequestURI();
    }
}
