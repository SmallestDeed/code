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
public class SystemProvider {

    private final static Logger logger = LoggerFactory.getLogger(SystemProvider.class);
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Condition STOP = LOCK.newCondition();
    public static void main(String[] args) throws Exception {
        logger.info("############### System Server Starting... ###############");
        ApplicationContext context = new SpringApplicationBuilder()
                .sources(SystemProvider.class)
                .web(false)
                .run(args);

        logger.info("############### System Server Start Finish! ###############");
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

