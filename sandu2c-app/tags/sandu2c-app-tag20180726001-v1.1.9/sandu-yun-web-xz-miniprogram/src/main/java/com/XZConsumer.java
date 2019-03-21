package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * yun
 *
 * @author Yoco (yocome@gmail.com)
 * @datetime 2017/11/19 19:47
 */
@SpringBootApplication
@ImportResource(value = {"classpath:spring/spring.xml"})
public class XZConsumer extends WebMvcConfigurerAdapter {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    public static void main(String[] args) {
        SpringApplication.run(XZConsumer.class, args);
    }
}
