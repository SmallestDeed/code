package com.sandu.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.interceptor.LoginUserInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.SimpleDateFormat;

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
@Profile("dev")
public class WebDevConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        //设置时间格式转换
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //设置不转为null字段
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
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


    /**
     * 设置SwaggerUI
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sandu.web"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("三度空间 运营网 API")
                .description("更多内容，请访问: http://www.sanduspace.com")
                .termsOfServiceUrl("http://www.sanduspace.com")
                .version("1.0")
                .build();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //用户会话拦截器
        registry.addInterceptor(new LoginUserInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
