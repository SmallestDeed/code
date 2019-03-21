package com.sandu.common.filter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class FilterWrap {

	public static String strWrapper(ServletRequest  req){
		
		HttpServletRequest request = (HttpServletRequest)req;
		String appKey = request.getParameter("appKey");
		String token = request.getParameter("token");
		String deviceId = request.getParameter("deviceId");
		String mediaType = request.getParameter("mediaType");
		Map<String,String> map = new HashMap<String,String>();
		map.put("appKey", appKey);
		map.put("token", token);
		map.put("deviceId", deviceId);
		map.put("mediaType", mediaType);
		String jsonStr = map.toString();
		return jsonStr.toString();
		
	}
	
}
