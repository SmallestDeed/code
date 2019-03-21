package com.sandu.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestUtils {
	static Logger logger = LoggerFactory.getLogger(RequestUtils.class.getName());

	private static final Pattern COMPILE = Pattern.compile("/");

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

	public static String getDomainUrlFromReferer(HttpServletRequest request){
		String referer = request.getHeader("referer");
		return referer;
	}
	
	public static String getSubDomainFromReferer(HttpServletRequest request){
		String referer = request.getHeader("referer");
		if(referer!=null) {
			try {
				return referer.substring(referer.indexOf("//")+2,referer.indexOf("."));
			}catch(Exception ex) {
				logger.error("Error in process reffer:"+referer);
				return null;
			}
		}
		return null;
	}
	
	public static String getCompanyAppIdFromReferer(HttpServletRequest request){
		String referer = request.getHeader("referer");
		if(referer!=null) {
			try {
				return referer.substring(getCharacterPosition(referer,3)+1,getCharacterPosition(referer,4));
			}catch(Exception ex) {
				logger.error("Error in process reffer:"+referer);
				return null;
			}
		}

		return  null;
	};




	public static int getCharacterPosition(String string,int n){
		//这里是获取"/"符号的位置
		Matcher slashMatcher = COMPILE.matcher(string);
		int mIdx = 0;
		while(slashMatcher.find()) {
			mIdx++;
			//当"/"符号第n次出现的位置
			if(mIdx == n){
				break;
			}
		}
		return slashMatcher.start();
	}

	public static void main(String[] args){
		String referer = "http://aa.bb.sandu.com";
		System.out.println(referer.substring(referer.indexOf("//")+2,referer.indexOf(".sandu")));
	}

}
