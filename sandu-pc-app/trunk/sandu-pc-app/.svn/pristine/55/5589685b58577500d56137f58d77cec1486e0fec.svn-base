package com.sandu.aes.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;

/**
 * 读取 aes.properties 配置文件的信息并赋值给静态变量
 * 因为springboot不支持给静态变量直接注入配置信息
 * Created by chenm on 2018/11/21.
 */
@Configuration
@PropertySource("classpath:config/aes.properties")
public class FileEncryptConfig {

    // 密匙
    @Value("${aes.resources.encrypt.key}")
    private  String key;
    // 加密开关
    @Value("${aes.resources.encrypt.switch}")
    private  String encryptSwitch;
    // 加密文件后缀配置(确定哪些文件需要加密)
    @Value("${aes.resources.encrypt.fileSuffix}")
    public String encryptFileSuffix;
    // 加密文件存放地址
    @Value("${aes.resources.encrypt.upload.root}")
    public String encryptUploadRoot;
    // 文件加密方式
    @Value("${aes.resources.encrypt.way}")
    public String encryptWay;
    // 原文件(非加密路径)存放地址
    @Value("${aes.resources.noEncrypt.upload.root}")
    public String noEncryptUploadRoot;

    @Bean
    public int initStaticVariate(){
        FileEncrypt.setKey(this.key);
        FileEncrypt.setEncryptSwitch(this.encryptSwitch);
        FileEncrypt.setEncryptFileSuffix(this.encryptFileSuffix);
        FileEncrypt.setEncryptWay(this.encryptWay);
        FileEncrypt.setNoEncryptUploadRoot(this.noEncryptUploadRoot);
        return 0;
    }


}
