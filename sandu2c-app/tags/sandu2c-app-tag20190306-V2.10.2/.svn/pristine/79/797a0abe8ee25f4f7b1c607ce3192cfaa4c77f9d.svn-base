package com.sandu.config;

import com.sandu.amqp.handler.OrderHandler;
import com.sandu.common.constant.RabbitConstant;
import com.sandu.product.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 消息队列配置(RabbitMq)
 *
 * @date 2019/2/15
 * @auth zhangchengda
 */
@Slf4j
@Configuration
public class RabbitConfig {
    private final static String CLASS_LOG_PREFIX = "消息队列配置:";
    @Autowired
    ConnectionFactory connectionFactory;
    @Autowired
    OrderHandler orderHandler;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(new Queue("order.dead.queue", true));
        container.setConnectionFactory(connectionFactory);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setPrefetchCount(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            byte[] bytes = message.getBody();
            try {
                //接收消息
                String receiveMessageStr = new String(bytes, "UTF-8");
                orderHandler.handler(receiveMessageStr);
            } catch (UnsupportedEncodingException e) {
                log.error(CLASS_LOG_PREFIX + "解析消息失败.bytes:" + bytes, e);
            } catch (Exception e) {
                log.error(CLASS_LOG_PREFIX + "消费消息失败.bytes:" + bytes, e);
            } finally {
                //确认消息
                try {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException e) {
                    log.error(CLASS_LOG_PREFIX + "确认消息失败!", e);
                }
            }
        });
        return container;
    }
}
