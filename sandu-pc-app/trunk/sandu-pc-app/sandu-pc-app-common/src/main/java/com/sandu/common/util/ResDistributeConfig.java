package com.sandu.common.util;

import com.sandu.aes.util.FileEncrypt;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * 读取文件资源存储地址配置类
 * Created by chenm on 2018/11/21.
 */
@Configuration
@Data
public class ResDistributeConfig {

    @Value("${app.resources.upload.root.distribute}")
    private String noEncryptDistribute;
    @Value("${app.resources.encrypt.upload.root.distribute}")
    private String encryptDistribute;
    @Value("${app.resources.url.distribute}")
    private String urlDistribute;

    // 文件默认上传根目录
    @Value("${app.upload.root}")
    public String defaultUploadRoot;
    //默认访问路径(根,域名,url前面部分)
    @Value("${app.resources.url}")
    public String resourceUrl;


    @Bean
    public int initStatic(){
        ResDistributeUtils.setNoEncryptDistribute(this.noEncryptDistribute);
        ResDistributeUtils.setEncryptDistribute(this.encryptDistribute);
        ResDistributeUtils.setUrlDistribute(this.urlDistribute);
        ResDistributeUtils.setEncryptDistributeMap(Utils.getMapFromListJsonStr(encryptDistribute, "cfg", "modelName", "uploadRoot"));
        ResDistributeUtils.setNoEncryptDistributeMap(Utils.getMapFromListJsonStr(noEncryptDistribute, "cfg", "modelName", "uploadRoot"));
        ResDistributeUtils.setUrlDistributeMap(Utils.getMapFromListJsonStr(urlDistribute, "cfg", "modelName", "uploadRoot"));
        FileEncrypt.setDefaultUploadRoot(this.defaultUploadRoot);
        FileEncrypt.setResourceUrl(this.resourceUrl);
        return 0;
    }
}
