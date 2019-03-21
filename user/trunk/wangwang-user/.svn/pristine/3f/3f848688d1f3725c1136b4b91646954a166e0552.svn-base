package com.sandu.service.user.impl.biz;

import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.redis.RedisService;
import com.sandu.api.system.model.ResPic;
import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.service.ResPicService;
import com.sandu.api.system.service.SysDictionaryService;
import com.sandu.api.user.input.UserLogin;
import com.sandu.api.user.model.bo.UserInfoBO;
import com.sandu.api.user.output.CityUnionUserLolginVO;
import com.sandu.api.user.service.biz.UserLoginService;
import com.sandu.common.AESUtil2;
import com.sandu.common.constant.UserConstant;
import com.sandu.common.gson.GsonUtil;
import com.sandu.common.jwt.Jwt;
import com.sandu.config.ResourceConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("cityUnionUserLoginService")
public class CityUnionUserLoginServiceImpl extends BaseUserLoginServiceImpl implements UserLoginService {

    private Logger logger = LoggerFactory.getLogger(CityUnionUserLoginServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private ResPicService resPicService;

    @Override
    public Object cacheUserInfoToRedis(UserInfoBO userInfoBO) {

        CityUnionUserLolginVO vo = new CityUnionUserLolginVO();
        vo.setId(userInfoBO.getId());
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
        vo.setMobile(userInfoBO.getMobile());
        vo.setLoginPhone(userInfoBO.getMobile());
        vo.setBalanceAmount(userInfoBO.getBalanceAmount());
        vo.setConsumAmount(userInfoBO.getConsumAmount());
        vo.setMediaType(userInfoBO.getMediaType());
        vo.setMenuTree(userInfoBO.getMenuTree());
        vo.setPermissions(userInfoBO.getPermissions());
        vo.setQueryFields(userInfoBO.getQueryFields());
        vo.setPasswordUpdateFlag(userInfoBO.getPasswordUpdateFlag());
        vo.setRoleCodeList(userInfoBO.getRoleCodeList());


        String userJson = GsonUtil.toJson(vo);
        redisService.set(UserConstant.CITY_UNION_LOGIN_PREFIX + userInfoBO.getId(), userJson, UserConstant.CITY_UNION_USER_SESSION_TIMEOUT);
        redisService.set(UserConstant.CITY_UNION_LOGIN_PREFIX + userInfoBO.getAppKey(), userJson, UserConstant.CITY_UNION_USER_SESSION_TIMEOUT);

        return vo;

    }

    @Override
    public void checkEquipment(UserInfoBO userInfoBO, UserLogin userLogin) {

    }

    @Override
    public void setServerConfigInfo(UserInfoBO userInfoBO, UserLogin userLogin) {
        logger.info("设置移动端用户其他信息");
        userInfoBO.setServerUrl(ResourceConfig.MOBILE_SERVER_URL);
        userInfoBO.setResourcesUrl(ResourceConfig.MOBILE_RESOURCES_URL);
        userInfoBO.setSiteName(ResourceConfig.MOBILE_SITE_NAME);
        userInfoBO.setServitizationList(ResourceConfig.PC_SERVITIZATION_URLS);
        
        //获取用户头像
        this.getUserPic(userInfoBO);
    }

    private void getUserPic(UserInfoBO userInfoBO) {
        //获取用户头像
        if (StringUtils.isEmpty(userInfoBO.getPicPath())) {
            Integer sexValue = userInfoBO.getSex();
            if (sexValue == null) {
                sexValue = 1;
            }
            SysDictionary dictSearch = new SysDictionary();
            dictSearch.setIsDeleted(0);
            dictSearch.setType("userDefaultPicture");
            dictSearch.setValue(sexValue);
            SysDictionary dict = sysDictionaryService.getSysDictionary("userDefaultPicture", sexValue);
            if (dict != null && dict.getPicId() != null) {
                if (dict != null && dict.getPicId() != null) {
                    ResPic userPic = resPicService.get(dict.getPicId());
                    if (userPic != null && userPic.getPicPath() != null) {
                        userInfoBO.setUserPic(userPic.getPicPath());
                    }
                }
            }
        }
    }

    @Override
    public void createUserJwtToken(UserInfoBO userInfoBO) {
        String appKey = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> payload = new HashMap<String, Object>();
        Date date = new Date();
        payload.put("signflat", UserConstant.CITY_UNION_LOGIN_PREFIX);
        payload.put("uid", userInfoBO.getId());//用户ID
        payload.put("appKey", appKey);//用户ID
        payload.put("ukey",appKey);
        payload.put("utype", userInfoBO.getUserType());
        payload.put("iat", date.getTime());//生成时间
        payload.put("uname", userInfoBO.getNickName());
        payload.put("ext", date.getTime() + UserConstant.CITY_UNION_JWT_TOKEN_TIMEOUT * 1000);
        payload.put("mediaType", userInfoBO.getMediaType());
        payload.put("mtype", userInfoBO.getMediaType());
        payload.put("sessionTimeout", UserConstant.CITY_UNION_USER_SESSION_TIMEOUT);
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
}
