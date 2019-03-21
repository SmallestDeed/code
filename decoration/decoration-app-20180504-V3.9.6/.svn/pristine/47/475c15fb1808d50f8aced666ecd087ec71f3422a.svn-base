/**    
 * 文件名：Tools.java    
 *    
 * 版本信息：    
 * 日期：2017-7-5    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.nork.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 *     
 * 项目名称：timeSpace    
 * 类名称：Tools    
 * 类描述：    
 * 创建人：Timy.Liu   
 * 创建时间：2017-7-12 下午1:30:18    
 * 修改人：Timy.Liu    
 * 修改时间：2017-7-12 下午1:30:18    
 * 修改备注：    
 * @version     
 *
 */
public class Tools {
    public static final int CPU_NUM=Runtime.getRuntime().availableProcessors();
    public static ExecutorService fixExecutorService = Executors.newFixedThreadPool(2*CPU_NUM);
    public static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(CPU_NUM);
  /*  private static HashMap<String, String> dirMap4Crypt = new HashMap<>();

    private static HashMap<String, String> dirMap4UnCrypt = new HashMap<>();
    static{
        JSONObject jsonObject = JSONObject.fromObject(Constants.STORE_DIRS_CRYPT);
        JSONArray jsonArray = (JSONArray)   jsonObject.get("cfg");
        int length = jsonArray.size();
        for(int i=0;i<length;i++){
            JSONObject json  =  (JSONObject) jsonArray.get(i);
            String keys = json.getString("modelName");
            if(StringUtils.isEmpty(keys))
                continue;
            String[] keyArray = keys.split(",");
            int keyLength = keyArray.length;
            for(int j=0;j<keyLength;j++){
                if(StringUtils.isEmpty(keyArray[j]))
                    continue;
                dirMap4Crypt.put(keyArray[j], json.getString("uploadRoot"));
            }
        }
        
        
        jsonObject = JSONObject.fromObject(Constants.STORE_DIRS_UN_CRYPT);
        jsonArray = (JSONArray)   jsonObject.get("cfg");
        length = jsonArray.size();
        for(int i=0;i<length;i++){
            JSONObject json  =  (JSONObject) jsonArray.get(i);
            String keys = json.getString("modelName");
            if(StringUtils.isEmpty(keys))
                continue;
            String[] keyArray = keys.split(",");
            int keyLength = keyArray.length;
            for(int j=0;j<keyLength;j++){
                if(StringUtils.isEmpty(keyArray[j]))
                    continue;
                dirMap4UnCrypt.put(keyArray[j], json.getString("uploadRoot"));
            }
        }
        
    }*/

    /**
     * 
       
     * getRootPath得到文件存储的根路径：第一：先看未加密配置，找到直接返回 。第二：在看加密配置，找到直接返回。第三兼容老的配置，        
       
     * @param filePath
     * @param defaultVal
     * @return 
    
     * @return String    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    /*public static String getRootPath(String filePath,String defaultVal) {
         if(StringUtils.isEmpty(filePath))
            return defaultVal;
        
        Iterator iter = dirMap4UnCrypt.keySet().iterator();
        while(iter.hasNext()){
            String key = (String)iter.next();
            if(filePath.indexOf("/"+key+"/") >-1|| filePath.indexOf("\\"+key+"\\")>-1){
                return dirMap4UnCrypt.get(key);
            }
        }

        iter = dirMap4Crypt.keySet().iterator();
        while(iter.hasNext()){
            String key = (String)iter.next();
            if(filePath.indexOf("/"+key+"/") >-1|| filePath.indexOf("\\"+key+"\\")>-1){
                return dirMap4Crypt.get(key);
            }
        }
        return StringUtils.isEmpty(Constants.UPLOAD_ROOT)?defaultVal:Constants.UPLOAD_ROOT;
    }*/

    public static String getRootPath(String filePath,String defaultVal) {
    	if(StringUtils.isEmpty(filePath)) {
    		return defaultVal;
    	}
    	
    	String domain = Utils.getDomain(filePath, null);
    	
    	if(StringUtils.isEmpty(domain)) {
    		return defaultVal;
    	}
    	
    	return domain;
    }
    
    public static void main(String[] args) {
        /*//System.out.println(getRootPath("/AA/c_basedesign/dd",""));*/
    }
}
