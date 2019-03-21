package com.sandu.service.user.impl.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.redis.RedisService;
import com.sandu.api.user.input.UserLogin;
import com.sandu.api.user.model.bo.CompanyInfoBO;
import com.sandu.api.user.model.bo.UserInfoBO;
import com.sandu.api.user.output.WebsiteUserLoginVO;
import com.sandu.api.user.service.biz.UserLoginService;
import com.sandu.common.constant.UserConstant;
import com.sandu.common.gson.GsonUtil;
import com.sandu.common.jwt.Jwt;

@Service("websiteUserLoginService")
public class WebsiteUserLoginServiceImpl extends BaseUserLoginServiceImpl implements UserLoginService {

    private Logger logger = LoggerFactory.getLogger(WebsiteUserLoginServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Override
    public void checkEquipment(UserInfoBO userInfoBO, UserLogin userLogin) {

    }


    @Override
    public void setServerConfigInfo(UserInfoBO userInfoBO, UserLogin userLogin) {

    }

    @Override
    public void createUserJwtToken(UserInfoBO userInfoBO) {
    	String mediaType = "2";
    	if(userInfoBO.getMediaType()!=null) {
    		mediaType = userInfoBO.getMediaType().toString();
		}
        String appKey = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> payload = new HashMap<String, Object>();
        Date date = new Date();
        payload.put("signflat",UserConstant.BRAND_2C_LOGIN_PREFIX);
        payload.put("uid", userInfoBO.getId());//用户ID
        payload.put("appKey", appKey);//用户ID
        payload.put("utype", userInfoBO.getUserType());
        payload.put("iat", date.getTime());//生成时间
        payload.put("uname", userInfoBO.getNickName());
        payload.put("mtype",mediaType);
        payload.put("ext", date.getTime() + UserConstant.BRAND_2C_JWT_TOKEN_TIMEOUT * 1000);
        payload.put("sessionTimeout", UserConstant.BRAND_2C_USER_SESSION_TIMEOUT);
        String token = Jwt.createToken(payload);
        userInfoBO.setAppKey(appKey);
        userInfoBO.setToken(token);
        userInfoBO.setMenuList(null);

    }
    

    @Override
    public Object cacheUserInfoToRedis(UserInfoBO userInfoBO) {
        WebsiteUserLoginVO vo = new WebsiteUserLoginVO();
        vo.setId(userInfoBO.getId());
        vo.setName(userInfoBO.getUserName());
        vo.setUserName(userInfoBO.getUserName());
        vo.setAppKey(userInfoBO.getAppKey());
        vo.setUserKey(userInfoBO.getAppKey());
        vo.setToken(userInfoBO.getToken());
        vo.setId(userInfoBO.getId());
        vo.setUserType(userInfoBO.getUserType());
        vo.setSiteName(userInfoBO.getSiteName());
        vo.setBalanceAmount(userInfoBO.getBalanceAmount());
        vo.setConsumAmount(userInfoBO.getConsumAmount());
        vo.setMediaType(userInfoBO.getMediaType());

        
        vo.setPermissions(userInfoBO.getPermissions());
        vo.setQueryFields(userInfoBO.getQueryFields());
        vo.setRoleCodeList(userInfoBO.getRoleCodeList());
        String userJson = GsonUtil.toJson(vo);
        redisService.set(UserConstant.BRAND_2C_LOGIN_PREFIX + userInfoBO.getId(), userJson, UserConstant.BRAND_2C_USER_SESSION_TIMEOUT);
        redisService.set(UserConstant.BRAND_2C_LOGIN_PREFIX + userInfoBO.getAppKey(), userJson, UserConstant.BRAND_2C_USER_SESSION_TIMEOUT);
        return userInfoBO;
    }

}
