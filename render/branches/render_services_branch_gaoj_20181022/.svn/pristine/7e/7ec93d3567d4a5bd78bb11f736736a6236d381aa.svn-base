package com.nork.common.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyHandlerExceptionResolver implements HandlerExceptionResolver{  
    private static Logger logger = Logger.getLogger(MyHandlerExceptionResolver.class);  
    @Override  
    public ModelAndView resolveException(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex) {  
        logger.info("Catch Exception: ",ex);//把漏网的异常信息记入日志  
        Map<String,Object> map = new HashMap<String,Object>();  
        StringPrintWriter strintPrintWriter = new StringPrintWriter();  
        ex.printStackTrace(strintPrintWriter);  
        map.put("Exception", ex);
        map.put("errorMsg", strintPrintWriter.getString());//将错误信息传递给view  
        return new ModelAndView("errors\\error",map);  
    }  

}
