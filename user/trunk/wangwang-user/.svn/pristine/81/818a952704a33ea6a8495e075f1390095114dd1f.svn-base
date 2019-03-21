package com.sandu.service.user.impl.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.redis.RedisService;
import com.sandu.api.user.input.UserLogin;
import com.sandu.api.user.input.UserQuery;
import com.sandu.api.user.model.bo.UserInfoBO;
import com.sandu.api.user.output.MiniProgramUserLoginVO;
import com.sandu.api.user.service.biz.UserLoginService;
import com.sandu.common.AESUtil2;
import com.sandu.common.constant.UserConstant;
import com.sandu.common.exception.BizException;
import com.sandu.common.exception.ExceptionCodes;
import com.sandu.common.gson.GsonUtil;
import com.sandu.common.jwt.Jwt;
import com.sandu.config.ResourceConfig;
import com.sandu.pay.order.service.PayAccountService;
import com.sandu.service.user.dao.SysUserDao;


@Service("miniProgramUserLoginService")
public class MiniProgramUserLoginServiceImpl extends BaseUserLoginServiceImpl implements UserLoginService {
    private Logger logger = LoggerFactory.getLogger(MiniProgramUserLoginServiceImpl.class);
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private RedisService redisService;


    @Resource(name = "payAcctService")
    private PayAccountService payAccountService;

    public UserInfoBO checkUserAccount(UserLogin userLogin) {
        String openId = userLogin.getOpenid();
        String appId = userLogin.getAppid();
        logger.info("小程序:检查用户名账号:" + openId);
        //小程序登录
        if (StringUtils.isBlank(openId)) {
            throw new BizException(ExceptionCodes.CODE_10010031, "openId不能为空");
        }
        if (StringUtils.isBlank(appId)) {
            throw new BizException(ExceptionCodes.CODE_10010030, "appId不能为空");
        }

        List<UserInfoBO> list = null;
        //openid登录
        UserQuery userQuery = new UserQuery();
        userQuery.setIsDeleted(0);
        userQuery.setOpenId(openId);
        userQuery.setAppId(appId);
        list = sysUserDao.selectList(userQuery);
        //小程序登录成功
        if (list != null && list.size() > 0) {
            return list.get(0);
        }//绑定小程序新用户openId
        logger.error("小程序登录异常:openId:" + openId + ",appId:" + appId);
        throw new BizException("登录失败,请重新进入小程序.");
    }


    public void setUserPermissions(UserInfoBO userInfoBO, Long platformId, String platformCode) {

    }

    @Override
    public void createUserJwtToken(UserInfoBO userInfoBO) {
        // TODO Auto-generated method stub
        logger.info("设置微信小程序端用户token");
        String appKey = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> payload = new HashMap<String, Object>();
        Date date = new Date();
        payload.put("signflat", UserConstant.MINI_PROGRAM_LOGIN_PREFIX);
        payload.put("uid", userInfoBO.getId());//用户ID
        payload.put("appKey", appKey);//用户ID
        payload.put("ext", date.getTime() + UserConstant.MINI_PROGRAM_JWT_TOKEN_TIMEOUT * 1000);
        payload.put("sessionTimeout", UserConstant.MINI_PROGRAM_USER_SESSION_TIMEOUT);
        payload.put("uname", userInfoBO.getNickName());
        payload.put("utype", userInfoBO.getUserType());

	/*	payload.put("ukey", appKey); //
        payload.put("iat", date.getTime());//生成时间
        payload.put("mediaType", userInfoBO.getMediaType());
        payload.put("mtype", userInfoBO.getMediaType());*/

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
    public Object cacheUserInfoToRedis(UserInfoBO userInfoBO) {
        MiniProgramUserLoginVO vo = new MiniProgramUserLoginVO();
        vo.setName(userInfoBO.getNickName());
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
        vo.setMiniProgramCompanyId(userInfoBO.getMiniProgramCompanyId());
        vo.setIsLoginBefore(userInfoBO.getIsLoginBefore());
        vo.setSessionId(userInfoBO.getUuid());
        vo.setHeadPic(userInfoBO.getHeadPic());
        String userJson = GsonUtil.toJson(vo);

        redisService.set(UserConstant.MINI_PROGRAM_LOGIN_PREFIX + vo.getAppKey(), userJson, UserConstant.MINI_PROGRAM_USER_SESSION_TIMEOUT);
        redisService.set(UserConstant.MINI_PROGRAM_LOGIN_PREFIX + vo.getId(), userJson, UserConstant.MINI_PROGRAM_USER_SESSION_TIMEOUT);

        return vo;
    }

    @Override
    public void checkEquipment(UserInfoBO userInfoBO, UserLogin userLogin) {
        // TODO Auto-generated method stub

    }


    @Override
    public void setServerConfigInfo(UserInfoBO userInfoBO, UserLogin userLogin) {
        // TODO Auto-generated method stub

    }

  

}
