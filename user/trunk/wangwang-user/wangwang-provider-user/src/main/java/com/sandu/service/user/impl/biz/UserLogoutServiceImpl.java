package com.sandu.service.user.impl.biz;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.redis.RedisService;
import com.sandu.api.user.service.biz.UserLogoutService;
import com.sandu.common.jwt.Jwt;
import com.sandu.common.jwt.TokenState;

import net.minidev.json.JSONObject;

@Service("userLogoutService")
public class UserLogoutServiceImpl  implements UserLogoutService {

    private Logger logger = LoggerFactory.getLogger(UserLogoutServiceImpl.class);

    @Autowired
    private RedisService redisService;

	@Override
	public void logout(String token) {
		//解析token
		Map<String, Object> tokenInfoMap = Jwt.validToken(token);
		//如果token有效
		if(validatToken(tokenInfoMap)) {
			//获到系统标识,用户id,用户随机串
			 JSONObject dataObj = (JSONObject)tokenInfoMap.get("data");
		     String signflat =  (String) dataObj.get("signflat");
		     String appkey =  (String) dataObj.get("appKey");
		     String uid =  dataObj.get("uid").toString();
			//删除redis相关存储
		     redisService.del(signflat+appkey);
		     redisService.del(signflat+uid);
		}
		
		
	}
	
	 private boolean validatToken(Map<String, Object> dataMap) {
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
