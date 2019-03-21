package com.sandu.analyze.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ElasticSearch配置
 *
 * @date 20171211
 * @auth pengxuangang
 */
@Data
@Component
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchConfig {

    //协议
    private final static String TRANSPORT_PROTOCOL = "http";
    //服务器URI(格式->Host:Port/Host:Port/Host:Port)
    private List<String> uri;

    @Bean(name = "elasticsearch", destroyMethod = "close")
    public RestHighLevelClient setRestHighLevelClient() {

        if (null == uri || 0 == uri.size()) {
            throw new IllegalArgumentException("elasticsearch hosts must not be null!");
        }

        //处理配置参数
        HttpHost[] httpHosts = new HttpHost[uri.size()];
        for (int i = 0; i < uri.size(); i++) {
            String[] hostAndPort = uri.get(i).split(":");
            httpHosts[i] = new HttpHost(hostAndPort[0], Integer.parseInt(hostAndPort[1]), TRANSPORT_PROTOCOL);
        }

        //初始化客户端
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHosts));

        return client;
    }
}
