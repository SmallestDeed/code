package com.sandu.analyze;

import com.sandu.amqp.config.AmqpRabbitConfig;
import com.sandu.amqp.config.PropertySourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(value = "com.sandu")
@ImportAutoConfiguration(value = {PropertySourceConfig.class, AmqpRabbitConfig.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class AnalyzeApplication {

    public static void main(String[] args) {
        log.info("############### Analyze Server Starting... ###############");
        SpringApplication.run(AnalyzeApplication.class, args);
        log.info("############### Analyze Server Start Finish! ###############");

    }
}
