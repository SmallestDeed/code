package com.nork.common.cache;

import com.nork.common.metadata.CacherContainer;
import com.nork.common.util.ConfigUtil;

/***
 * 缓存管理类
 * @author qiu.jun
 * @date 2016-05-09
 */
public class CacheManager {
	private static CacheManager cacheManager = null;
	private static Cacher cacher = null;
	private static UserCacher userCacher = null;

	private CacheManager() {
		init();
	}

	private void init() {
		if (cacher == null) {
			ConfigUtil configUtil=ConfigUtil.getInstance("app");
			String cacherContainer = configUtil.getValue("cache.container");
			if (cacherContainer != null && cacherContainer.equalsIgnoreCase(CacherContainer.REDIS)) {
				cacher=new JedisCacher();
			}
		}
		if (userCacher == null) {
			ConfigUtil configUtil=ConfigUtil.getInstance("app");
			String cacherContainer = configUtil.getValue("cache.container");
			if (cacherContainer != null && cacherContainer.equalsIgnoreCase(CacherContainer.REDIS)) {
				userCacher=new JedisUserCacher();
			}
		}
	}

	public synchronized static CacheManager getInstance() {
		if (cacheManager == null) {
			cacheManager = new CacheManager();
		}
		return cacheManager;
	}
	
	public Cacher getCacher() {
		return cacher;
	}

	public UserCacher getUserCacher(){
		return userCacher;
	}
}
