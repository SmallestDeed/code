package com.sandu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication
@ImportResource(value = {"classpath:spring/spring.xml"})
public class ModuleProvider {
    private final static Logger logger = LoggerFactory.getLogger(ModuleProvider.class);
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Condition STOP = LOCK.newCondition();
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new SpringApplicationBuilder()
                .sources(ModuleProvider.class)
                .web(false)
                .run(args);
        try {
            LOCK.lock();
            STOP.await();
        } catch (InterruptedException e) {
            logger.warn("Dubbo service server stopped, interrupted by other thread!", e);
        } finally {
            LOCK.unlock();
        }
    }
}
