package com.sandu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ImportResource(value = {"classpath:spring/spring.xml"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SanduYunMiniWebApplication extends WebMvcConfigurerAdapter{
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }
	public static void main(String[] args) {
		SpringApplication.run(SanduYunMiniWebApplication.class, args);
	}
}
