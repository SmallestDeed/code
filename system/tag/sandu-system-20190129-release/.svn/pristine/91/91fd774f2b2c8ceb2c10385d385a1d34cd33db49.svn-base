package com.sandu.queue.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import com.sandu.api.collect.model.RecordMachineInfoOperation;
import com.sandu.constant.ResponseEnum;
import com.sandu.exception.ServiceException;
import com.sandu.queue.SyncMessage;
import com.sandu.queue.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2018/3/22 14:23
 */
@Service("queueService")
@Slf4j
public class QueueServiceImpl implements QueueService {

    private static ObjectMapper JSON = new ObjectMapper();

    /**
     * 连接工厂
     */
    private ConnectionFactory factory;

    /**
     * 连接
     */
    private Connection connection;

    /**
     * 通道
     */
    private Channel channel;

    /**
     * 交换器名
     */
    private String exchangeName;

    /**
     * 交换器类型
     */
    private String exchangeType;

    /**
     * 路由key
     */
    private String routingKey;

    /**
     * 队列名
     */
    private String queueName;

    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {

        final String host = env.getProperty("spring.rabbitmq.host", "127.0.0.1");
        final String username = env.getProperty("spring.rabbitmq.username", "dev");
        final String password = env.getProperty("spring.rabbitmq.password", "");
        final int port = env.getProperty("spring.rabbitmq.port", Integer.class);
        log.debug("RabbitMQ: host={}, username={}, password={}, port={}", host, username, password, port);

        this.factory = new ConnectionFactory();
        this.factory.setHost(host);
        this.factory.setUsername(username);
        this.factory.setPassword(password);
        this.factory.setPort(port);
        try {
            this.connection = this.factory.newConnection();
            this.channel = this.connection.createChannel();
        } catch (IOException e) {
            log.error("{}", e.getMessage());
            throw new ServiceException(ResponseEnum.ERROR, "Do not connection RabbitMQ Server.", e);
        } catch (TimeoutException e) {
            log.error("{}", e.getMessage());
            throw new ServiceException(ResponseEnum.ERROR, "Connection RabbitMQ timeout.", e);
        }
    }

    @PreDestroy
    public void close() {
        try {
            this.channel.close();
            this.connection.close();
        } catch (IOException e) {
            log.error("{}", e.getMessage());
        } catch (TimeoutException e) {
            log.error("{}", e.getMessage());
        }
    }

    @Override
    public boolean send(byte[] messages) {
        try {
            this.channel.exchangeDeclare(this.exchangeName, this.exchangeType, true);
            this.channel.queueDeclare(this.queueName, true, false, false, null);
            this.channel.queueBind(this.queueName, this.exchangeName, this.routingKey);

            this.channel.basicPublish(this.exchangeName, this.routingKey, null, messages);

            log.debug("Success: CHANNELId={}, publishSeqNo={}, content={}",
                    this.channel.getChannelNumber(), this.channel.getNextPublishSeqNo(), new String(messages));
        } catch (IOException e) {
            log.error("{}", e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean sendDeviceInfo(RecordMachineInfoOperation info) {
        this.exchangeName = env.getProperty("spring.rabbitmq.collect.exchange-name");
        this.exchangeType = env.getProperty("spring.rabbitmq.collect.exchange-type");
        this.routingKey = env.getProperty("spring.rabbitmq.collect.routing-key");
        this.queueName = env.getProperty("spring.rabbitmq.collect.queue");
        log.debug("ExchangeName={}, ExchangeType={}, routingKey={}, queue={}",
                this.exchangeName, this.exchangeType, this.routingKey, this.queueName);

        final String body = beanToJson(info);

        if (!Strings.isNullOrEmpty(body)) {
            return send(body.getBytes());
        }

        return false;
    }

    @Override
    public boolean sendSyncSearch(SyncMessage message) {
        this.exchangeName = env.getProperty("spring.rabbitmq.search.exchange-name");
        this.exchangeType = env.getProperty("spring.rabbitmq.search.exchange-type");
        this.routingKey = env.getProperty("spring.rabbitmq.search.routing-key");
        this.queueName = env.getProperty("spring.rabbitmq.search.queue");
        log.info("ExchangeName={}, ExchangeType={}, routingKey={}, queue={}",
                this.exchangeName, this.exchangeType, this.routingKey, this.queueName);

        final String body = beanToJson(message);

        if (!Strings.isNullOrEmpty(body)) {
            return send(body.getBytes());
        }

        return false;
    }

    @Override
    public String fetch() {
        final StringBuilder content = new StringBuilder();
        try {
            this.channel.exchangeDeclare(this.exchangeName, this.exchangeType, true);
            this.channel.queueDeclare(this.queueName, true, false, false, null);

            GetResponse response = null;
            int num = 0;
            while (response == null && num < 5) {
                response = this.channel.basicGet(this.queueName, true);
                if (response != null) {
                    log.debug("The {} num , Get Response: {}", num, response);
                    content.append(new String(response.getBody(), "UTF-8"));
                }

                try {
                    num++;
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            log.debug("Success: CHANNELId={}, publishSeqNo={}, content={}",
                    this.channel.getChannelNumber(), this.channel.getNextPublishSeqNo(), content.toString());
        } catch (IOException e) {
            log.error("{}", e.getMessage());
            return null;
        }

        log.info("content: " + content.toString());

        return content.toString();
    }

    @Override
    public <T> T fetchDeviceInfo(Class<T> cls) {
        this.exchangeName = env.getProperty("spring.rabbitmq.collect.exchange-name");
        this.exchangeType = env.getProperty("spring.rabbitmq.collect.exchange-type");
        this.routingKey = env.getProperty("spring.rabbitmq.collect.routing-key");
        this.queueName = env.getProperty("spring.rabbitmq.collect.queue");
        log.debug("ExchangeName={}, ExchangeType={}, routingKey={}, queue={}",
                this.exchangeName, this.exchangeType, this.routingKey, this.queueName);

        return jsonToBean(fetch(), cls);
    }

    /**
     * Bean 转 json 字符串
     *
     * @param obj
     * @return
     */
    private String beanToJson(Object obj) {
        String json = null;
        if (!Objects.isNull(obj)) {
            try {
                json = JSON.writeValueAsString(obj);
                log.debug("JSON: {}", json);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
        }
        return json;
    }

    /**
     * json 字符串 转 Bean
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    private <T> T jsonToBean(final String json, Class<T> cls) {
        if (!Strings.isNullOrEmpty(json)) {
            try {
                return JSON.readValue(json, cls);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

}
