package com.sandu.service.user.impl.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.sandu.common.exception.BizException;
import com.sandu.common.exception.ExceptionCodes;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.redis.RedisService;
import com.sandu.api.user.input.UserLogin;
import com.sandu.api.user.model.SysRole;
import com.sandu.api.user.model.bo.CompanyInfoBO;
import com.sandu.api.user.model.bo.UserInfoBO;
import com.sandu.api.user.output.PcUserLoginVO;
import com.sandu.api.user.service.biz.UserLoginService;
import com.sandu.common.AESUtil2;
import com.sandu.common.constant.UserConstant;
import com.sandu.common.gson.GsonUtil;
import com.sandu.common.jwt.Jwt;
import com.sandu.config.ResourceConfig;

@Service("pcHouseDrawUserLoginService")
public class PcHouseDrawUserLoginServiceImpl extends BaseUserLoginServiceImpl implements UserLoginService{

	private Logger logger = LoggerFactory.getLogger(PcHouseDrawUserLoginServiceImpl.class);
	
	@Autowired
	private RedisService redisService;
	 
	@Override
	public void createUserJwtToken(UserInfoBO userInfoBO) {
		logger.info("pc端设置用户token");
		String userKey = UUID.randomUUID().toString();
		Map<String , Object> payload=new HashMap<String, Object>();
		Date date=new Date();
		payload.put("signflat",UserConstant.PC_HOURSE_DRAW_LOGIN_PREFIX);
		payload.put("uid", userInfoBO.getId());
		payload.put("uname", userInfoBO.getNickName());
		payload.put("mtype", userInfoBO.getMediaType());
		payload.put("uphone", userInfoBO.getMobile());
		payload.put("appKey", userKey);
		payload.put("ukey", userKey);
		payload.put("utype", userInfoBO.getUserType());
		payload.put("iat", date.getTime());
		payload.put("ext",date.getTime()+UserConstant.PC_HOURSE_DRAW_JWT_TOKEN_TIMEOUT*1000);//jwtToken过期时间
		payload.put("sessionTimeout", UserConstant.PC_HOURSE_DRAW_USER_SESSION_TIMEOUT);
		String token=Jwt.createToken(payload);
		userInfoBO.setUserKey(userKey);
		userInfoBO.setToken(token);	
		userInfoBO.setCryptKey(this.encryptKey(token));

	}

	private String encryptKey(String token) {
		//截取token的前八位
		if(token.length() < 8){
			//字符长度不够8位则在后面补0
			token = token+String.format("%1$0"+(8-token.length())+"d",0);
		}else{
			token = token.substring(0,8);
		}
		return AESUtil2.encryptFile(ResourceConfig.AES_RESOURCES_ENCRYPT_KEY, token);
	}

	
	@Override
	public Object cacheUserInfoToRedis(UserInfoBO userInfoBO) {
		PcUserLoginVO vo =  new PcUserLoginVO();
		vo.setId(userInfoBO.getId());
		vo.setUserType(userInfoBO.getUserType());
		vo.setLoginId(userInfoBO.getSysCode());
		vo.setLoginName(userInfoBO.getNickName());
		vo.setLoginPhone(userInfoBO.getMobile()==null?"":userInfoBO.getMobile());
		vo.setLoginEmail(userInfoBO.getEmail());
		vo.setName(userInfoBO.getUserName());
		vo.setAppKey(userInfoBO.getAppKey());
		vo.setToken(userInfoBO.getToken());
		vo.setDeviceId(userInfoBO.getMediaType()==null?"":userInfoBO.getMediaType().toString());
		vo.setMsgId(userInfoBO.getMsgId());
		vo.setGroupId(userInfoBO.getGroupId());
		vo.setSex(userInfoBO.getSex());
		vo.setBrandIds(userInfoBO.getBrandIds());
		vo.setMediaType(userInfoBO.getMediaType()==null?"3":userInfoBO.getMediaType().toString());
		vo.setUserKey(userInfoBO.getUserKey());
		vo.setRoleCodeList(userInfoBO.getRoleCodeList());
		vo.setMaturityFlag(userInfoBO.isMaturityFlag());
		vo.setServiceType(userInfoBO.getServiceType());
		vo.setTipsFlag(userInfoBO.isTipsFlag());
		vo.setRemainingDays(userInfoBO.getRemainingDays());
		vo.setLeftTime(userInfoBO.getLeftTime());
		vo.setOldServiceFlag(userInfoBO.getOldServiceFlag());
		
		//设置角色
		if(userInfoBO.getRoleList()!=null && userInfoBO.getRoleList().size()>0) {
			Set<String> s = new HashSet<String>();
			for(SysRole role : userInfoBO.getRoleList()) {
				s.add(role.getCode());
			}
			vo.setRoles(s);
		}
		
		vo.setPermissions(userInfoBO.getPermissions());
		vo.setQueryFields(userInfoBO.getQueryFields());
		
		String userInfoJson = GsonUtil.toJson(vo);
		logger.info("缓存pc端户型绘制用户数据:{}",UserConstant.PC_HOURSE_DRAW_LOGIN_PREFIX+vo.getUserKey());
		redisService.set(UserConstant.PC_HOURSE_DRAW_LOGIN_PREFIX+vo.getUserKey(), userInfoJson,UserConstant.PC_2B_USER_SESSION_TIMEOUT);
		redisService.set(UserConstant.PC_HOURSE_DRAW_LOGIN_PREFIX+vo.getId(), userInfoJson,UserConstant.PC_2B_USER_SESSION_TIMEOUT);
		return userInfoBO;
	}
	
	public List<CompanyInfoBO> checkCompanyUserInfo(UserInfoBO userInfoBO,BasePlatform platform) {
		return null;
	}

	@Override
	public void checkEquipment(UserInfoBO userInfoBO, UserLogin userLogin) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setServerConfigInfo(UserInfoBO userInfoBO, UserLogin userLogin) {
		// TODO Auto-generated method stub
		userInfoBO.setResourcesUrl(ResourceConfig.PC_DRAW_RESOURCES_URL);
		userInfoBO.setServerUrl(ResourceConfig.PC_DRAW_SERVER_URL);
		userInfoBO.setResourcesUrlStandby(ResourceConfig.PC_DRAW_HOUSEDRAW_RESOURCES_URL);
	}

	@Override
	public void checkPcHouseDrawVersion(UserLogin userLogin) {
	    if(userLogin == null || StringUtils.isEmpty(userLogin.getVersion())){
           throw  new BizException(ExceptionCodes.CODE_10010041,"当前版本过低，请登录三度官网下载最新版本" +
				   "www.sanduspace.com/download");
		}else{
	    	if(!"0.0.0".equals(userLogin.getVersion())){
				throw  new BizException(ExceptionCodes.CODE_10010041,"当前版本过低，请登录三度官网下载最新版本" +
						"www.sanduspace.com/download");
			}
		}
	}
}
