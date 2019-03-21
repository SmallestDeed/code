package com.nork.common.cache;

import com.nork.common.cache.utils.JedisUserUtils;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.ConfigUtil;
import com.nork.common.util.NumberUtil;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.ShardedJedis;

import java.util.List;

/***
 * Redis 缓存实现类
 * @author qiu.jun
 * @date 2016-05-09
 */
public class JedisUserCacher implements UserCacher {

	private int defaultCacheSeconds = 0;

	public JedisUserCacher() {
		String seconds = ConfigUtil.getConfig("/config/redis", "redis.pool.login.cacheSeconds");
		this.defaultCacheSeconds = NumberUtil.parseInt(seconds);
		//////System.out.println("defaultCacheSeconds:"+defaultCacheSeconds);
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return JedisUserUtils.get(key);
	}

	@Override
	public Object getObject(String key) {
		// TODO Auto-generated method stub
		return JedisUserUtils.getObject(key);
	}

	@Override
	public <T> List<T> getList(Class<T> clazz, String key) {
		// TODO Auto-generated method stub
		return (List<T>) JedisUserUtils.getObject(key);
	}

	@Override
	public void set(String key, String value, int cacheSeconds) {
		// TODO Auto-generated method stub
		if (cacheSeconds == 0) {
			JedisUserUtils.set(key, value, defaultCacheSeconds);
		} else if (cacheSeconds == -1) {
			JedisUserUtils.set(key, value, 0);
		} else {
			JedisUserUtils.set(key, value, cacheSeconds);
		}
	}

	@Override
	public void setObject(String key, Object value, int cacheSeconds) {
		// TODO Auto-generated method stub
		if (cacheSeconds == 0) {
			JedisUserUtils.setObject(key, value, defaultCacheSeconds);
		} else if (cacheSeconds == -1) {
			JedisUserUtils.setObject(key, value, 0);
		} else {
			JedisUserUtils.setObject(key, value, cacheSeconds);
		}
	}

	public void remove(String key) {
		// TODO Auto-generated method stub
		JedisUserUtils.del(key);
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		ShardedJedis jedis = JedisUserUtils.getResource();
		if (jedis != null) {
			JedisUserUtils.returnResource(jedis);
		}
	}

	public void removeObject(String key) {
		// TODO Auto-generated method stub
		JedisUserUtils.delObject(key);
	}

	@Override
	public void remove(ModuleType moduleType, String key) {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(key)){
		    JedisUserUtils.del(key);
		}
		String keyPrefix=ConfigUtil.getConfig("/dataSource/cache", moduleType.toString());
		if(StringUtils.isNotBlank(keyPrefix)){
		    JedisUserUtils.removeWithKeyPrefix(keyPrefix);
		}
	}

	@Override
	public void removeObject(ModuleType moduleType, String key) {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(key)){
		     JedisUserUtils.delObject(key);
		}
		String keyPrefix=ConfigUtil.getConfig("/dataSource/cache", moduleType.toString());
		if(StringUtils.isNotBlank(keyPrefix)){
		    JedisUserUtils.removeWithKeyPrefix(keyPrefix);
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
			JedisUserUtils.setByte(key, value, defaultCacheSeconds);
		} else if (cacheSeconds == -1) {
			JedisUserUtils.setByte(key, value, 0);
		} else {
			JedisUserUtils.setByte(key, value, cacheSeconds);
		}
	}

  
}
