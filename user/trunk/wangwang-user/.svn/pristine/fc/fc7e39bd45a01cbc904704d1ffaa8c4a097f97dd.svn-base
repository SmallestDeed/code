package com.sandu.service.user.impl.biz;

import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.redis.RedisService;
import com.sandu.api.user.input.UserLogin;
import com.sandu.api.user.model.bo.UserInfoBO;
import com.sandu.api.user.output.MerchantMgrUserLoginVO;
import com.sandu.api.user.service.biz.UserLoginService;
import com.sandu.common.constant.UserConstant;
import com.sandu.common.gson.GsonUtil;
import com.sandu.common.jwt.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("operationPlatformUserLoginService")
public class OperationPlatformUserLoginServiceImpl extends BaseUserLoginServiceImpl implements UserLoginService {

    private Logger logger = LoggerFactory.getLogger(OperationPlatformUserLoginServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Override
    public void checkEquipment(UserInfoBO userInfoBO, UserLogin userLogin) {

    }
    
    /*@Override
    public List<CompanyInfoBO> checkCompanyUserInfo(UserInfoBO userInfoBO,BasePlatform platform,Long currentLoginCompanyId){
    	if(userInfoBO.getPlatformType()!=SysUser.PLATFORM_TYPE_B) {
    		 throw new BizException(ExceptionCodes.CODE_10010028, "您不是企业用户");
    	}
    	//检查是否开通平台权限
    	super.checkPlatformJurisdiction(userInfoBO.getId(),platform.getId());
    	super.validUserEffectivityTime(userInfoBO);
    	return null;
    }*/


    @Override
    public void setServerConfigInfo(UserInfoBO userInfoBO, UserLogin userLogin) {

    }

    @Override
    public void createUserJwtToken(UserInfoBO userInfoBO) {
        String appKey = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> payload = new HashMap<String, Object>();
        Date date = new Date();
        payload.put("signflat", UserConstant.OPERATION_PLATFORM_LOGIN_PREFIX);
        payload.put("uid", userInfoBO.getId());//用户ID
        payload.put("appKey", appKey);//用户ID
        payload.put("utype", userInfoBO.getUserType());
        payload.put("iat", date.getTime());//生成时间
        payload.put("uname", userInfoBO.getNickName());
        payload.put("ext", date.getTime() + UserConstant.MERCHANT_MANAGER_JWT_TOKEN_TIMEOUT * 1000);
        payload.put("sessionTimeout", UserConstant.MERCHANT_MANAGER_USER_SESSION_TIMEOUT); 
        
        String token = Jwt.createToken(payload);
        userInfoBO.setAppKey(appKey);
        userInfoBO.setToken(token);

    }
    
    
    @Override
    public Object cacheUserInfoToRedis(UserInfoBO userInfoBO) {
    	MerchantMgrUserLoginVO vo = new MerchantMgrUserLoginVO();
        vo.setId(userInfoBO.getId());
        vo.setNickName(userInfoBO.getNickName());
        vo.setMobile(userInfoBO.getMobile());
        vo.setPicPath(userInfoBO.getPicPath());
        vo.setToken(userInfoBO.getToken());
        vo.setPasswordUpdateFlag(userInfoBO.getPasswordUpdateFlag());
        vo.setMenuTree(userInfoBO.getMenuTree());
        vo.setPermissions(userInfoBO.getPermissions());
        vo.setQueryFields(userInfoBO.getQueryFields());
        vo.setRoleCodeList(userInfoBO.getRoleCodeList());
        vo.setLoginName(userInfoBO.getNickName());
        vo.setBusinessAdministrationId(checkDealerVirtual(userInfoBO.getBusinessAdministrationId(),userInfoBO.getUserType()));
        vo.setCompanyId(userInfoBO.getCompanyId());
        vo.setMaturityFlag(userInfoBO.isMaturityFlag());
        vo.setServiceType(userInfoBO.getServiceType());
        vo.setTipsFlag(userInfoBO.isTipsFlag());
        vo.setRemainingDays(userInfoBO.getRemainingDays());
        vo.setOldServiceFlag(userInfoBO.getOldServiceFlag());
        vo.setUserName(userInfoBO.getUserName());
        vo.setServiceName(userInfoBO.getServiceName());
        vo.setIsExamine(userInfoBO.getIsExamine());
        vo.setLeftTime(userInfoBO.getLeftTime());
        vo.setUserType(userInfoBO.getUserType());
        /**是否当天首次登录 add by wangHaiLin start**/
        boolean firstLoginToday = isFirstLoginToday(userInfoBO.getId(), userInfoBO.getPlatformId());
        vo.setFirstLoginToday(firstLoginToday);
        //end

        String userJson = GsonUtil.toJson(vo);
        redisService.set(UserConstant.OPERATION_PLATFORM_LOGIN_PREFIX + userInfoBO.getId(), userJson, UserConstant.MERCHANT_MANAGER_USER_SESSION_TIMEOUT);
        redisService.set(UserConstant.OPERATION_PLATFORM_LOGIN_PREFIX + userInfoBO.getAppKey(), userJson, UserConstant.MERCHANT_MANAGER_USER_SESSION_TIMEOUT);
        
        return vo;
    }

 
    private Long checkDealerVirtual(Long businessAdministrationId,Integer userType) {
        BaseCompany baseCompany = baseCompanyService.queryById(businessAdministrationId);
        if (null != baseCompany && baseCompany.getIsVirtual() == 1 && userType == 3) {
            return 0L;
        }
        return businessAdministrationId == null?0L:businessAdministrationId;
    }
}
