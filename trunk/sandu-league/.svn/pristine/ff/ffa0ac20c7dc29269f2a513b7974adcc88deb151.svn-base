package com.sandu.config;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(environment, "redis.pool.");


        this.uri = resolver.getProperty("uri");
        this.maxActive = Integer.valueOf(resolver.getProperty("maxActive"));
        this.maxIdle = Integer.valueOf(resolver.getProperty("maxIdle"));
        this.minIdle = Integer.valueOf(resolver.getProperty("minIdle"));
        this.maxWaitMills = Long.valueOf(resolver.getProperty("maxWaitMillis"));
        this.maxWait = Integer.valueOf(resolver.getProperty("maxWait"));
        log.debug("Uri={}", this.uri);
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

}
