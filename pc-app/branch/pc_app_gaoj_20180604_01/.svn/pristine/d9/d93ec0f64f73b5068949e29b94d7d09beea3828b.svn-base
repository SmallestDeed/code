package com.nork.common.cache.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nork.common.util.ConfigUtil;
import com.nork.common.util.ObjectUtils;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;

/**
 * Created by Administrator on 2017-12-22.
 */
public class JedisUserUtils {

    private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);

    private static ShardedJedisPool shardedJedisPool2 = null;
    public static final String KEY_PREFIX = ConfigUtil.getConfig("/config/redis", "redis.pool.login.keyPrefix");

    /**
     *
     * @Title: initPool
     * @Description: 进行pool的初始化
     * @param: @return
     * @return: ShardedJedisPool
     * @throws
     */
    private static synchronized ShardedJedisPool initPool() {

        if ( shardedJedisPool2 == null ) {
            shardedJedisPool2 = SpringContextHolder.getBean("shardedJedisPool2");
            if ( shardedJedisPool2 == null ) {
            }
            else {}
        }
        else {

        }
        return shardedJedisPool2;
    }

    /**
     * 获取的方法进行修改
     * @author louxinhua
     * @return
     */
    public static ShardedJedisPool getShardedJedisPool() {

        if ( shardedJedisPool2 == null ) {
            return initPool();
        }
        else {
            return shardedJedisPool2;
        }
    }





    /**
     * 获取缓存
     *
     * @param key
     *            键
     * @return 值
     */
    public static String get(String key) {

        String value = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.get(key);
                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
                logger.info("get {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.error("get {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取缓存
     *
     * @param key
     *            键
     * @return 值
     */
    public static Object getObject(String key) {
        Object value = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                value = toObject(jedis.get(getBytesKey(key)));
                logger.info("getObject {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.error("getObject {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param cacheSeconds
     *            超时时间，0为不超时
     * @return
     */
    public static String set(String key, String value, int cacheSeconds) {
        String result = null;

        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.set(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.info("set {} = {}", key, value);
        } catch (Exception e) {
            logger.error("set {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param cacheSeconds
     *            超时时间，0为不超时
     * @return
     */
    public static String setObject(String key, Object value, int cacheSeconds) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis != null) {
                result = jedis.set(getBytesKey(key), toBytes(value));
                if (cacheSeconds != 0) {
                    jedis.expire(key, cacheSeconds);
                }
                logger.info("setObject {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.error("setObject {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param cacheSeconds
     *            超时时间，0为不超时
     * @return
     */
    public static String setByte(String key, byte[] value, int cacheSeconds) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis != null) {
                result = jedis.set(getBytesKey(key), value);
                if (cacheSeconds != 0) {
                    jedis.expire(key, cacheSeconds);
                }
                logger.info("setObject {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.error("setObject {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取List缓存
     *
     * @param key
     *            键
     * @return 值
     */
    public static List<String> getList(String key) {
        List<String> value = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.lrange(key, 0, -1);
                logger.info("getList {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.error("getList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取List缓存,出队列(阻塞模式)
     *
     * @param key
     *            键
     * @return 值
     */
    public static List<String>  getBrpopList(String key) {
        List<String>  value = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
//				 value = jedis.brpop(2,key);
                value = jedis.blpop(2, key);
                Long size = jedis.llen(key);
                logger.error("getBrpopList size= " + size);
                logger.info("getObjectList {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.error("getObjectList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }
    /**
     * 获取List缓存
     *
     * @param key
     *            键
     * @return 值
     */
    public static List<Object> getObjectList(String key) {
        List<Object> value = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                List<byte[]> list = jedis.lrange(getBytesKey(key), 0, -1);
                value = Lists.newArrayList();
                for (byte[] bs : list) {
                    value.add(toObject(bs));
                }
                logger.info("getObjectList {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.error("getObjectList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取List缓存
     *
     * @param key
     *            键
     * @return 值
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getTList(String key) {
        List<T> value = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                List<byte[]> list = jedis.lrange(getBytesKey(key), 0, -1);
                value = Lists.newArrayList();
                for (byte[] bs : list) {
                    value.add((T) toObject(bs));
                }
                logger.info("getObjectList {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.error("getObjectList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置List缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param cacheSeconds
     *            超时时间，0为不超时
     * @return
     */
    public static long setList(String key, List<String> value, int cacheSeconds) {
        long result = 0;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.rpush(key, (String[]) value.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.info("setList {} = {}", key, value);
        } catch (Exception e) {
            logger.error("setList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置List缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param cacheSeconds
     *            超时时间，0为不超时
     * @return
     */
    public static long setObjectList(String key, List<Object> value, int cacheSeconds) {
        long result = 0;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                jedis.del(key);
            }
            List<byte[]> list = Lists.newArrayList();
            for (Object o : value) {
                list.add(toBytes(o));
            }
            result = jedis.rpush(getBytesKey(key), (byte[][]) list.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.info("setObjectList {} = {}", key, value);
        } catch (Exception e) {
            logger.error("setObjectList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return
     */
    public static long listAdd(String key, String... value) {
        long result = 0;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.rpush(key, value);
            Long size = jedis.llen(key);
            logger.error("listAdd size= " + size);
            logger.info("listAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.error("listAdd {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return
     */
    public static long listLAdd(String key, String... value) {
        long result = 0;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.lpush(key, value);
            logger.debug("listAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("listAdd {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }
    /**
     * 向List缓存中添加值
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return
     */
    public static long listObjectAdd(String key, Object... value) {
        long result = 0;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            List<byte[]> list = Lists.newArrayList();
            for (Object o : value) {
                list.add(toBytes(o));
            }
            result = jedis.rpush(getBytesKey(key), (byte[][]) list.toArray());
            logger.info("listObjectAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.error("listObjectAdd {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取缓存
     *
     * @param key
     *            键
     * @return 值
     */
    public static Set<String> getSet(String key) {
        Set<String> value = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.smembers(key);
                logger.info("getSet {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.error("getSet {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取缓存
     *
     * @param key
     *            键
     * @return 值
     */
    public static Set<Object> getObjectSet(String key) {
        Set<Object> value = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                value = Sets.newHashSet();
                Set<byte[]> set = jedis.smembers(getBytesKey(key));
                for (byte[] bs : set) {
                    value.add(toObject(bs));
                }
                logger.info("getObjectSet {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.error("getObjectSet {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置Set缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param cacheSeconds
     *            超时时间，0为不超时
     * @return
     */
    public static long setSet(String key, Set<String> value, int cacheSeconds) {
        long result = 0;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.sadd(key, (String[]) value.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.info("setSet {} = {}", key, value);
        } catch (Exception e) {
            logger.error("setSet {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置Set缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param cacheSeconds
     *            超时时间，0为不超时
     * @return
     */
    public static long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
        long result = 0;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                jedis.del(key);
            }
            Set<byte[]> set = Sets.newHashSet();
            for (Object o : value) {
                set.add(toBytes(o));
            }
            result = jedis.sadd(getBytesKey(key), (byte[][]) set.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.info("setObjectSet {} = {}", key, value);
        } catch (Exception e) {
            logger.error("setObjectSet {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return
     */
    public static long setSetAdd(String key, String... value) {
        long result = 0;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.sadd(key, value);
            logger.info("setSetAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.error("setSetAdd {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return
     */
    public static long setSetObjectAdd(String key, Object... value) {
        long result = 0;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> set = Sets.newHashSet();
            for (Object o : value) {
                set.add(toBytes(o));
            }
            result = jedis.rpush(getBytesKey(key), (byte[][]) set.toArray());
            logger.info("setSetObjectAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.error("setSetObjectAdd {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取Map缓存
     *
     * @param key
     *            键
     * @return 值
     */
    public static Map<String, String> getMap(String key) {
        Map<String, String> value = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.hgetAll(key);
                logger.info("getMap {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.error("getMap {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取Map缓存
     *
     * @param key
     *            键
     * @return 值
     */
    public static Map<String, Object> getObjectMap(String key) {
        Map<String, Object> value = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                value = Maps.newHashMap();
                Map<byte[], byte[]> map = jedis.hgetAll(getBytesKey(key));
                for (Map.Entry<byte[], byte[]> e : map.entrySet()) {
                    value.put(StringUtils.toString(e.getKey()), toObject(e.getValue()));
                }
                logger.info("getObjectMap {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.error("getObjectMap {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置Map缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param cacheSeconds
     *            超时时间，0为不超时
     * @return
     */
    public static String setMap(String key, Map<String, String> value, int cacheSeconds) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.hmset(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.info("setMap {} = {}", key, value);
        } catch (Exception e) {
            logger.error("setMap {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置Map缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param cacheSeconds
     *            超时时间，0为不超时
     * @return
     */
    public static String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                jedis.del(key);
            }
            Map<byte[], byte[]> map = Maps.newHashMap();
            for (Map.Entry<String, Object> e : value.entrySet()) {
                map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
            }
            result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>) map);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.info("setObjectMap {} = {}", key, value);
        } catch (Exception e) {
            logger.error("setObjectMap {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Map缓存中添加值
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return
     */
    public static String mapPut(String key, Map<String, String> value) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hmset(key, value);
            logger.info("mapPut {} = {}", key, value);
        } catch (Exception e) {
            logger.error("mapPut {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Map缓存中添加值
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return
     */
    public static String mapObjectPut(String key, Map<String, Object> value) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            Map<byte[], byte[]> map = Maps.newHashMap();
            for (Map.Entry<String, Object> e : value.entrySet()) {
                map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
            }
            result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>) map);
            logger.info("mapObjectPut {} = {}", key, value);
        } catch (Exception e) {
            logger.error("mapObjectPut {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 移除Map缓存中的值
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return
     */
    public static long mapRemove(String key, String mapKey) {
        long result = 0;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hdel(key, mapKey);
            logger.info("mapRemove {}  {}", key, mapKey);
        } catch (Exception e) {
            logger.error("mapRemove {}  {}", key, mapKey, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 移除Map缓存中的值
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return
     */
    public static long mapObjectRemove(String key, String mapKey) {
        long result = 0;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hdel(getBytesKey(key), getBytesKey(mapKey));
            logger.info("mapObjectRemove {}  {}", key, mapKey);
        } catch (Exception e) {
            logger.error("mapObjectRemove {}  {}", key, mapKey, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 判断Map缓存中的Key是否存在
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return
     */
    public static boolean mapExists(String key, String mapKey) {
        boolean result = false;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hexists(key, mapKey);
            logger.info("mapExists {}  {}", key, mapKey);
        } catch (Exception e) {
            logger.error("mapExists {}  {}", key, mapKey, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 判断Map缓存中的Key是否存在
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return
     */
    public static boolean mapObjectExists(String key, String mapKey) {
        boolean result = false;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hexists(getBytesKey(key), getBytesKey(mapKey));
            logger.info("mapObjectExists {}  {}", key, mapKey);
        } catch (Exception e) {
            logger.error("mapObjectExists {}  {}", key, mapKey, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 删除缓存
     *
     * @param key
     *            键
     * @return
     */
    public static long del(String key) {
        long result = 0;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                result = jedis.del(key);
                logger.info("del {}", key);
            } else {
                logger.info("del {} not exists", key);
            }
        } catch (Exception e) {
            logger.error("del {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 删除缓存
     *
     * @param key
     *            键
     * @return
     */
    public static long delObject(String key) {
        long result = 0;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                result = jedis.del(getBytesKey(key));
                logger.info("delObject {}", key);
            } else {
                logger.info("delObject {} not exists", key);
            }
        } catch (Exception e) {
            logger.error("delObject {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 缓存是否存在
     *
     * @param key
     *            键
     * @return
     */
    public static boolean exists(String key) {
        boolean result = false;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.exists(key);
            logger.info("exists {}", key);
        } catch (Exception e) {
            logger.error("exists {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 缓存是否存在
     *
     * @param key
     *            键
     * @return
     */
    public static boolean existsObject(String key) {
        boolean result = false;
        ShardedJedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.exists(getBytesKey(key));
            logger.info("existsObject {}", key);
        } catch (Exception e) {
            logger.error("existsObject {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取资源
     *
     * @return
     * @throws JedisException
     */
    public static ShardedJedis getResource() throws JedisException {
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
     * 归还资源
     *
     * @param jedis
     */
    public static void returnBrokenResource(ShardedJedis jedis) {
        if (jedis != null) {
            getShardedJedisPool().returnBrokenResource(jedis);
        }
    }

    /**
     * 释放资源
     *
     * @param jedis
     */
    public static void returnResource(ShardedJedis jedis) {
        if (jedis != null) {
            getShardedJedisPool().returnResource(jedis);
        }
    }

    /***
     * 根据KEY的前缀批量删除缓存对象
     *
     * @param keyPrefix
     */
    public static void removeWithKeyPrefix(String keyPrefix) {
        ShardedJedis shareJedis = null;
        try {
            shareJedis = getResource();
            Collection<Jedis> jedisC = shareJedis.getAllShards();
            if (jedisC == null) {
                return;
            }
            Iterator<Jedis> iter = jedisC.iterator();
            while (iter.hasNext()) {
                Jedis jedis = iter.next();
                Set<String> keys = jedis.keys(keyPrefix + "*");
                String[] keyArray;
                if (keys != null && keys.size() > 0) {
                    keyArray = keys.toArray(new String[keys.size()]);
                    jedis.del(keyArray);
                    for (String key : keyArray) {
                        if (!key.contains("_get_")) { // 不删除单个缓存对象
                            jedis.del(getBytesKey(key));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("removeWithKeyPrefix = {}", keyPrefix, e);
        } finally {
            returnResource(shareJedis);
        }
    }

    /**
     * 获取byte[]类型Key
     *
     * @param key
     * @return
     */
    public static byte[] getBytesKey(Object object) {
        if (object instanceof String) {
            return StringUtils.getBytes((String) object);
        } else {
            return ObjectUtils.serialize(object);
        }
    }

    /**
     * Object转换byte[]类型
     *
     * @param key
     * @return
     */
    public static byte[] toBytes(Object object) {
        return ObjectUtils.serialize(object);
    }

    /**
     * byte[]型转换Object
     *
     * @param key
     * @return
     */
    public static Object toObject(byte[] bytes) {
        return ObjectUtils.unserialize(bytes);
    }

    /**
     * 得到所有缓存key
     *
     * @return
     */
    public static Set<String> getAllKey() {
        ShardedJedis shareJedis = null;
        Set<String> allKeys = new HashSet<String>();
        try {
            shareJedis = getResource();
            Collection<Jedis> jedisC = shareJedis.getAllShards();
            for (Jedis jedis : jedisC) {
                Set<String> keys = jedis.keys("*");
                allKeys.addAll(keys);
            }
        } catch (Exception e) {
            logger.error("getAllKey = {}", e);
        } finally {
            returnResource(shareJedis);
        }
        return allKeys;
    }

    public static Long getSizeOfList(String key) {
        ShardedJedis jedis = null;
        Long size = 0L;
        try {
            jedis = getResource();
            size = jedis.llen(key);
        } catch (Exception e) {
            logger.error("getSizeOfList {} = {}", key, size);
        } finally {
            returnResource(jedis);
        }
        return size;
    }

    public void disconnect() {
        ShardedJedis shardedJedis = getResource();
        shardedJedis.disconnect();
    }

}
