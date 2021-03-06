package com.sandu.amqp.service.impl;

import com.sandu.amqp.common.constant.RabbitExchange;
import com.sandu.amqp.common.constant.RabbitPlatformCode;
import com.sandu.amqp.common.constant.RabbitRoutingKey;
import com.sandu.amqp.common.tool.JsonUtil;
import com.sandu.amqp.entity.queue.DesignPlanMessage;
import com.sandu.amqp.exception.RabbitException;
import com.sandu.amqp.service.RabbitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Rabbit发送消息
 *
 * @date 20180104
 * @auth pengxuangang
 */
@Slf4j
@Service("rabbitService")
public class RabbitServiceImpl implements RabbitService {

    private final static String CLASS_LOG_PREFIX = "Rabbit发送消息:";

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void sendMsg(Object obj) throws RabbitException {

        if (null == obj) {
            log.error(CLASS_LOG_PREFIX + "发送失败,对象为空.");
        }

        //交换器
        String exchange;
        //路由Key
        String routingKey;

        //设计方案
        if (obj instanceof DesignPlanMessage) {
            //分析平台交换器
            exchange = RabbitPlatformCode.ANALYZE + "." + RabbitExchange.EXCHANGE;
            //设计方案路由
            routingKey = RabbitPlatformCode.ANALYZE + "." + RabbitRoutingKey.DESIGN_PLAN;
        } else {
            log.error(CLASS_LOG_PREFIX + "不支持的传输对象!obj:{}.", obj.toString());
            throw new RabbitException(CLASS_LOG_PREFIX + "不支持的传输对象!obj:" + obj.toString() + ".");
        }

        rabbitTemplate.convertAndSend(exchange, routingKey, JsonUtil.toJson(obj), new CorrelationData(UUID.randomUUID().toString()));
    }

}
