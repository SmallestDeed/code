package com.sandu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2017/12/16 18:48
 */
@SpringBootApplication
@ImportResource(value = {"classpath:dubbo.xml"})
public class StorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }
}
