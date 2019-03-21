package com.nork.common.cache;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.ShardedJedis;

import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.ConfigUtil;
import com.nork.common.util.NumberUtil;

/***
 * Redis 缓存实现类
 * @author qiu.jun
 * @date 2016-05-09
 */
public class JedisCacher implements Cacher {

	private int defaultCacheSeconds = 0;

	
	public JedisCacher() {
		String seconds = ConfigUtil.getConfig("/config/redis", "redis.pool.login.cacheSeconds");
		this.defaultCacheSeconds = NumberUtil.parseInt(seconds);
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return JedisUtils.get(key);
	}

	@Override
	public Object getObject(String key) {
		// TODO Auto-generated method stub
		return JedisUtils.getObject(key);
	}

	@Override
	public <T> List<T> getList(Class<T> clazz, String key) {
		// TODO Auto-generated method stub
		return (List<T>) JedisUtils.getObject(key);
	}

	@Override
	public void set(String key, String value, int cacheSeconds) {
		// TODO Auto-generated method stub
		if (cacheSeconds == 0) {
			JedisUtils.set(key, value, defaultCacheSeconds);
		} else if (cacheSeconds == -1) {
			JedisUtils.set(key, value, 0);
		} else {
			JedisUtils.set(key, value, cacheSeconds);
		}
	}

	@Override
	public void setObject(String key, Object value, int cacheSeconds) {
		// TODO Auto-generated method stub
		if (cacheSeconds == 0) {
			JedisUtils.setObject(key, value, defaultCacheSeconds);
		} else if (cacheSeconds == -1) {
			JedisUtils.setObject(key, value, 0);
		} else {
			JedisUtils.setObject(key, value, cacheSeconds);
		}
	}

	public void remove(String key) {
		// TODO Auto-generated method stub
		JedisUtils.del(key);
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		ShardedJedis jedis = JedisUtils.getResource();
		if (jedis != null) {
			JedisUtils.returnResource(jedis);
		}
	}

	public void removeObject(String key) {
		// TODO Auto-generated method stub
		JedisUtils.delObject(key);
	}

	@Override
	public void remove(ModuleType moduleType, String key) {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(key)){
		    JedisUtils.del(key);
		}
		String keyPrefix=ConfigUtil.getConfig("/dataSource/cache", moduleType.toString());
		if(StringUtils.isNotBlank(keyPrefix)){
		    JedisUtils.removeWithKeyPrefix(keyPrefix);
		}
	}

	@Override
	public void removeObject(ModuleType moduleType, String key) {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(key)){
		     JedisUtils.delObject(key);
		}
		String keyPrefix=ConfigUtil.getConfig("/dataSource/cache", moduleType.toString());
		if(StringUtils.isNotBlank(keyPrefix)){
		    JedisUtils.removeWithKeyPrefix(keyPrefix);
		}
		//////System.out.println("remove from cacher,key:" + key);
	}

	@Override
	public byte[] getBytes(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setByte(String key, byte[] value, int cacheSeconds) {
		// TODO Auto-generated method stub
		if (cacheSeconds == 0) {
			JedisUtils.setByte(key, value, defaultCacheSeconds);
		} else if (cacheSeconds == -1) {
			JedisUtils.setByte(key, value, 0);
		} else {
			JedisUtils.setByte(key, value, cacheSeconds);
		}
	}

  
}
