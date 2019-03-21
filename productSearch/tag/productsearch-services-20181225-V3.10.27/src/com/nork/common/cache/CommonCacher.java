package com.nork.common.cache;

import java.util.Map;

import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.ResponseEnvelope;

/**
 * 缓存公用类
 * @author Administrator
 *
 */
public class CommonCacher {

	
	
	/**
	 * 支持塞入任何结果集
	 * @param moduleType  模块类别
	 * @param method  方法名
	 * @param paramsMap	参数Map
	 * @param responseEnvelope 结果集
	 * @return flag
	 */
 
	@SuppressWarnings("rawtypes")
	public static Boolean addAll(ModuleType moduleType,String method,Map<Object,Object>paramsMap,ResponseEnvelope responseEnvelope){
		
		ResponseEnvelope responseEnvelope_=null;
		Boolean flag=false;
		
		if(moduleType==null){
			return flag;
		}
		if(method==null||"".equals(method)){
			return flag;
		}
		if(paramsMap==null||paramsMap.size()<=0){
			return flag;
		}
		try{
			paramsMap.put("method", method);
			String key = KeyGenerator.getAllListKeyWithMap(moduleType, paramsMap);
			responseEnvelope_=   (ResponseEnvelope) CacheManager.getInstance().getCacher().getObject(key);
			if(responseEnvelope_!=null){
				 CacheManager.getInstance().getCacher().removeObject(key);
			}
			CacheManager.getInstance().getCacher().setObject(key, responseEnvelope, 0);
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
			return flag;
		}
		return flag;
	}

	
	/**
	 * 取结果集
	 * @param moduleType  模块类别
	 * @param method  方法名
	 * @return ResponseEnvelope 响应信封
	 */
	@SuppressWarnings({  "rawtypes" })
	public static ResponseEnvelope getAll(ModuleType moduleType,String method,Map<Object,Object>paramsMap){
		ResponseEnvelope responseEnvelope=null;
		
		if(moduleType==null){
			return responseEnvelope;
		}
		if(method==null||"".equals(method)){
			return responseEnvelope;
		}
		if(paramsMap==null||paramsMap.size()<=0){
			return responseEnvelope;
		}
		try{
			paramsMap.put("method", method);
			String key = KeyGenerator.getAllListKeyWithMap(moduleType, paramsMap);
			responseEnvelope=(ResponseEnvelope) CacheManager.getInstance().getCacher().getObject(key);
		}catch(Exception e){
			e.printStackTrace();
			return responseEnvelope;
		}
		return responseEnvelope;
	}
	
	
	public static  void removeAll (ModuleType moduleType,String method,Map<Object,Object>paramsMap){
		paramsMap.put("method", method);
		String key = KeyGenerator.getAllListKeyWithMap(moduleType, paramsMap);
		//CacheManager.getInstance().getCacher().removeObject(moduleType, key);
		CacheManager.getInstance().getCacher().removeObject(key);
	}


	/**
	 * 支持塞入任何结果集
	 * @param moduleType  模块类别
	 * @param method  方法名
	 * @param paramsMap	参数Map
	 * @param responseEnvelope 结果集
	 * @param cacheSeconds 缓存有效时间
	 * @return flag
	 */

	@SuppressWarnings("rawtypes")
	public static Boolean addAll(ModuleType moduleType,String method,Map<Object,Object>paramsMap,ResponseEnvelope responseEnvelope,int cacheSeconds){

		ResponseEnvelope responseEnvelope_=null;
		Boolean flag=false;

		if(moduleType==null){
			return flag;
		}
		if(method==null||"".equals(method)){
			return flag;
		}
		if(paramsMap==null||paramsMap.size()<=0){
			return flag;
		}
		try{
			paramsMap.put("method", method);
			String key = KeyGenerator.getAllListKeyWithMap(moduleType, paramsMap);
			responseEnvelope_=   (ResponseEnvelope) CacheManager.getInstance().getCacher().getObject(key);
			if(responseEnvelope_!=null){
				CacheManager.getInstance().getCacher().removeObject(key);
			}
			CacheManager.getInstance().getCacher().setObject(key, responseEnvelope, cacheSeconds);
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
			return flag;
		}
		return flag;
	}
}
