package com.sandu.service.redis.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.sandu.api.redis.RedisCallback;
import com.sandu.api.redis.RedisService;
import com.sandu.common.constant.redis.RedisKeyConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
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

    @Override
    public String getCacheOfHash(String key, String hashKey, Long timeout, TimeUnit unit, RedisCallback callback) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)) {
            return callback.get();
        }

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
    }

    @Override
    public void clearAllCache() {
        RedisKeyConstant[] redisKeys = RedisKeyConstant.values();
        if (redisKeys.length <= 0) {
            log.info("clear redis key length 0");
            return;
        }

        List<String> keys = Lists.newArrayList(redisKeys).stream().map(Enum::toString).collect(Collectors.toList());
        this.clearCacheByKeys(keys);
    }

    @Override
    public void clearCacheByKeys(List<String> keys) {
        log.info("clear cache by key {}", Arrays.toString(keys.toArray()));
        redisTemplate.delete(keys);
    }

    private String addCache(String key, String hashKey, Long timeout, TimeUnit unit, RedisCallback callback,
                            HashOperations<String, Object, Object> hashOperations) {
        String value = callback.get();
        if (value == null) {
            return null;
        }

        hashOperations.put(key, hashKey, value);

        // set expire
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.expire(key, timeout, unit);
        }

        return value;
    }
}
