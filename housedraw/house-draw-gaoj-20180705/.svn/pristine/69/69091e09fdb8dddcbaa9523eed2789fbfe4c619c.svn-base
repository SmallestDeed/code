package com.sandu.config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * demo
 *
 * @author Yoco (yocome@gmail.com)
 * @datetime 2017/12/11 22:36
 */

@Configuration
@Profile("local")
//@EnableSwagger2
public class AppLocalConfig {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		// 设置时间格式转换
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 设置不转为null字段
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 设置忽略未知字段
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		return objectMapper;
	}

	/**
	 * 设置JSON转换
	 *
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter messageConverter(ObjectMapper objectMapper) {
		return new MappingJackson2HttpMessageConverter(objectMapper);
	}

	/**
	 * 设置SwaggerUI
	 *
	 * @return
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.sandu.web"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("三度空间 Demo RESTFull API")
				.description("更多内容，请访问: http://www.sanduspace.cn")
				.termsOfServiceUrl("http://www.sanduspace.cn")
				.version("1.0").build();
	}
}
