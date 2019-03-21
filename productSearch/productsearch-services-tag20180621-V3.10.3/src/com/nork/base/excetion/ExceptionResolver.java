/**
 * 
 */
package com.nork.base.excetion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * 
 * Spring 异常处理
 * @author louxinhua
 *
 */
public class ExceptionResolver implements HandlerExceptionResolver {

	
	/**
	 * loggger类
	 */
	public final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);
	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest req,
			HttpServletResponse res, Object handler, Exception ex) {
		
		logger.info("Request path : " + req.getRequestURI() + ", error : ", ex); // louxinhua added
		
		ModelAndView view = new ModelAndView(); //"Error", "errorMsg", msg);
		
		// FIXME:这里实际上需要进行判断，是 web 页面请求还是 api json 格式请求，返回不同的出错格式。
		String errorInfo = "";
		if(ex instanceof DataAccessException){
			errorInfo = "数据操作失败";
		}else if(ex instanceof NullPointerException){
			errorInfo = "调用了未经初始化的对象或者不存在的对象";
		}else if(ex instanceof IOException){
			errorInfo = "IO异常";
		}else if(ex instanceof ClassNotFoundException){
			errorInfo = "指定的类不存在";
		}else if(ex instanceof ArithmeticException){
			errorInfo = "数据运算异常";
		}else if(ex instanceof ArrayIndexOutOfBoundsException){
			errorInfo = "数据下标越界";
		}else if(ex instanceof IllegalArgumentException){
			errorInfo = "方法的参数错误";
		}else if(ex instanceof ClassCastException){
			errorInfo = "类型强制转换错误";
		}else if(ex instanceof SecurityException){
			errorInfo = "违背安全原则异常";
		}else if(ex instanceof SQLException){
			errorInfo = "操作数据库异常";
		}else if(ex.getClass().equals(NoSuchMethodError.class)){
			errorInfo = "方法未找到异常";
		}else if(ex.getClass().equals(InternalError.class)){
			errorInfo = "Java虚拟机发生了内部错误";
		}else{
			errorInfo = "程序内部错误,操作失败";
		}
		logger.info("异常信息: "+errorInfo,ex);
		
		JSONObject json = new JSONObject();
		json.put("isError", true);
		json.put("msg", errorInfo);
		
		
		MappingJackson2JsonView view2JsonView = new MappingJackson2JsonView();
		Map<String, Object> attributes = new HashMap<>();
        attributes.put("isError", true);
        attributes.put("msg", errorInfo);
        
        view2JsonView.setAttributesMap(attributes);
        view.setView(view2JsonView);
		
        return view;
	}

}
