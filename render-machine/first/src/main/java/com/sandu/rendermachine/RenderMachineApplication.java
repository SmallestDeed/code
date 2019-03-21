package com.sandu.rendermachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Slf4j
@ComponentScan
@EnableScheduling
@EnableTransactionManagement
@ImportResource("classpath*:spring-sso-config.xml")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RenderMachineApplication extends WebMvcConfigurerAdapter{

	public static void main(String[] args) {

		log.info("############### RenderMachine Server Starting... ###############");
		SpringApplication.run(RenderMachineApplication.class, args);
		log.info("############### RenderMachine Server Start Finish!!!  ###############");
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false);
	}
}
