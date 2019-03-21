package com.sandu.common.util;

/**
 * 获取头信息处理类
 *
 * @author xiaoxc
 * @data 2019/1/21 0021.
 */
public class DomainUtil {

    public static String getAppIdByReferer(String customerRefer) {
        String appId = null;

        if (null != customerRefer && !"".equals(customerRefer) && (
                customerRefer.startsWith("http://servicewechat.com/")
                        || customerRefer.startsWith("https://servicewechat.com/")
        )) {
            //去掉前缀
            customerRefer = customerRefer.substring(customerRefer.indexOf("//servicewechat.com/") + 20, customerRefer.length());
            //去掉后缀
            appId = customerRefer.substring(0, customerRefer.indexOf("/"));

            if (!"".equals(appId) && 0 > appId.indexOf("\\.")) {
                return appId;
            }
        }

        return appId;
    }
}
