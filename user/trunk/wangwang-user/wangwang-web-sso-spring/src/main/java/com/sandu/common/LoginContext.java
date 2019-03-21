package com.sandu.common;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sandu.authz.aop.PermissionAnnotationMethodInterceptor;
import com.sandu.cache.RedisManager;
import com.sandu.gson.GsonUtil;
import com.sandu.jwt.Jwt;
import com.sandu.jwt.TokenState;

import net.minidev.json.JSONObject;


public class LoginContext {
	
	private static Logger logger = LoggerFactory.getLogger(LoginContext.class);
	private static LoginContext loginContext;
	private static RedisManager redisManager;
	private static final Integer NO_TOKEN = 1; //1:请求没有token (提示:请登录)
    private static final Integer EXPIRED_TOKEN = 2; //2:请求有token,但是已过期 (提示:请登录)
    private static final Integer NO_USER_INFO_FROM_CACHE = 3; //3:请求有token,没有过期,但是redis没有. (提示:用户在其他地方登录)
    private static final Integer NORMAL = 4; //4:正常
    private static final Integer INVALID_TOKEN = 5; //无效的token
    /*@Autowired
    public void setRedisSsoManager(RedisManager redisSsoManager) {
    	LoginContext.redisManager = redisSsoManager;
    }
     */
	
    public static RedisManager getRedisManager() {
    	return redisManager;
    }
    
    public static void setRedisManager(RedisManager redisManager) {
		LoginContext.redisManager = redisManager;
	}

	public void init() {
    	loginContext = this;
    	loginContext.redisManager = this.redisManager;
	}
	
	public static boolean removeUserCache(String key) {
		Long l = redisManager.del(key);
		if(l!=null && l.longValue()>0) {
			return true;
		}
		return false;
	}
    
    public static Map getTokenData() {
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
        String token = request.getHeader("Authorization");
        if(StringUtils.isBlank(token)) {
        	return null;
        }
        Map<String, Object> dataMap = Jwt.resolveToken(token);
        if(!validatToken(dataMap)) {
        	return null;
        }
        Map tokenData = (Map)dataMap.get("data");
        return tokenData;
    }
    
    public static <T> T getLoginUser(Class<T> objClass) {
    	//获取token
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
        String token = request.getHeader("Authorization");
        if(StringUtils.isBlank(token)) {
        	logger.error("token为空");
        	return null;
        }
        Map<String, Object> dataMap = Jwt.resolveToken(token);
        if(!validatToken(dataMap)) {
        	logger.error("token异常"+token);
        	return null;
        }
        String userJson = getUserJson(dataMap);
        if(StringUtils.isBlank(userJson)) {
        	logger.error("缓存用户不存在:"+token);
        	return null;
        }
    	return GsonUtil.fromJson(userJson, objClass);
    }
    
    private static String getUserJson(Map<String, Object> dataMap) {
    	//从token中获取系统标识和appKey
        JSONObject dataObj = (JSONObject)dataMap.get("data");
        String signflat =  (String) dataObj.get("signflat");
        String appkey =  (String) dataObj.get("appKey");
        if(StringUtils.isBlank(signflat)){
        	logger.error("系统标识不存在");
        	return null;
        }
        if(StringUtils.isBlank(appkey)){
        	logger.error("appKey不存在");
        	return null;
        }
        
        //确认用户是否已登录
        String userJson = redisManager.get(signflat + appkey);
        if(StringUtils.isBlank(userJson)){
        	logger.error("登录超时");
        	return null;
        }
        return userJson;
	}

	public static Integer getLoginStatus(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if(StringUtils.isBlank(token)) {
            logger.error("token为空");
            return NO_TOKEN;
        }

        Map<String, Object> dataMap = Jwt.resolveToken(token);
        TokenState state = TokenState.getTokenState((String) dataMap.get("state"));
        if (Objects.equals("EXPIRED",state.toString())){
            logger.error("token过期");
            return EXPIRED_TOKEN;
        }else if(Objects.equals("INVALID",state.toString())){
            logger.error("无效的token");
            return INVALID_TOKEN;
        }

        String userJson = getUserJson(dataMap);
        if(StringUtils.isBlank(userJson)) {
            logger.error("缓存用户不存在:"+token);
            return NO_USER_INFO_FROM_CACHE;
        }
        return NORMAL;
    }
    
    private static boolean validatToken(Map<String, Object> dataMap) {
    	TokenState state = TokenState.getTokenState((String) dataMap.get("state"));
        switch (state) {
        	case VALID:
        		return true;
            case EXPIRED:
            	logger.error("token已过期");
            	return false;
            case INVALID:
            	logger.error("无效的token");
            	return false;
        }
        return true;
	}
}
