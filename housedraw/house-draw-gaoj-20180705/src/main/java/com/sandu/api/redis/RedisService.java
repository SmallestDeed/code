package com.sandu.api.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface RedisService {

    /**
     * 添加hash类型缓存
     * @param key
     * @param hashKey
     * @param timeout
     * @param unit
     * @param callback
     * @return
     */
    String getCacheOfHash(String key,
                    String hashKey,
                    Long timeout,
                    TimeUnit unit,
                    RedisCallback callback);

    void clearAllCache();

    void clearCacheByKeys(List<String> keys);
}
