package com.nork.base.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang3.StringUtils;

import com.nork.base.service.JsonDataService;
import com.nork.common.util.Utils;




public class JsonDataServiceImpl<T> implements JsonDataService<T>{

	public static final String[] defaultExcludes =
			 new String[]{"msgId","deviceId","ids","start","start","limit","order","orderNum","orders"};
	@Override
	public String getBeanToJsonData(Object bean) {
		try {
			if(null !=bean){
				JsonConfig jsonconfig=new JsonConfig();
				jsonconfig.setIgnoreDefaultExcludes(false);
				
				registerJsonValueProcessor(jsonconfig);
				
				jsonconfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
				
				//String [] filters=getFileters();
				
				//jsonconfig.setExcludes(filters); 
				//jsonconfig.setExcludes(new String[]{"msgId","deviceId","ids",""});  
				//jsonconfig.setExcludes(new String[]{"deviceId","preArriveTime","preLeaveTime","orderTime"});   
				JSON json=JSONSerializer.toJSON(bean,jsonconfig);
				return json.toString();
			}else{
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}


	@Override
	public String getBeanToJsonData(Object bean,final String[] excludeProperties){
		try {
			if(null !=bean){
				JsonConfig jsonconfig=new JsonConfig();
				jsonconfig.setIgnoreDefaultExcludes(false);
				
				registerJsonValueProcessor(jsonconfig);
				jsonconfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
				
				if(null != excludeProperties && excludeProperties.length>0){
					jsonconfig.setExcludes(excludeProperties); 
				}
				
				
				
				JSON json=JSONSerializer.toJSON(bean,jsonconfig);
				return json.toString();
			}else{
				return "";
			}
		} catch (Exception e) {

			e.printStackTrace();
			return "";
		}
		
	}
	
	private void registerJsonValueProcessor(JsonConfig jsonconfig){
		jsonconfig.registerJsonValueProcessor(java.util.Date.class,new JsonValueProcessor() { 
            private final String format = Utils.DATE_TIME;
            public Object processObjectValue(String key, Object value, 
                    JsonConfig arg2) { 
                if (value == null) 
                    return ""; 
                if (value instanceof Date) { 
                    String str = new SimpleDateFormat(format).format((Date) value); 
                    return str; 
                } 
                return value.toString(); 
            } 
            public Object processArrayValue(Object value, JsonConfig arg1) { 
                return null; 
            }
		});
	}
	
	private void registerJsonValueProcessor_Date(JsonConfig jsonconfig){
		jsonconfig.registerJsonValueProcessor(java.util.Date.class,new JsonValueProcessor() { 
            private final String format = Utils.DATE;
            public Object processObjectValue(String key, Object value, 
                    JsonConfig arg2) { 
                if (value == null) 
                    return ""; 
                if (value instanceof Date) { 
                    String str = new SimpleDateFormat(format).format((Date) value); 
                    return str; 
                } 
                return value.toString(); 
            } 
            public Object processArrayValue(Object value, JsonConfig arg1) { 
                return null; 
            }
		});
	}
	
	
	@Override
	public String getListToJsonData(List<?> dataList) {
		if (dataList != null && dataList.size()>0) {
			return getBeanToJsonData(dataList);
		}
		return "";
	}
	
	@Override
	public String getMapToJsonData(Map<String, ?> map) {
		if (map != null && !map.isEmpty()) {
			return getBeanToJsonData(map);
		}
		return "";
	}


	@Override
	public T getJsonToBean(String jsonData,Class clzz) {
		if(null !=jsonData && null !=clzz){
			
			//JsonConfig jsonconfig=new JsonConfig();
			//jsonconfig.setIgnoreDefaultExcludes(false);
			
			//registerJsonValueProcessor_Date(jsonconfig);
			//JSONObject jsonObject =JSONObject.fromObject(jsonData,jsonconfig);
		     
			JSONObject jsonObject =JSONObject.fromObject(jsonData);	
			return (T)JSONObject.toBean(jsonObject, clzz);
		} else{
			return null;
		}
		
	}
	
	@Override
	public List<T> getJsonToBeanList(String jsonData,Class clzz) {
		if(StringUtils.isNotBlank(jsonData) && null !=clzz){
			//JsonConfig jsonconfig=new JsonConfig();
			//jsonconfig.setIgnoreDefaultExcludes(false);
			//registerJsonValueProcessor_Date(jsonconfig);
			//JSONArray array = JSONArray.fromObject(jsonData,jsonconfig);
			JSONArray array = JSONArray.fromObject(jsonData);
			return (List<T>)JSONArray.toList(array, clzz);
		} else{
			return null;
		}
		
	}
		
	}
