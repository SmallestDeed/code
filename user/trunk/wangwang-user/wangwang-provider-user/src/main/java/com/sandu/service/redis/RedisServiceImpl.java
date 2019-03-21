package com.sandu.service.redis;

import com.sandu.api.redis.RedisService;
import com.sandu.api.system.output.SysDictionaryVO;
import com.sandu.common.gson.GsonUtil;
import com.sandu.common.spring.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.Resource;
import java.util.*;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

    private static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    @Resource(name = "shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    @Override
    public boolean set(String key, String value, int seconds) {
        if (value == null) return false;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(key, value);
            shardedJedis.expire(key, seconds);
            return true;
        } catch (Exception ex) {
            logger.error("set error[key=" + key + " value=" + value + " seconds=" + seconds + "]" + ex.getMessage(), ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    @Override
    public boolean set(String key, String value) {
        if (value == null) return false;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(key, value);
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    @Override
    public String get(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.get(key);
        } catch (Exception ex) {
            logger.error("get error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }


    @Override
    public List<String> mget(String... keys) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            Collection<Jedis> shards = shardedJedis.getAllShards();
            Jedis jedis = shards.iterator().next();
            return jedis.mget(keys);
        } catch (Exception ex) {
            logger.error("mget error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    @Override
    public boolean del(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.del(key);
            return true;
        } catch (Exception ex) {
            logger.error("del error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    @Override
    public void expire(String key, int seconds) {
        if (key == null) return;
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
    public boolean sadd(String key, String... value) {
        if (key == null || value == null) {
            return false;
        }
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.sadd(key, value);
            return true;
        } catch (Exception ex) {
            logger.error("sadd error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    @Override
    public void saddObject(String key, Set set) {
        if (set != null && set.size() > 0) {
            String[] stringArray = new String[set.size()];
            int i = 0;
            for (Object obj : set) {
                stringArray[i++] = GsonUtil.toJson(obj);
            }
            this.sadd(key, stringArray);
        }


    }

    @Override
    public Set<String> smembers(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.smembers(key);
        } catch (Exception ex) {
            logger.error("getList error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    @Override
    public <T> Set<T> smembersObject(String key, Class<T> objClass) {
        Set<T> resultSet = new HashSet<T>();
        Set<String> set = smembers(key);
        if (set != null && set.size() > 0) {
            for (String item : set) {
                if (StringUtils.isNoneBlank(item)) {
                    resultSet.add(GsonUtil.fromJson(item, objClass));
                }
            }
        }
        return resultSet;

    }

    @Override
    public Set<String> sunion(String... keys) {
        if (keys == null || keys.length <= 0) return null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            Collection<Jedis> shards = shardedJedis.getAllShards();
            Jedis jedis = shards.iterator().next();
            return jedis.sunion(keys);
        } catch (Exception ex) {
            logger.error("getList error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    @Override
    public Set<String> keys(String pattern) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            Collection<Jedis> shards = shardedJedis.getAllShards();
            Jedis jedis = shards.iterator().next();
            return jedis.keys(pattern);
        } catch (Exception ex) {
            logger.error("keys error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    @Override
    public <T> Set<T> sunionObjects(String[] keys, Class<T> objClass) {
        Set<T> resultSet = new HashSet<T>();
        Set<String> set = sunion(keys);
        if (set != null && set.size() > 0) {
            for (String item : set) {
                if (StringUtils.isNoneBlank(item)) {
                    resultSet.add(GsonUtil.fromJson(item, objClass));
                }
            }
        }
        return resultSet;
    }
    
    /*@Override
    public Set<String> zunionstore(String... sets) {
        ShardedJedis shardedJedis = null;
        try {
        	String dstkey="tempSet";
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.getShard(dstkey).zunionstore(dstkey, new ZParams().aggregate(Aggregate.MAX), sets);
            return shardedJedis.zrange(dstkey, 0, -1); 
        } catch (Exception ex) {
            logger.error("getList error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }*/

    @Override
    public Set<String> zrange(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.zrange(key, 0, -1);
        } catch (Exception ex) {
            logger.error("zrange error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }


    @Override
    public <T> T getObject(String key, Class<T> objClass) {
        if (key == null) return null;
        String json = get(key);
        if (StringUtils.isNotBlank(json)) {
            return GsonUtil.fromJson(json, objClass);
        }
        return null;
    }

    @Override
    public <T> List<T> getObjects(String[] keys, Class<T> objClass) {
        List<T> resultList = new ArrayList<T>();
        List<String> list = mget(keys);
        if (list != null && list.size() > 0) {
            for (String item : list) {
                if (StringUtils.isNoneBlank(item)) {
                    resultList.add(GsonUtil.fromJson(item, objClass));
                }
            }
        }
        return resultList;
    }

    @Override
    public void setObject(String key, Object obj) {
        if (obj == null) return;
        String json = GsonUtil.toJson(obj);
        this.set(key, json);
    }

    @Override
    public long incr(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.incr(key);
        } catch (Exception ex) {
            logger.error("incr error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return 0;
    }

    @Override
    public long decr(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.decr(key);
        } catch (Exception ex) {
            logger.error("incr error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return 0;
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
            jedis = getShardedJedisPool().getResource();
        } catch (JedisException e) {
            logger.error("getResource.", e);
            returnBrokenResource(jedis);
        }
        return jedis;
    }

    /**
     * 获取的方法进行修改
     *
     * @return
     * @author
     */
    public ShardedJedisPool getShardedJedisPool() {
        if (shardedJedisPool == null) {
            return initPool();
        } else {
            return shardedJedisPool;
        }
    }

    /**
     * @throws
     * @Title: initPool
     * @Description: 进行pool的初始化
     * @param: @return
     * @return: ShardedJedisPool
     */
    private synchronized ShardedJedisPool initPool() {
        if (shardedJedisPool == null) {
            shardedJedisPool = SpringContextHolder.getBean(ShardedJedisPool.class);
            if (shardedJedisPool == null) {
            } else {
            }
        } else {

        }
        return shardedJedisPool;
    }
    /**
     * 添加到Map中
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean addMap(String mapName, String key, String value) {
        if (null == mapName || key == null || value == null) {
            return false;
        }

        Map<String, String> objectMap = new HashMap<>(1);
        objectMap.put(key, value);

        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.hmset(mapName, objectMap);
            return true;
        } catch (Exception ex) {
            logger.error("setMap error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }
    /**
     * 获取Map数据
     *
     * @param mapName Map名称
     * @param key     Map的Key对象
     * @return
     */
    @Override
    public String getMap(String mapName, String key) {
        if (null == mapName || null == key) {
            return null;
        }

        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.hget(mapName, key);
        } catch (Exception ex) {
            logger.error("getMap error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }
    /**
     * 删除Map数据
     *
     * @param mapName Map名称
     * @param key     Map的Key对象
     * @return
     */
    @Override
    public boolean delMap(String mapName, String key) {
        if (null == mapName || null == key) {
            return false;
        }

        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.hdel(mapName, key);
            return true;
        } catch (Exception ex) {
            logger.error("delMap error.", ex);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

	@Override
	public void setObject(String key, Object object, int seconds) {
		if(object == null) {
			return;
		}
		String json = GsonUtil.toJson(object);
		this.set(key, json, seconds);
	}

}
