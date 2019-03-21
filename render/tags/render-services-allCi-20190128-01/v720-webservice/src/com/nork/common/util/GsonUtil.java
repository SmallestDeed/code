package com.nork.common.util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

//import com.google.gson.JsonParser;
public class GsonUtil {
    private static Gson gson = new GsonBuilder().create();
    
    public static String bean2Json(Object obj){
        return gson.toJson(obj);
    }
    
    public static <T> T json2Bean(String jsonStr,Class<T> objClass){
        return gson.fromJson(jsonStr, objClass);
    }

    /**
     * json转集合
     * @param jsonStr
     * @param typeOfT
     * @param <T>
     * @return
     */
    public static <T> T json2Bean(String jsonStr, Type typeOfT){
        return gson.fromJson(jsonStr, typeOfT);
    }

/*    public static String jsonFormatter(String uglyJsonStr){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJsonStr);
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }*/
}
