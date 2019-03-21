package com.sandu.api.base.common.bloom.operator;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Pool;

@Slf4j
public class ShardedJedisDataOperator implements DataOperator {
    private final Pool<ShardedJedis> shardedJedisPool;

    public ShardedJedisDataOperator(Pool<ShardedJedis> shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    @Override
    public DataOperator init(long bits) {
        return this;
    }

    @Override
    public boolean put(String key, long bit) {
        try (ShardedJedis shardedJedis = shardedJedisPool.getResource()) {
            return shardedJedis.setbit(key, bit, true);
        } catch (Exception e) {
            log.error("set error.", e);
        }
        return false;
    }

    @Override
    public boolean get(String key, long bit) {
        try (ShardedJedis shardedJedis = shardedJedisPool.getResource()) {
            return shardedJedis.getbit(key, bit);
        } catch (Exception e) {
            log.error("set error.", e);
        }
        return false;
    }
}
