package com.sandu.websocket;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 8:06 2019/1/8 0008
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2019/1/8 0008PM 8:06
 */
public class GsonUtil {
    private static Gson gson = (new GsonBuilder()).create();

    public GsonUtil() {
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String jsonStr, Class<T> objClass) {
        return gson.fromJson(jsonStr, objClass);
    }
}
