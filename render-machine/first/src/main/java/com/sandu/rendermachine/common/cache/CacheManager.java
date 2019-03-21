package com.sandu.rendermachine.common.cache;

/***
 * 缓存管理类
 * @author qiu.jun
 * @date 2016-05-09
 */
public class CacheManager {
	private static CacheManager cacheManager = null;
	private static Cacher cacher = null;

	public synchronized static CacheManager getInstance() {
		if (cacheManager == null) {
			cacheManager = new CacheManager();
		}
		return cacheManager;
	}
	
	public Cacher getCacher() {
		return cacher;
	}

}
