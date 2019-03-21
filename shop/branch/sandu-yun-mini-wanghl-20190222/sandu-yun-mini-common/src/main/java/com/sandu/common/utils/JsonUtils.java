package com.sandu.common.utils;

import java.util.Date;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Created by kono on 2018/5/14 0014.
 */
public class JsonUtils {
	private static Gson GSON = null;

	static {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new DateAdapter());
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		GSON = builder.create();
	}

	/**
	 * 对象转JSON
	 *
	 * @param obj
	 *            待转换对象
	 * @return
	 */
	public static String toJson(Object obj) {
		return GSON.toJson(obj);
	}

	/**
	 * JSON转对象
	 *
	 * @param jsonStr
	 *            待转换字符串
	 * @param objClass
	 *            类Class
	 * @param <T>
	 * @return
	 */
	public static <T> T fromJson(String jsonStr, Class<T> objClass) {
		return GSON.fromJson(jsonStr, objClass);
	}

	/**
	 * JSON转List对象
	 *
	 * @param jsonStr
	 *            待转换字符串
	 * @param typeOfT
	 *            类型
	 * @param <T>
	 * @return
	 */
	public static <T> T fromJson(String jsonStr, Type typeOfT) {
		T t = null;
		try {
			t = GSON.fromJson(jsonStr, typeOfT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

}

/**
 * JSON对象日期适配器
 *
 * @date 2018-04-28
 * @auth xiaoxc
 */
class DateAdapter implements JsonDeserializer<Date> {

	private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Date deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		try {
			return df.parse(arg0.getAsString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}