package com.nork.system.cache;

import java.util.HashMap;
import java.util.Map;

import com.sandu.common.LoginContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;

/***
 * 系统用户缓存层
 *
 * @author qiu.jun
 * @date 2016-05-16
 */
public class SysUserCacher {

	private static Logger logger = LoggerFactory.getLogger(SysUserCacher.class);

	public static LoginUser getCacheLoginUserByUserkey(String userKey) {
//		LoginUser user = (LoginUser) CacheManager.getInstance().getCacher().getObject(getUserKeyByUserkey(userKey));
		LoginUser user = LoginContext.getLoginUser(LoginUser.class);
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

}
