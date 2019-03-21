package com.sandu.amqp.service.impl;


import com.sandu.amqp.RabbitService;
import com.sandu.amqp.model.OrderMessage;
import com.sandu.product.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service("rabbitService")
public class RabbitServiceImpl implements RabbitService {
    private final static String CLASS_LOG_PREFIX = "Rabbit发送消息:";

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void sendExpireMsg(String exchange, String routingKey, Object obj, long time) {
        if (null == obj) {
            log.error(CLASS_LOG_PREFIX + "发送失败,对象为空.");
            throw new RuntimeException(CLASS_LOG_PREFIX + "发送失败,对象为空!");
        }
        if (!(obj instanceof OrderMessage)) {
            log.error(CLASS_LOG_PREFIX + "不支持的传输对象!obj:{}.", obj.toString());
            throw new RuntimeException(CLASS_LOG_PREFIX + "不支持的传输对象!obj:" + obj.toString() + ".");
        }
        rabbitTemplate.convertAndSend(exchange, routingKey, JsonUtil.toJson(obj), message -> {
            message.getMessageProperties().setExpiration(Long.toString(time));
            return message;
        }, new CorrelationData(UUID.randomUUID().toString()));
    }
}
