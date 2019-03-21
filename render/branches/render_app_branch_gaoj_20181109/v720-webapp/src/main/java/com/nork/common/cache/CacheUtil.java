package com.nork.common.cache;

/***
 * 缓存接口对象
 * @author qiu.jun
 * @date 2016-05-09
 */
public interface CacheUtil {
	public void put(String key, String value);
	public void put(String key, Object value);
	public <T> T get(String key, Class<T> className);
	public String get(String key);
}
