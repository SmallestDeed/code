package com.sandu.authz.aop;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sandu.authz.AuthorizationException;
import com.sandu.authz.UserInfo;
import com.sandu.cache.RedisManager;
import com.sandu.gson.GsonUtil;
import com.sandu.jwt.TokenState;

import net.minidev.json.JSONObject;

public class BaseAnnotationHandler {
	private Logger logger = LoggerFactory.getLogger(BaseAnnotationHandler.class);
	private RedisManager redisManager;
	public BaseAnnotationHandler(RedisManager redisManager) {
		this.redisManager = redisManager;
	}
    
	public BaseAnnotationHandler(){
		
	}
    protected UserInfo validateLogin(Map<String, Object> dataMap) {
    	//从token中获取系统标识和appKey
        JSONObject dataObj = (JSONObject)dataMap.get("data");
        String signflat =  (String) dataObj.get("signflat");
        String appkey =  (String) dataObj.get("appKey");
        logger.info("userRedisKey:{}", signflat+appkey);
        if(StringUtils.isBlank(signflat)){
            throw new AuthorizationException("系统标识不存在");
        }
        if(StringUtils.isBlank(appkey)){
        	throw new AuthorizationException("appKey不存在");
        }
        
        //确认用户是否已登录
        String jsoUser = redisManager.get(signflat + appkey);
        if(StringUtils.isBlank(jsoUser)){
        	throw new AuthorizationException("登录超时");
        }
        return GsonUtil.fromJson(jsoUser, UserInfo.class);
	}
	
    protected void validatToken(Map<String, Object> dataMap) {
    	TokenState state = TokenState.getTokenState((String) dataMap.get("state"));
        switch (state) {
            case VALID:
                break;
            case EXPIRED:
            	logger.error("token已过期");
                dataMap.put("success", false);
                throw new AuthorizationException("登录超时,请重新登录!");
            case INVALID:
            	logger.error("无效的token");
                // token过期或者无效，则输出错误信息返回
            	throw new AuthorizationException("非法访问!");
        }
	}
	
}
