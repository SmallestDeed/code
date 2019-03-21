package com.nork.common.adapter;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class CorsConfigurerAdapter extends WebMvcConfigurerAdapter {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	   //registry.addMapping("/mobile/*").allowedOrigins("*");
		 registry.addMapping("/**");
		         //.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
		         //.allowedHeaders("X-Auth-Token", "Content-Type")
		         //.allowCredentials(false)
		         //.maxAge(3600);
	}
}
