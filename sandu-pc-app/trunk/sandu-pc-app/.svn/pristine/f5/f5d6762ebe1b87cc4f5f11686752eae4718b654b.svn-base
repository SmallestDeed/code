package com.sandu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@SpringBootApplication
@ImportResource(value = {"classpath:spring/spring.xml", "classpath:spring-sso-config.xml"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
    
    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
    	MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    	List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
    	supportedMediaTypes.add(MediaType.TEXT_HTML);
    	supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    	mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
    	return mappingJackson2HttpMessageConverter;
    }

}
