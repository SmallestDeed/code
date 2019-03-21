package com.sandu.config;

import com.sandu.api.base.common.bloom.filter.BloomFilter;
import com.sandu.api.base.common.bloom.filter.SimpleBloomFilter;
import com.sandu.api.base.common.bloom.operator.MemoryDataOperator;
import com.sandu.api.base.common.bloom.operator.ShardedJedisDataOperator;
import com.sandu.api.base.common.cache.RedisKeyConstans;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Arrays;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * yun
 *
 * @author Yoco (yocome@gmail.com)
 * @datetime 2017/11/19 21:30
 */
@Configuration
public class RedisConfig implements EnvironmentAware {

    private String uri;

    private Integer maxActive;

    private Integer maxIdle;

    private Integer minIdle;

    private Long maxWaitMills;

    private Integer maxWait;

    @Override
    public void setEnvironment(Environment environment) {
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(environment, "redis.");
        this.uri = resolver.getProperty("pool.uri");
        this.maxActive = Integer.valueOf(resolver.getProperty("pool.maxActive"));
        this.maxIdle = Integer.valueOf(resolver.getProperty("pool.maxIdle"));
        this.minIdle = Integer.valueOf(resolver.getProperty("pool.minIdle"));
        this.maxWaitMills = Long.valueOf(resolver.getProperty("pool.maxWaitMillis"));
        this.maxWait = Integer.valueOf(resolver.getProperty("pool.maxWait"));
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(this.maxActive);
        config.setMaxIdle(this.maxIdle);
        config.setMinIdle(this.minIdle);
        config.setMaxWaitMillis(this.maxWaitMills);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        return config;
    }

    @Bean(name = "redis1")
    public ShardedJedisPool shardedJedisPool() {
        return new ShardedJedisPool(jedisPoolConfig(),
                Arrays.asList(new JedisShardInfo(this.uri)));
    }

    @Bean(name = "default")
    public BloomFilter defaultBloomFilter(ShardedJedisPool shardedJedisPool) {
        return SimpleBloomFilter.builder()
                .operator(new ShardedJedisDataOperator(shardedJedisPool))
                .name(RedisKeyConstans.REDIS_BLOOM_FILTER_PREFIX + RedisKeyConstans.REDIS_BLOOM_FILTER_DEFAULT)
                .fpp(0.0001D)
                .expectedInsertions(1000000L)
                .build();
    }

    @Bean(name = "decorationTips")
    public BloomFilter decorationTipsBloomFilter(ShardedJedisPool shardedJedisPool) {
        return SimpleBloomFilter.builder()
                .operator(new ShardedJedisDataOperator(shardedJedisPool))
                .name(RedisKeyConstans.REDIS_BLOOM_FILTER_PREFIX + RedisKeyConstans.REDIS_BLOOM_FILTER_DECORATION_TIPS)
                .fpp(0.0001D)
                .expectedInsertions(1000000L)
                .build();
    }

    @Bean(name = "memory")
    public BloomFilter memoryBloomFilter() {
        return SimpleBloomFilter.builder()
                .operator(new MemoryDataOperator())
                .fpp(0.0001D)
                .expectedInsertions(200000L)
                .build();
    }
}
