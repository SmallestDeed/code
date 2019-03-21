package com.sandu.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@Configuration
@ComponentScan("com.sandu.gateway.pay.callback.service.async")
@EnableAsync //开启异步任务支持
public class AsyncConfig implements AsyncConfigurer{

        @Override
        public Executor getAsyncExecutor() {//实现AsyncConfigurer接口并重写getAsyncExecutor方法，并返回一个ThreadPoolTaskExecutor，这样我们就获得了一个基于线程池TaskExecutor
            ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setCorePoolSize(10);
            taskExecutor.setMaxPoolSize(80);
            taskExecutor.setQueueCapacity(100);
            taskExecutor.initialize();
            return taskExecutor;
        }

        @Override
        public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
            return null;
        }

}
