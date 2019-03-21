package com.sandu.config;

import com.sandu.interceptor.CheckTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2018/3/4 17:17
 */
//@Profile(value = {"dev", "test", "ci", "online"})
@Configuration
public class EnvOnlineConfig extends WebMvcConfigurerAdapter {

    /**
     * 配置拦截器
     *
     * @param registry 拦截器注册实例
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // addPathPatterns 用于添加拦截规则
        registry.addInterceptor(new CheckTokenInterceptor())
                .addPathPatterns("/v1/**");
        // excludePathPatterns 用户排除拦截
        super.addInterceptors(registry);
    }
}
