package com.sandu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/26 16:03
 */
@Slf4j
@SpringBootApplication
@ImportResource(value = {"classpath:spring/spring.xml"})
public class SampleProvider {

    private final static ReentrantLock LOCK = new ReentrantLock();
    private final static Condition STOP = LOCK.newCondition();

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder()
                .sources(SampleProvider.class)
                .web(false)
                .run(args);
        log.info("--- Strart Proovider finish! ---");

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
