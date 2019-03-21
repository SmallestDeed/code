package com.sandu.amqp.service;

import com.sandu.amqp.exception.RabbitException;

/**
 * Rabbit发送消息
 *
 * @date 20180104
 * @auth pengxuangang
 */
public interface RabbitService {

    /**
     * 发送信息
     *
     * @param obj 传输对象
     */
    void sendMsg(Object obj) throws RabbitException;
}
