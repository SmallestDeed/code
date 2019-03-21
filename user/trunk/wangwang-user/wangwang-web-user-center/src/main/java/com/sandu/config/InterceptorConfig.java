package com.sandu.config;

import com.sandu.interceptor.LoginUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2018/3/5 20:56
 */
@Configuration
@Profile({"local","dev","test","ci","online"})
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //用户会话拦截器
        registry.addInterceptor(new LoginUserInterceptor())
                    .addPathPatterns("/**")
                     .excludePathPatterns("/swagger-resources/**","/getCode/**","/getSms/**","/**/checkMobileExist/**");
        super.addInterceptors(registry);
    }
}
