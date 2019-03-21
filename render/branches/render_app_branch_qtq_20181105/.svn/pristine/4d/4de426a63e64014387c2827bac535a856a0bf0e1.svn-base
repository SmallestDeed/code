//package com.nork.common.interceptor;
//
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nork.common.properties.AppProperties;
//import com.nork.common.util.ForMatJSONStr;
//import com.nork.common.util.Utils;
//
////import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class ControllerMethodInterceptor implements MethodInterceptor {
//	//private static Logger logger = Logger.getLogger(ControllerMethodInterceptor.class);
//	private final Logger logger = LoggerFactory.getLogger(TimeInteceptor.class);
//	private final static ObjectMapper jsonMapper = new ObjectMapper();
//
//	@Override
//	public Object invoke(MethodInvocation invocation) throws Throwable {
//		logger.warn("Before: interceptor name: {}", invocation.getMethod().getName());
//		
//		logger.warn("Arguments: {}",invocation.getArguments());
//
//		Object result = invocation.proceed();
//
//		//logger.warn("After: result: {}",  "true".equals(Utils.getValue("json_fomate", "false").trim())?ForMatJSONStr.format(jsonMapper.writeValueAsString(result)):jsonMapper.writeValueAsString(result));
////		logger.warn("After: result: {}",  ForMatJSONStr.format(jsonMapper.writeValueAsString(result)));
//	    //jsonMapper.writeValueAsString(result));
//
//		return result;
//	}
//
//}
