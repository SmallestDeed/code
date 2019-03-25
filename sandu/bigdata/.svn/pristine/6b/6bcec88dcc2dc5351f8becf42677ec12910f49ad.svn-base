package com.sandu.custom.interceptor;
import java.net.URLDecoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.sandu.custom.constant.EventLogConstants;
import com.sandu.custom.util.DateUtils;
import com.sandu.custom.util.IPSeekerExt;
import com.sandu.custom.util.IPSeekerExt.RegionInfo;


public class ETLInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory
		      .getLogger(ETLInterceptor.class);
	private static IPSeekerExt ipSeekerExt = new IPSeekerExt();
    
	public void initialize() {
	}

	
	/**
	 * Modifies events in-place.
	 */
	public Event intercept(Event event) {
		Map<String, String> logInfoMap = null; //存放日志每个字段及内容
		//处理时间
		byte[] line = event.getBody();
		if(line!=null) {
			String lineStr = new String(event.getBody(), Charsets.UTF_8);
			String[] fields = lineStr.split(EventLogConstants.LOG_SEPARTIOR);
			if(fields.length==4) {
				String ip = fields[0];
				String serverTimeStr = fields[1];
				String hostname = fields[2];
				String reqUrl = fields[3];
				int index =reqUrl.indexOf("?");
				if(index>-1) {
					logInfoMap = new ConcurrentHashMap<String, String>(); 
					String requestBody = reqUrl.substring(index + 1); 
					//获取ip地址及其对应的省市区
                    analyticIp(ip,logInfoMap);
                    
                    //获取nginx服务器时间
                    if(!handleServerTime(serverTimeStr,logInfoMap)) {
                    	return null;
                    }
                    
                    //获取主机名
                    logInfoMap.put(EventLogConstants.LOG_COLUMN_NAME_HOST_NAME,hostname);
                    
                    //获取请求参数
					if(!handleRequestBody(requestBody, logInfoMap)) {
						return null;
					}
					//构建数据清洗完后的事件
					event = buildRetEvent(event,logInfoMap);
					
				}else {
					return null;
				}
			}			
		}
		
		return event;
	}
	
	

	private Event buildRetEvent(Event event,Map<String, String> logInfoMap) {
		StringBuffer retSb = new StringBuffer();
		if(!concat(retSb,logInfoMap,EventLogConstants.LOG_COLUMN_NAME_UID))	return null;
		if(!concat(retSb,logInfoMap,EventLogConstants.LOG_COLUMN_NAME_SERVER_TIME))	return null;
		if(!concat(retSb,logInfoMap,EventLogConstants.LOG_COLUMN_NAME_CLIENT_TIME))	return null;
		if(!concat(retSb,logInfoMap,EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME))	return null;
		if(!concat(retSb,logInfoMap,EventLogConstants.LOG_COLUMN_NAME_EVENT_PROPERTY))	return null;
		if(!concat(retSb,logInfoMap,EventLogConstants.LOG_COLUMN_NAME_APPID))	return null;
		if(!concat(retSb,logInfoMap,EventLogConstants.LOG_COLUMN_NAME_SDK))	return null;
		if(!concat(retSb,logInfoMap,EventLogConstants.LOG_COLUMN_NAME_SDK_VERSION))	return null;
		if(!concat(retSb,logInfoMap,EventLogConstants.LOG_COLUMN_NAME_HOST_NAME))	return null;
		if(!concat(retSb,logInfoMap,EventLogConstants.LOG_COLUMN_NAME_IP))	return null;
		if(!concat(retSb,logInfoMap,EventLogConstants.LOG_COLUMN_NAME_COUNTRY))	return null;
		if(!concat(retSb,logInfoMap,EventLogConstants.LOG_COLUMN_NAME_PROVINCE))	return null;
		if(!concat(retSb,logInfoMap,EventLogConstants.LOG_COLUMN_NAME_CITY))	return null;
		
//System.out.println(retSb.substring(0, retSb.length()-1));
		event.setBody(retSb.substring(0, retSb.length()-1).getBytes(Charsets.UTF_8));
		return event;
	}
	
	private boolean concat(StringBuffer retSb,Map<String, String> logInfoMap,String columnName) {
		String fieldValue = logInfoMap.get(columnName);
		if(StringUtils.isNotBlank(fieldValue)) {
			retSb.append(fieldValue).append(EventLogConstants.LOG_COLUMN_SEPARTIOR);
			return true;
		}else {
			return false;
		}
	}

	private boolean handleServerTime(String serverTime, Map logInfoMap) {
		Date date =  DateUtils.parseNginxServerTime2Date(serverTime);
		if(date != null) {
			logInfoMap.put(EventLogConstants.LOG_COLUMN_NAME_SERVER_TIME, FastDateFormat.getInstance(DateUtils.DATE_TIME_FORMAT).format(date));
			return true;
		}
		return false;
	}
	
	public void test() {
		Map<String, String> logInfoMap = null; //存放日志每个字段及内容
   
		//String lineStr = "192.168.2.111^A1550717013.315^Abigdatadev1^A/log.gif?uid=10001&ct=1550717013315&en=pv&ep={\"goodsid\":1001,\"goodsname\":\"台灯\"}&appid=appid001&sdk=wx&ver=1.0.0";
		  String lineStr = "192.168.2.111^A1551345628.862^Abigdatadev1^A/log.gif?en=pv&ep=%7B%22curpage%22%3A%22index%22%7D&uid=u0001&ct=1551345628115&aid=a0001&sdk=js&ver=1.0.0";
		if(lineStr!=null) {
			String[] fields = lineStr.split(EventLogConstants.LOG_SEPARTIOR);
			if(fields.length==4) {
				String ip = fields[0];
				String serverTimeStr = fields[1];
				String hostname = fields[2];
				String reqUrl = fields[3];
				int index =reqUrl.indexOf("?");
				if(index>-1) {
					logInfoMap = new ConcurrentHashMap<String, String>(); 
					String requestBody = reqUrl.substring(index + 1); 
					//获取ip地址及其对应的省市区
                    analyticIp(ip,logInfoMap);
                    
                    //获取nginx服务器时间并且设置header的 timestamp
                    if(!handleServerTime(serverTimeStr,logInfoMap)) {
                    }
                    
                    //获取主机名
                    logInfoMap.put(EventLogConstants.LOG_COLUMN_NAME_HOST_NAME,hostname);
                    
                    //获取请求参数
					if(!handleRequestBody(requestBody, logInfoMap)) {
						 
					}
					
					buildRetEvent(null,logInfoMap);
					
				} 
			}			
		} 
	}
	
	public static void main(String[] args) {
		 
	//	new ETLInterceptor().test();
	}

	private void analyticIp(String ip, Map logInfoMap) {

		RegionInfo info = ipSeekerExt.analyticIp(ip);
        if (info != null) {
        	logInfoMap.put(EventLogConstants.LOG_COLUMN_NAME_COUNTRY, info.getCountry());
        	logInfoMap.put(EventLogConstants.LOG_COLUMN_NAME_PROVINCE, info.getProvince());
        	logInfoMap.put(EventLogConstants.LOG_COLUMN_NAME_CITY, info.getCity());
        }
		logInfoMap.put(EventLogConstants.LOG_COLUMN_NAME_IP, ip);
		
	}

	private boolean handleRequestBody(String requestBody, Map logInfoMap) {
		// 处理请求参数
		if (StringUtils.isNotBlank(requestBody)) {
			String[] requestParams = requestBody.split("&");
			for (String param : requestParams) {
				if (StringUtils.isNotBlank(param)) {
					int idx = param.indexOf("=");
					if (idx < 0) {
						logger.warn("没法进行解析参数:" + param + "， 请求参数为:" + requestBody);
						return false;
					}

					String key = null, value = null;
					try {
						key = param.substring(0, idx);
						value = URLDecoder.decode(param.substring(idx + 1), "utf-8");
						//客户端时间转化
						if(EventLogConstants.LOG_COLUMN_NAME_CLIENT_TIME.equals(key)) {
							value = FastDateFormat.getInstance(DateUtils.DATE_TIME_FORMAT).format(Long.valueOf(value));
						}
						//事件属性解析
						else if(EventLogConstants.LOG_COLUMN_NAME_EVENT_PROPERTY.equals(key)) {
							LinkedHashMap<String, String> jsonMap = JSON.parseObject(value, new TypeReference<LinkedHashMap<String, String>>() {});
							StringBuffer tempStr = new StringBuffer();
							for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
								tempStr.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
					        }
							value = tempStr.substring(0,tempStr.length()-1);
						}
						logInfoMap.put(key, value);
					} catch (Exception e) {
						logger.warn("解析出现异常:"+requestBody, e);
						return false;
					}
				}
			}
			return true;
		}else {
			return false;
		}
		
	}

	/**
	 * Delegates to {@link #intercept(Event)} in a loop.
	 *
	 * @param events
	 * @return
	 */
	public List<Event> intercept(List<Event> events) {
		List<Event> out = Lists.newArrayList();
		for (Event event : events) {
			Event outEvent = intercept(event);
			if (outEvent != null) {
				out.add(outEvent);
			}
		}
		return out;
	}

	public void close() {
	}

	/**
	 */
	public static class Builder implements Interceptor.Builder {

	
		public Interceptor build() {
			return new ETLInterceptor();
		}

		public void configure(Context context) {
			
		}

	}



}