package com.sandu.service.redis.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.sandu.api.redis.RedisCallback;
import com.sandu.api.redis.RedisService;
import com.sandu.common.constant.redis.RedisKeyConstant;
import com.sandu.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RedisTemplate<String, ?> redisTemplate;

    @Autowired
    RedisTemplate<String, String> redisTemplateForList;

    @Override
    public String getCacheByHash(String key, String hashKey, Long timeout, TimeUnit unit, RedisCallback callback) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)) {
            return callback.get();
        }

        try {
            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
            if (hashOperations.hasKey(key, hashKey)) {
                // get cache
                Object value = hashOperations.get(key, hashKey);
                if (value != null) {
                    return value.toString();
                }

                return addCache(key, hashKey, timeout, unit, callback, hashOperations);
            } else {
                return addCache(key, hashKey, timeout, unit, callback, hashOperations);
            }
        } catch (Exception e) {
            // 异常时，去数据库获取数据
            log.warn("获取Redis缓存数据异常，key => {}, hashKey => {}", key, hashKey, e);
        }

        return callback.get();
    }

    @Override
    public void leftPushAllByQueue(String key, List<String> values, RedisCallback callback) {
        if (StringUtils.isEmpty(key)) {
            throw new BizException("Key cannot be empty.");
        }

        if (values == null || values.isEmpty()) {
            log.warn("Values cannot be empty.");
            return;
        }

        ListOperations<String, String> listOperations = redisTemplateForList.opsForList();
        listOperations.leftPushAll(RedisKeyConstant.HOUSE_DRAW_FIX_CUPBOARD_QUEUE.toString(), values);
    }

    @Override
    public String rightPopByQueue(String key, RedisCallback callback) {
        if (StringUtils.isEmpty(key)) {
            throw new BizException("Key cannot be empty.");
//            return callback.get();
        }

        return redisTemplateForList.opsForList().rightPop(key);
    }

    @Override
    public void clearAllCache() {
        RedisKeyConstant[] redisKeys = RedisKeyConstant.values();
        if (redisKeys.length <= 0) {
            log.info("没有需要清理缓存redis key");
            return;
        }

        List<String> keys = Lists.newArrayList(redisKeys).stream().map(Enum::toString).collect(Collectors.toList());
        this.clearCacheByKeys(keys);
    }

    @Override
    public void clearCacheByKeys(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            log.warn("没有需要清理缓存redis key");
            return;
        }

        log.info("clear cache by key {}", Arrays.toString(keys.toArray()));
        redisTemplate.delete(keys);
    }

    @Override
    public Long getListCount(String key) {
        if (key == null || key.isEmpty()) {
            log.warn("没有需要清理缓存redis key");
            return -1L;
        }

        return redisTemplateForList.opsForList().size(key);
    }

    private String addCache(String key, String hashKey, Long timeout, TimeUnit unit, RedisCallback callback,
                            HashOperations<String, Object, Object> hashOperations) {
        String value = callback.get();
        if (value == null) {
            return null;
        }

        hashOperations.put(key, hashKey, value);

        // set expire
        if (timeout != null && timeout > 0 && !redisTemplate.hasKey(key)) {
            redisTemplate.expire(key, timeout, unit);
        }

        return value;
    }
}
