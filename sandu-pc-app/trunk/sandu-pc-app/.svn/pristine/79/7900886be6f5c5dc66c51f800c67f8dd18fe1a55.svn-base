package com.sandu.common.util;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonUtil {

    private static Gson gson = null;

    static{
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateAdapter());
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gson = gsonBuilder.create();
    }

    /**
     * 对象转json
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        return gson.toJson(obj);
    }

    /**
     * json转对象
     * @param jsonStr
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String jsonStr, Class<T> obj){
        return gson.fromJson(jsonStr, obj);
    }

    /**
     * json转集合
     * @param jsonStr
     * @param typeOfT
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String jsonStr, Type typeOfT){
        return gson.fromJson(jsonStr, typeOfT);
    }

}

/**
 * JSON对象日期适配器
 */
class DateAdapter implements JsonDeserializer<Date> {

    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return df.parse(json.getAsString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

