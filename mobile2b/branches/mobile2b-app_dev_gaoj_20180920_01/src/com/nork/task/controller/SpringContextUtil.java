package com.nork.task.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 3:13 2018/6/6 0006
 * @Modified By:
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String beanName) {
        logger.info("SpringContextUtil  -------- 获取到beanName了 ："+beanName);
        return applicationContext.getBean(beanName);
    }
}
