//package com.sandu.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * CopyRight (c) 2017 Sandu Technology Inc.
// * <p>
// * RedisConfig
// *
// * @author songjianming@sanduspace.cn
// * @datetime 2018/06/27 22:36
// */
//@Slf4j
//@Configuration
//public class RedisConfig {
//
//    @Bean("redisPoolConfig")
//    @ConfigurationProperties(prefix = "spring.redis")
//    public JedisPoolConfig getRedisConfig() {
//        return new JedisPoolConfig();
//    }
//
//    @Bean("redisConnectionFactory")
//    @ConfigurationProperties(prefix = "spring.redis")
//    public JedisConnectionFactory getConnectionFactory(JedisPoolConfig redisPoolConfig) {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setPoolConfig(redisPoolConfig);
//        log.info("JedisConnectionFactory bean init success.");
//        return factory;
//    }
//
//    @Bean
//    public RedisTemplate<?, ?> getRedisTemplate(JedisConnectionFactory redisConnectionFactory) {
//        return new StringRedisTemplate(redisConnectionFactory);
//    }
//}
