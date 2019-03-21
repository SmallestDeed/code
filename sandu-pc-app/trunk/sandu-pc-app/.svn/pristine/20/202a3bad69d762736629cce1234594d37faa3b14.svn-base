package com.sandu;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@EnableTransactionManagement
@SpringBootApplication
@ImportResource(value = {"classpath:spring/spring.xml"})
@MapperScan(basePackages = "com.sandu.*.dao")
public class DecorateProvider {

    private final static Logger logger = LoggerFactory.getLogger(DecorateProvider.class);
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Condition STOP = LOCK.newCondition();
    
    public static void main(String[] args) {
        logger.info("############### Decorate Server Starting... ###############");
        ApplicationContext context = new SpringApplicationBuilder()
                .sources(DecorateProvider.class)
                .web(false)
                .run(args);

        logger.info("############### Decorate Server Start Finish! ###############");
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
