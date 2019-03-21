package com.sandu.config;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Arrays;


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
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(environment, "redis.pool.login.");
        this.uri = resolver.getProperty("uri");
        this.maxActive = Integer.valueOf(resolver.getProperty("maxActive"));
        this.maxIdle = Integer.valueOf(resolver.getProperty("maxIdle"));
        this.minIdle = Integer.valueOf(resolver.getProperty("minIdle"));
        this.maxWaitMills = Long.valueOf(resolver.getProperty("maxWaitMillis"));
        this.maxWait = Integer.valueOf(resolver.getProperty("maxWait"));
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(this.maxActive);
        config.setMaxIdle(this.maxIdle);
        config.setMinIdle(this.minIdle);
        config.setMaxWaitMillis(this.maxWaitMills);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(true);
        return config;
    }

    @Bean(name = "shardedJedisPool")
    public ShardedJedisPool shardedJedisPool() {
        return new ShardedJedisPool(jedisPoolConfig(),
                Arrays.asList(new JedisShardInfo(this.uri)));
    }


}
