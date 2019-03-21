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
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.cache.RedisManager;
import com.sandu.jwt.Jwt;

import net.minidev.json.JSONObject;

public class PermissionAnnotationHandler extends BaseAnnotationHandler{
	private Logger logger = LoggerFactory.getLogger(PermissionAnnotationHandler.class);
	public PermissionAnnotationHandler(RedisManager redisManager) {
		super(redisManager);
	}
    public void assertAuthorized(Annotation a) throws AuthorizationException {
        String[] perms = getAnnotationValue(a);
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
        
        //功能权限验证
        if(!u.isPermit(perms)) {
        	throw new AuthorizationException("无权限访问,请联系管理员!");
        }
        
        //存储用户相关信息到request
        storeInfoToRequest(request,dataMap);
    }
    
    private void storeInfoToRequest(HttpServletRequest request,Map<String, Object> dataMap) {
    	JSONObject dataObj = (JSONObject)dataMap.get("data");
        request.setAttribute("AuthorizationData", dataObj);
        request.setAttribute("tokenUserId", (Long) dataObj.get("uid"));
	}
    
    
	protected String[] getAnnotationValue(Annotation a) {
        RequiresPermissions rpAnnotation = (RequiresPermissions) a;
        return rpAnnotation.value();
    }
}
