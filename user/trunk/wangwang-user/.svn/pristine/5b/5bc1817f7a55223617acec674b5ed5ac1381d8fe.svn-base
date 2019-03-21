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
public class UserProvider {

    private final static Logger logger = LoggerFactory.getLogger(UserProvider.class);
    private final static ReentrantLock LOCK = new ReentrantLock();
    private final static Condition STOP = LOCK.newCondition();

    public static void main(String[] args) {
        logger.info("--- Start User Provider ----");
        ApplicationContext context = new SpringApplicationBuilder()
                .sources(UserProvider.class)
                .web(false)
                .run(args);

        logger.info("--- Start User Provider Finish! ---");

        try {
            LOCK.lock();
            STOP.await();
        } catch (InterruptedException e) {
            logger.error("{}", e.getLocalizedMessage());
        } finally {
            LOCK.unlock();
        }
    }
}
