package com.sandu.amqp;

public interface RabbitService {
    /**
     * 发送超时消息
     *
     * @param exchange
     * @param routingKey
     * @param obj
     * @param time
     */
    void sendExpireMsg(String exchange, String routingKey, Object obj, long time);
}
