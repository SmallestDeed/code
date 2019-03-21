package com.nork.common.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
	
	public static String getParas(HttpServletRequest request){
			Enumeration emu = request.getParameterNames();
			StringBuffer condition = new StringBuffer("?");
			while (emu.hasMoreElements()) {
				String paramName = (String) emu.nextElement();
				condition.append(paramName);
				condition.append("=");
				condition.append(request.getParameter(paramName));
				condition.append("&");
			}
			return condition.toString();
	}
	
	public static void main(String[] args){
		
	}
}
