package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * yun
 * i love three things in this world , sun moon and you , sun for morning , moon for night , you forever .
 * @author Yoco (yocome@gmail.com)
 * @datetime 2017/11/19 19:47
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ImportResource(value = {"classpath:spring/spring.xml"})
public class MiniProgramConsumer {

    public static void main(String[] args) {
        SpringApplication.run(MiniProgramConsumer.class, args);
    }
}
