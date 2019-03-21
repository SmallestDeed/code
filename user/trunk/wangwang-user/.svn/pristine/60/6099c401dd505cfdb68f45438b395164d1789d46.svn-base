package com.sandu.service.user.impl.biz;

import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.redis.RedisService;
import com.sandu.api.user.input.UserLogin;
import com.sandu.api.user.model.bo.UserInfoBO;
import com.sandu.api.user.output.MobileUserLoginVO;
import com.sandu.api.user.service.biz.UserLoginService;
import com.sandu.common.AESUtil2;
import com.sandu.common.constant.UserConstant;
import com.sandu.common.gson.GsonUtil;
import com.sandu.common.jwt.Jwt;
import com.sandu.config.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("mobile2bUserLoginService")
public class Mobile2bUserLoginServiceImpl extends BaseUserLoginServiceImpl implements UserLoginService {

    private Logger logger = LoggerFactory.getLogger(Mobile2bUserLoginServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Override
    public void checkEquipment(UserInfoBO userInfoBO, UserLogin userLogin) {
        /*查询用户关联的序列号*/
       /* AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
        authorizedConfigSearch.setUserId(userInfoBO.getId());
        authorizedConfigSearch.setState(new Integer(1));
        authorizedConfigSearch.setIsDeleted(0);
        int count = authorizedConfigService.getCount(authorizedConfigSearch);
        if (count <= 0) {
           // throw new BizException(ExceptionCodes.CODE_10010011, "尚未绑定序列号或序列号已过期，请在电脑端绑定后再登录！");
        	throw new BizException(userInfoBO.getId().intValue(), "尚未绑定序列号或序列号已过期，请在电脑端绑定后再登录！");
        }
        //子账号不需要验证是否开通移动端
        if(userInfoBO.getFranchiserAccountType()!=null && userInfoBO.getFranchiserAccountType()!=2) {
        	 //用户是否开通移动端
	        if (userInfoBO.getExistsMobile() != UserConstant.HAS_EXISTS_MOBILE) {
	           // throw new BizException(ExceptionCodes.CODE_10010012, "您尚未开通移动版功能，请联系客服开通!");
	        	throw new BizException(userInfoBO.getId().intValue(), "您尚未开通移动版功能，请联系客服开通!");
	        } else {
	            Date mobileClosedDate = userInfoBO.getMobileClosedDate();
	            Date date = new Date();
	            if (mobileClosedDate.before(date)) {
	               // throw new BizException(ExceptionCodes.CODE_10010013, "您尚未开通移动版功能，请联系客服开通!");
	            	throw new BizException(userInfoBO.getId().intValue(), "您尚未开通移动版功能，请联系客服开通!");
	            }
	        }
        }*/
        
    }


    @Override
    public void createUserJwtToken(UserInfoBO userInfoBO) {
        logger.info("设置移动端用户token");
        String appKey = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> payload = new HashMap<String, Object>();
        Date date = new Date();
        payload.put("signflat", UserConstant.MOBILE_2B_LOGIN_PREFIX);
        payload.put("uid", userInfoBO.getId());//用户ID
        payload.put("appKey", appKey);//用户ID
        payload.put("ukey", appKey); //
        payload.put("utype", userInfoBO.getUserType());
        payload.put("iat", date.getTime());//生成时间
        payload.put("uname", userInfoBO.getNickName());
        payload.put("ext", date.getTime() + UserConstant.MOBILE_2B_JWT_TOKEN_TIMEOUT * 1000);
        payload.put("mediaType", userInfoBO.getMediaType());
        payload.put("mtype", userInfoBO.getMediaType());
        payload.put("sessionTimeout", UserConstant.MOBILE_2B_USER_SESSION_TIMEOUT);
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
        logger.info("设置移动端用户其他信息");
        userInfoBO.setServerUrl(ResourceConfig.MOBILE_SERVER_URL);
        userInfoBO.setResourcesUrl(ResourceConfig.MOBILE_RESOURCES_URL);
        userInfoBO.setSiteName(ResourceConfig.MOBILE_SITE_NAME);
        userInfoBO.setServitizationList(ResourceConfig.PC_SERVITIZATION_URLS);

        super.setUserPic(userInfoBO);
    }
    

    @Override
    public Object cacheUserInfoToRedis(UserInfoBO userInfoBO) {
        MobileUserLoginVO vo = new MobileUserLoginVO();
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
        vo.setMediaType(userInfoBO.getMediaType());
        vo.setMenuTree(userInfoBO.getMenuTree());
        vo.setPermissions(userInfoBO.getPermissions());
        vo.setQueryFields(userInfoBO.getQueryFields());
        vo.setPasswordUpdateFlag(userInfoBO.getPasswordUpdateFlag());
        vo.setRoleCodeList(userInfoBO.getRoleCodeList());
        vo.setUserPic(userInfoBO.getUserPic());
        vo.setCompanyName(getCompanyName(userInfoBO.getCompanyId()));
        vo.setMaturityFlag(userInfoBO.isMaturityFlag());
        vo.setServiceType(userInfoBO.getServiceType());
        vo.setTipsFlag(userInfoBO.isTipsFlag());
        vo.setRemainingDays(userInfoBO.getRemainingDays());
        vo.setLeftTime(userInfoBO.getLeftTime());
        vo.setOldServiceFlag(userInfoBO.getOldServiceFlag());
        vo.setUserName(userInfoBO.getUserName());
        vo.setServiceName(userInfoBO.getServiceName());
        vo.setIsVirtual(userInfoBO.getIsVirtual());
        vo.setCompanyId(userInfoBO.getCompanyId());
        vo.setIsExamine(userInfoBO.getIsExamine());
        vo.setPromptMessage(userInfoBO.getPromptMessage());
        vo.setSessionId(userInfoBO.getUuid());
        vo.setCompanyType(userInfoBO.getCompanyType());
        vo.setPromptUpdatePassword(userInfoBO.getPromptUpdatePassword() == null ? 0 : userInfoBO.getPromptUpdatePassword());
        String mobile2bUserJson = GsonUtil.toJson(vo);
        //判断用户是否在其他地方登录
        MobileUserLoginVO loginUser = null;
        try{
        	loginUser = redisService.getObject(UserConstant.MOBILE_2B_LOGIN_PREFIX + vo.getId(), MobileUserLoginVO.class);
        }catch(Exception ex) {
        	logger.error("缓存用户数据有问题:"+UserConstant.MOBILE_2B_LOGIN_PREFIX + vo.getId());
        }
        if (loginUser != null) {
            logger.info("清除用户之前登录信息:用户重新登录或在其他地方登录key1:{},key2:{}", UserConstant.MOBILE_2B_LOGIN_PREFIX + loginUser.getAppKey(), UserConstant.MOBILE_2B_LOGIN_PREFIX + vo.getId());
            redisService.del(UserConstant.MOBILE_2B_LOGIN_PREFIX + vo.getId());
            redisService.del(UserConstant.MOBILE_2B_LOGIN_PREFIX + loginUser.getAppKey());
        }

        logger.info("缓存移动端用户数据key1:{},key2:{}", UserConstant.MOBILE_2B_LOGIN_PREFIX + vo.getAppKey(), UserConstant.MOBILE_2B_LOGIN_PREFIX + vo.getId());
        redisService.set(UserConstant.MOBILE_2B_LOGIN_PREFIX + vo.getId(), mobile2bUserJson, UserConstant.MOBILE_2B_USER_SESSION_TIMEOUT);
        redisService.set(UserConstant.MOBILE_2B_LOGIN_PREFIX + vo.getAppKey(), mobile2bUserJson, UserConstant.MOBILE_2B_USER_SESSION_TIMEOUT);


        /** 将移动端登录时的用户信息用PC端登录标识保存一份，供unity使用**/
        String pc2bUserJson = GsonUtil.toJson(vo);
        redisService.set(UserConstant.PC_2B_LOGIN_PREFIX + vo.getId(), pc2bUserJson, UserConstant.MOBILE_2B_USER_SESSION_TIMEOUT);
        redisService.set(UserConstant.PC_2B_LOGIN_PREFIX + vo.getAppKey(), pc2bUserJson, UserConstant.MOBILE_2B_USER_SESSION_TIMEOUT);
        
        
        return vo;
    }

    private String getCompanyName(Long companyId) {
        BaseCompany baseCompany = baseCompanyService.queryById(companyId);
        if(baseCompany != null){
            return baseCompany.getCompanyName();
        }
        return null;
    }
}
