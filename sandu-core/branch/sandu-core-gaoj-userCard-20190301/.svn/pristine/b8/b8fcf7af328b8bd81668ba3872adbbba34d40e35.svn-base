package com.sandu.api.base.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * cms
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2018/4/18 23:58
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * 获取应用上下文
     *
     * @return 应用上下文
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 获取注册的Bean
     *
     * @param clazz 传入类
     * @param <T>   传入类型
     * @return 返回指定Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * 通过Bean注册名，获取Bean
     *
     * @param name Bean注册名
     * @param <T>  返回bean的类
     * @return 返回bean
     */
    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    /**
     * 通过Bean的注册名获取 Bean
     *
     * @param name  Bean注册名
     * @param clazz 转换类
     * @param <T>   返回类
     * @return 返回指定类的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
