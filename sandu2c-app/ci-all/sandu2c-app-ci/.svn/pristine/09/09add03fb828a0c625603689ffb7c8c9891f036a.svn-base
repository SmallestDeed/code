package com.sandu.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class AmqpApplication {

    public static void main(String[] args) {
        log.info("############### AMQP Server Starting... ###############");
        SpringApplication.run(AmqpApplication.class, args);
        log.info("############### AMQP Server Start Finish! ###############");

    }
}
