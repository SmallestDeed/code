package com.sandu.config;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.common.utils.StringToDateConverter;
import com.sandu.interceptor.LoginUserInterceptor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * yun
 *
 * @author Yoco (yocome@gmail.com)
 * @datetime 2017/11/19 21:21
 */
@Configuration
@EnableSwagger2
@Profile("local")
public class WebLocalConfig extends WebMvcConfigurerAdapter {

    /**
     * 自动转换时间格式
     *
     * @param registry date
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }
    
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		// 设置时间格式转换
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 设置不转为null字段
		// objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 设置忽略未知字段
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}

	/**
	 * 解决乱码问题
	 * 
	 * @return
	 */
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		return converter;
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

	/**
	 * 设置SwaggerUI
	 *
	 * @return
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.sandu.web")).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("选装网小程序 API").description("更多内容，请访问: http://www.sanduspace.com")
				.termsOfServiceUrl("http://www.sanduspace.com").version("1.0").build();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 用户会话拦截器
		InterceptorRegistration addInterceptor = registry.addInterceptor(new LoginUserInterceptor());
		addInterceptor.addPathPatterns("/**");
		addInterceptor.excludePathPatterns("/swagger-ui.html**");
		addInterceptor.excludePathPatterns("/v1/sandu/mini/sysuser/**");
		super.addInterceptors(registry);
	}

}
