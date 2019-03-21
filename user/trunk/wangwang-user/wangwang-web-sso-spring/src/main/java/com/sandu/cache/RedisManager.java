package com.sandu.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

public class RedisManager implements Cache{

    private static Logger logger = LoggerFactory.getLogger(RedisManager.class);
    
    private ShardedJedisPool shardedJedisPool;

    @Override
    public String get(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();            
            return shardedJedis.get(key);
        } catch (Exception ex) {
            logger.error("缓存链接异常:get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }
    
    @Override
    public void expire(String key,int seconds) {
    	if(key==null) return;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.expire(key, seconds);
        } catch (Exception ex) {
            logger.error("expire error[key=" + key + " seconds=" + seconds + "]" + ex.getMessage(), ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
    }
    
    @Override
    public Long del(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.del(key);
        } catch (Exception ex) {
            logger.error("del error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return 0L;
    }

    private void returnBrokenResource(ShardedJedis shardedJedis) {
        if (shardedJedis == null) {
            return;
        }
        try {
            // 容错
            shardedJedis.close();
        } catch (Exception e) {
            logger.error("returnBrokenResource error.", e);
        }
    }

    private void returnResource(ShardedJedis shardedJedis) {
        try {
            shardedJedis.close();
        } catch (Exception e) {
            logger.error("returnResource error.", e);
        }
    }

    

    /**
     * 获取资源
     *
     * @return
     * @throws JedisException
     */
    public ShardedJedis getResource() throws JedisException {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
        } catch (JedisException e) {
            logger.error("getResource.", e);
            returnBrokenResource(jedis);
        }
        return jedis;
    }


	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}
    
    

	
}
