package com.sandu.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Slf4j
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@ImportResource("classpath*:spring-sso-config.xml")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SearchWebApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) throws Exception {
        log.info("############### Search Web Server Starting... ###############");
        SpringApplication.run(SearchWebApplication.class, args);
        log.info("############### Search Web Server Start Finish! ###############");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }
}
