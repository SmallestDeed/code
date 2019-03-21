package com.sandu.im.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    private static Gson gson = new GsonBuilder().create();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }


    public static <T> T fromJson(String jsonStr, Class<T> objClass) {
        return gson.fromJson(jsonStr, objClass);
    }

}
