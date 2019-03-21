package com.sandu.common.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.sf.json.JSONObject;

public class JsonObjectUtil {
	   public static String bean2Json(Object obj){
	        JSONObject jsonObject=JSONObject.fromObject(obj);
	        return jsonObject.toString();
	    }
	    
	    @SuppressWarnings("unchecked")
	    public static <T> T json2Bean(String jsonStr,Class<T> objClass){
	        return (T)JSONObject.toBean(JSONObject.fromObject(jsonStr), objClass);
	    }
	    
	    /**
	     * 
	    * @Description: String类型的json串转成JsonObject
	    * @param str
	    * @return     
	    * @return JsonObject    返回类型 
	    * @throws
	     */
	    public static JsonObject stringToJsonObject(String str){
	    	JsonParser parser = new JsonParser();
	    	JsonObject object = (JsonObject) parser.parse(str);
			return object;
	    }
}
