package com.sandu.authz.aop;
import java.lang.annotation.Annotation;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sandu.authz.AuthorizationException;
import com.sandu.authz.UserInfo;
import com.sandu.authz.annotation.FieldsFilter;
import com.sandu.cache.RedisManager;
import com.sandu.jwt.Jwt;

public class FieldsFilterAnnotationHandler extends BaseAnnotationHandler{
	private Logger logger = LoggerFactory.getLogger(FieldsFilterAnnotationHandler.class);
	public FieldsFilterAnnotationHandler(RedisManager redisManager) {
		super(redisManager);	
	}
    public Object doFilter(Annotation a,Object retObj) throws AuthorizationException {
    	FieldsFilter ffAnnotation = (FieldsFilter) a;
    	String filterCode = ffAnnotation.filterCode();
    	String filterPath = ffAnnotation.filterPath();
    	logger.info("FieldsFilter注解参数filterCode:{},filterPath:{}", filterCode,filterPath);
        //获取token
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
        String token = request.getHeader("Authorization");
        logger.info("获取用户token:{}", token);
        
        //解析token
        Map<String, Object> dataMap = Jwt.resolveToken(token);
        
        //验证token有效性验证
        validatToken(dataMap);
        
        //验证redis登录
        UserInfo u = validateLogin(dataMap);
        
        //过滤
        Object obj = u.doFiledsFilter(filterCode,filterPath,retObj);
       
        return obj;
    }
    
 

}
