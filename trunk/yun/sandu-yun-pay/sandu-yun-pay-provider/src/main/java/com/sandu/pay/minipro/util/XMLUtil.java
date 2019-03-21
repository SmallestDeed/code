package com.sandu.pay.minipro.util;

import java.util.Map;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/4/2
 * @since : sandu_yun_1.0
 */
public class XMLUtil {
    /**
     * 微信支付所需要的map转换为xml工具方法
     * @param map
     * @return
     */
    public static String mapToXml(Map<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (String key : map.keySet()) {
            // System.out.println(key + "========" + map.get(key));

            String value = "<![CDATA[" + map.get(key) + "]]>";
            sb.append("<" + key + ">" + value + "</" + key + ">");
            // System.out.println();
        }
        sb.append("</xml>");
        return sb.toString();

    }
}
