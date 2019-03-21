package com.sandu.rendermachine.common.cache;

import java.util.List;

/***
 * 缓存接口对象
 * @author qiu.jun
 * @date 2016-05-09
 */
public interface Cacher {
	
	/***
	 * 根据Key获取字符串
	 * 
	 * @param key
	 * @return
	 */
	String get(String key);

	/***
	 * 根据Key获取对象
	 * 
	 * @param key
	 * @return
	 */
	Object getObject(String key);
	
	byte[] getBytes(String key);

	/***
	 * 根据Key获取List
	 * 
	 * @param clazz
	 * @param key
	 * @return
	 */
	<T> List<T> getList(Class<T> clazz, String key);

	/***
	 * 将字符加入缓存
	 * 
	 * @param key
	 * @param value
	 * @param cacheSeconds
	 *            cacheSeconds=-1:永不过期
	 *            cacheSeconds=0:采用系统默认的过期时间
	 */
	void set(String key, String value, int cacheSeconds);
	
	/***
	 * 将对象加入缓存
	 * 
	 * @param key
	 * @param value
	 * @param cacheSeconds
	 *            cacheSeconds=-1:永不过期
	 *            cacheSeconds=0:采用系统默认的过期时间
	 */
	void setObject(String key, Object value, int cacheSeconds);
    
	void setByte(String key, byte[] value, int cacheSeconds);
	
	/***
	 * 移除缓存的字符(方法过时)
	 * 
	 * @param key
	 */
	void remove(String key);

	/***
	 * 释放资源
	 */
	void release();


}
