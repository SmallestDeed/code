package com.sandu.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2017/12/16 17:53
 */
@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        //设置时间格式转换
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //设置不转为null字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //设置忽略未知字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

    /**
     * 设置JSON转换
     *
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter messageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(objectMapper());
        return messageConverter;
    }


}
