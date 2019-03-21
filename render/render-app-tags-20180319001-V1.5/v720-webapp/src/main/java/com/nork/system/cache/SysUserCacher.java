package com.nork.system.cache;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.UserCacher;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;

/***
 * 系统用户缓存层
 * 
 * @author qiu.jun
 * @date 2016-05-16
 */
public class SysUserCacher {

	private static Logger logger = LoggerFactory.getLogger(SysUserCacher.class);
			
	
	public static void cacheTheLoginUserInfo(LoginUser user, int exSeconds) {
		UserCacher userCacher = CacheManager.getInstance().getUserCacher();
		if(Utils.checkOneLoginEnable()) {
			String loginFlagkey = SystemCommonConstant.LOGIN_FROM_PC + user.getId() ;
			LoginUser loginUser = (LoginUser) userCacher.getObject(loginFlagkey);
			if(loginUser != null && loginUser.getUserKey() != null) {
				//检测用户之前是否登录，移除登录用户缓存
				userCacher.remove(loginFlagkey);
				userCacher.remove(SystemCommonConstant.LOGIN_FROM_PC + loginUser.getUserKey());
//				userCacher.remove(getUserKeyByUserkey(loginUser.getUserKey()));
			}
 			userCacher.setObject(loginFlagkey, user, exSeconds);
		}
//		userCacher.setObject(getUserKeyByUserkey(user.getUserKey()), user, exSeconds);
		userCacher.setObject(SystemCommonConstant.LOGIN_FROM_PC + user.getUserKey(), user, exSeconds);
	}

	public static LoginUser getCacheLoginUserByUserkey(String userKey) {
		LoginUser user = (LoginUser) CacheManager.getInstance().getCacher().getObject(getUserKeyByUserkey(userKey));
		return user;
	}
  	
	public static String getUserKeyByUserkey(String userKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userKey", userKey);
		String loginSign = Utils.getValue("loginSign", "loginSign").trim();
		if (StringUtils.equals("loginSign", loginSign))
			logger.info("请在app.properties中添加loginSign配置");
		map.put("loginSign", loginSign);
		String key = KeyGenerator.getKeyWithMap(ModuleType.LoginUser, map);
		return key;
	}
	
	public static void removeCacheUserByUserkey(String userKey){
		CacheManager.getInstance().getCacher().removeObject(getUserKeyByUserkey(userKey));
	}
}
