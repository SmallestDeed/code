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
import com.sandu.api.user.output.MobileUserLoginVO;
import com.sandu.api.user.service.biz.UserLoginService;
import com.sandu.common.AESUtil2;
import com.sandu.common.constant.UserConstant;
import com.sandu.common.gson.GsonUtil;
import com.sandu.common.jwt.Jwt;
import com.sandu.config.ResourceConfig;

@Service("mobile2cUserLoginService")
public class Mobile2cUserLoginServiceImpl extends BaseUserLoginServiceImpl implements UserLoginService {

    private Logger logger = LoggerFactory.getLogger(Mobile2cUserLoginServiceImpl.class);

    
    @Autowired
    private RedisService redisService;

   
    @Override
	public void checkEquipment(UserInfoBO userInfoBO, UserLogin userLogin) {		
	}


    @Override
    public void createUserJwtToken(UserInfoBO userInfoBO) {
        logger.info("设置移动2c端用户token");
        String appKey = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> payload = new HashMap<String, Object>();
        Date date = new Date();
        payload.put("signflat", UserConstant.MOBILE_2C_LOGIN_PREFIX);
        payload.put("uid", userInfoBO.getId());//用户ID
        payload.put("appKey", appKey);//用户ID
        payload.put("ukey", appKey); //
        payload.put("utype", userInfoBO.getUserType());
        payload.put("iat", date.getTime());//生成时间
        payload.put("uname", userInfoBO.getNickName());
        payload.put("ext", date.getTime() + UserConstant.MOBILE_2C_JWT_TOKEN_TIMEOUT * 1000);
        payload.put("mediaType", userInfoBO.getMediaType());
        payload.put("mtype", userInfoBO.getMediaType());
        payload.put("sessionTimeout", UserConstant.MOBILE_2C_USER_SESSION_TIMEOUT);
        String token = Jwt.createToken(payload);
        userInfoBO.setAppKey(appKey);
        userInfoBO.setToken(token);
        userInfoBO.setCryptKey(this.encryptKey(token));
    }

    private String encryptKey(String token) {
        //截取token的前八位
        if (token.length() < 8) {
            //字符长度不够8位则在后面补0
            token = token + String.format("%1$0" + (8 - token.length()) + "d", 0);
        } else {
            token = token.substring(0, 8);
        }
        return AESUtil2.encryptFile(ResourceConfig.AES_RESOURCES_ENCRYPT_KEY, token);
    }

    @Override
    public void setServerConfigInfo(UserInfoBO userInfoBO, UserLogin userLogin) {
       
    }
    
 
    @Override
    public Object cacheUserInfoToRedis(UserInfoBO userInfoBO) {
        MobileUserLoginVO vo = new MobileUserLoginVO();
        vo.setName(userInfoBO.getUserName());
        vo.setUserName(userInfoBO.getUserName());
        vo.setBusinessAdministrationId(userInfoBO.getBusinessAdministrationId());
        vo.setCryptKey(userInfoBO.getCryptKey());
        vo.setAppKey(userInfoBO.getAppKey());
        vo.setUserKey(userInfoBO.getAppKey());
        vo.setToken(userInfoBO.getToken());
        vo.setId(userInfoBO.getId());
        vo.setUserType(userInfoBO.getUserType());
        vo.setServerUrl(userInfoBO.getServerUrl());
        vo.setResourcesUrl(userInfoBO.getResourcesUrl());
        vo.setSiteName(userInfoBO.getSiteName());
        vo.setBalanceAmount(userInfoBO.getBalanceAmount());
        vo.setConsumAmount(userInfoBO.getConsumAmount());
        vo.setMediaType(userInfoBO.getMediaType());
        vo.setMenuTree(userInfoBO.getMenuTree());
        vo.setPermissions(userInfoBO.getPermissions());
        vo.setQueryFields(userInfoBO.getQueryFields());
        vo.setPasswordUpdateFlag(userInfoBO.getPasswordUpdateFlag());
        vo.setRoleCodeList(userInfoBO.getRoleCodeList());
        vo.setLeftTime(userInfoBO.getLeftTime());
        
        String mobile2cUserJson = GsonUtil.toJson(vo);
        //判断用户是否在其他地方登录
        MobileUserLoginVO loginUser = redisService.getObject(UserConstant.MOBILE_2C_LOGIN_PREFIX + vo.getId(), MobileUserLoginVO.class);
        if (loginUser != null) {
            logger.info("清除用户之前登录信息:用户重新登录或在其他地方登录key1:{},key2:{}", UserConstant.MOBILE_2C_LOGIN_PREFIX + loginUser.getAppKey(), UserConstant.MOBILE_2C_LOGIN_PREFIX + vo.getId());
            redisService.del(UserConstant.MOBILE_2C_LOGIN_PREFIX + vo.getId());
            redisService.del(UserConstant.MOBILE_2C_LOGIN_PREFIX + loginUser.getAppKey());
        }
        
        logger.info("缓存移动端2C用户数据key1:{},key2:{}", UserConstant.MOBILE_2C_LOGIN_PREFIX + vo.getAppKey(), UserConstant.MOBILE_2C_LOGIN_PREFIX + vo.getId());
        redisService.set(UserConstant.MOBILE_2C_LOGIN_PREFIX + vo.getId(), mobile2cUserJson, UserConstant.MOBILE_2C_USER_SESSION_TIMEOUT);
        redisService.set(UserConstant.MOBILE_2C_LOGIN_PREFIX + vo.getAppKey(), mobile2cUserJson, UserConstant.MOBILE_2C_USER_SESSION_TIMEOUT);

        return vo;
    }
}
