package com.sandu.service.queue.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sandu.constant.ResponseEnum;
import com.sandu.exception.ServiceException;
import com.sandu.api.queue.service.QueueService;
import com.sandu.api.queue.SyncMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
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

        this.exchangeName = env.getProperty("spring.rabbitmq.search.exchange-type");
        this.exchangeType = env.getProperty("spring.rabbitmq.search.exchange-name");
        this.routingKey = env.getProperty("spring.rabbitmq.search.routing-key");
        this.queueName = env.getProperty("spring.rabbitmq.search.queue");
        log.debug("ExchangeName={}, ExchangeType={}, routingKey={}, queue={}",
                this.exchangeName, this.exchangeType, this.routingKey, this.queueName);

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
    public boolean send(SyncMessage sync) {
        try {
            this.channel.exchangeDeclare(this.exchangeName, this.exchangeType, true);
            this.channel.queueDeclare(this.queueName, true, false, false, null);

            final String message = JSON.writeValueAsString(sync);
            log.info("JSON: {}", message);
            this.channel.basicPublish(this.exchangeName, this.routingKey, null, message.getBytes());

            log.info("Success: CHANNELId={}, publishSeqNo={}, content={}",
                    this.channel.getChannelNumber(), this.channel.getNextPublishSeqNo(), message);
        } catch (IOException e) {
            log.error("{}", e.getMessage());
            return false;
        }
        return true;
    }
}
