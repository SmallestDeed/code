package com.nork.common.constant.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.jwt.Jwt;
import com.nork.common.jwt.TokenState;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.sandu.common.LoginContext;
public class SystemCommonUtil {
	private static Logger logger = Logger.getLogger(SystemCommonUtil.class);
	
	public static LoginUser getLoginUserFromSession(HttpServletRequest request) {
		LoginUser loginUser =getCurrentLoginUserInfo(request);
		return loginUser;
	}
	
	//TODO: What the mean about 2 ? (2,web2),(3,pc),(4,mac),(5,ios),(6,adr),(7,ipad)
	public static String getMediaType(HttpServletRequest request) {
		LoginUser user = getLoginUserInfoByAuthData(request);
		String mediaType = user.getMediaType();
		return mediaType;
	}
	
	
//	
//	public static void newSysSave(Mapper obj, HttpServletRequest request) {
//		LoginUser user = getCurrentLoginUserInfo(request);
//		String loginName = user.getLoginName();
//	    if(obj != null){
//            if(obj.getId() == null){
//            	obj.setGmtCreate(new Date());
//            	obj.setCreator(loginName);
//            	obj.setIsDeleted(0);
//                if(obj.getSysCode()==null || "".equals(obj.getSysCode())){
//                	obj.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
//                }
//            }
//            obj.setGmtModified(new Date());
//            obj.setModifier(loginName);
//        }
//	}
	//可以通过判断来获取
	public static LoginUser getCurrentLoginUserInfo(HttpServletRequest request) {
//		return getLoginUserInfoByAuthData(request);
		if(Utils.enableRedisCache()) {
			return getLoginUserInfoFromCache(request);
		}else {
			return getLoginUserInfoByAuthData(request);
		}
	}
	
	//从Http headers 获取登录用户信息
	public static LoginUser getLoginUserInfoByAuthData(HttpServletRequest request) {
		LoginUser user = null;
		if(request.getAttribute("AuthorizationData") != null) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> map = (HashMap<String, Object>) request.getAttribute("AuthorizationData");
			user = new LoginUser();
			String uName = map.get("uname").toString();
			Long uIdValue = (Long)map.get("uid");
			Long uTypeValue = (Long)map.get("utype");
			int uId = uIdValue.intValue();
			int uType = uTypeValue.intValue();
			String appKey = map.get("appKey").toString();
			String mediaType = request.getHeader("MediaType");
			user.setAppKey(appKey);
			user.setUserType(Integer.valueOf(uType));
			user.setLoginName(uName);
			user.setId(Integer.valueOf(uId));
			user.setMediaType(mediaType == null ? SystemCommonConstant.MEDIA_TYPE_PC : mediaType);
		}
		return user;
	}
	//从Http headers 获取登录用户信息
	public static LoginUser getLoginUserByAuthData(HttpServletRequest request) {
		LoginUser loginUser= new LoginUser();
		Map map =LoginContext.getTokenData();
		if (null != map) {
			String appKey = map.get("appKey").toString();
			String mediaType = request.getHeader("MediaType");
			Long uTypeValue = (Long)map.get("utype");
			String loginName = (String) map.get("uname");
			int uType = uTypeValue.intValue();
			long id =(long) map.get("uid");
			loginUser.setId(new Long(id).intValue());
			loginUser.setLoginName(loginName);
			loginUser.setUserType(Integer.valueOf(uType));
			loginUser.setAppKey(appKey);
			loginUser.setMediaType(mediaType == null ? SystemCommonConstant.MEDIA_TYPE_PC : mediaType);
		}
		return loginUser;
	}
	
	//从缓存里获取登录用户信息
		public static LoginUser getLoginUserInfoFromCache(HttpServletRequest request) {
			LoginUser user = null;
			if (request.getAttribute("AuthorizationData") != null) {
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) request.getAttribute("AuthorizationData");
				String ukey = map.get("ukey").toString();
				String signflag = map.get("signflat").toString();
				user = (LoginUser) JedisUtils.getObject(signflag + ukey);
			}
			if(null==user){
				logger.error("getLoginUserInfoFromCache==> user is null");
			}
			return user;
			
		}
	
	public static LoginUser getValidLoginUerFromCache(String ukey) {
		LoginUser loginUserInfo = null;
//		loginUserInfo = SysUserCacher.getCacheLoginUserByUserkey(ukey);
		if (loginUserInfo != null && StringUtils.isNoneBlank(loginUserInfo.getToken())) {
			String token = loginUserInfo.getToken();
			Map<String, Object> resultMap = Jwt.validToken(token);
			TokenState state = TokenState.getTokenState((String) resultMap.get("state"));
			switch (state) {
			case VALID:
				break;
			case EXPIRED:
				loginUserInfo = null;
				break;
			case INVALID:
				loginUserInfo = null;
				break;
			}
		}
		return loginUserInfo;
	}
	
	public static LoginUser getValidLoginUerFromHttpAuthData(HttpServletRequest request) {
		LoginUser user = null;
		if(request.getAttribute("AuthorizationData") != null) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> map = (HashMap<String, Object>) request.getAttribute("AuthorizationData");
			user = new LoginUser();
			String uName = map.get("uname").toString();
			String token = map.get("token").toString();
			String mType = map.get("mtype").toString();
			Long uIdValue = (Long)map.get("uid");
			Long uTypeValue = (Long)map.get("utype");
			int uId = uIdValue.intValue();
			int uType = uTypeValue.intValue();
			String ukey = map.get("ukey").toString();
			user.setUserKey(ukey);
			user.setUserType(Integer.valueOf(uType));
			user.setLoginName(uName);
			user.setId(Integer.valueOf(uId));
			user.setToken(token);
			user.setMediaType(mType);
		}
		if (user != null && StringUtils.isNoneBlank(user.getToken())) {
			String token = user.getToken();
			Map<String, Object> resultMap = Jwt.validToken(token);
			TokenState state = TokenState.getTokenState((String) resultMap.get("state"));
			switch (state) {
			case VALID:
				break;
			case EXPIRED:
				user = null;
				break;
			case INVALID:
				user = null;
				break;
			}
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public static LoginUser checkLoginUserIsValidByToken(String token) {
		LoginUser user = null;
		if (StringUtils.isNotBlank(token)) {
			Map<String, Object> resultMap = Jwt.validToken(token);
			TokenState state = TokenState.getTokenState((String) resultMap.get("state"));
			switch (state) {
			case VALID:
				user = new LoginUser();
				HashMap<String, Object> map = (HashMap<String, Object>)resultMap.get("data");
				String uName = map.get("uname").toString();
				String mType = map.get("mtype").toString();
				Long uIdValue = (Long)map.get("uid");
				Long uTypeValue = (Long)map.get("utype");
				int uId = uIdValue.intValue();
				int uType = uTypeValue.intValue();
				String ukey = map.get("ukey").toString();
				user.setUserKey(ukey);
				user.setUserType(Integer.valueOf(uType));
				user.setLoginName(uName);
				user.setId(Integer.valueOf(uId));
				user.setToken(token);
				user.setMediaType(mType);
				break;
			case EXPIRED:
				break;
			case INVALID:
				break;
			}
		}
		return user;
	}
	

}
