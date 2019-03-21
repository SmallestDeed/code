package com.sandu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2017/12/15 10:11
 */
@SpringBootApplication
@ImportResource("classpath:dubbo.xml")
@Slf4j
public class ProductProvider {

    private final static ReentrantLock LOCK = new ReentrantLock();
    private final static Condition STOP = LOCK.newCondition();

    public static void main(String[] args) {
        log.info("--- Start Product Provider ----");
        ApplicationContext context = new SpringApplicationBuilder()
                .sources(ProductProvider.class)
                .web(true)
                .run(args);

        log.info("--- Start Product Provider Finish! ---");
        try {
            LOCK.lock();
            STOP.await();
        } catch (InterruptedException e) {
            log.error("{}", e.getLocalizedMessage());
        } finally {
            LOCK.unlock();
        }
    }
}
