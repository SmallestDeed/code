package com.sandu.analyze.queue;

import com.sandu.amqp.entity.queue.DesignPlanMessage;
import com.sandu.analyze.common.constant.rabbit.RabbitQueue;
import com.sandu.analyze.common.tool.JsonUtil;
import com.sandu.analyze.exception.MessageHandleException;
import com.sandu.analyze.queue.handle.DesignplanHandle;
import com.sandu.analyze.queue.handle.ProductHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 消息分发
 *
 * @date 2018/5/3
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
@Slf4j
@Component
public class MessageDistribution {

    private final static String CLASS_LOG_PREFIX = "消息队列消息分发:";

    @Autowired
    DesignplanHandle designplanHandle;
    @Autowired
    ProductHandle productHandle;

    /**
     * 处理分发，解耦平台
     *
     * @param rabbitQueueArr    消息队列
     * @param message           消息体
     */
    public void process(String rabbitQueueArr, String message) {
        log.info(CLASS_LOG_PREFIX + "收到消息:rabbitQueue:{},Message:{}.", rabbitQueueArr, message);

        if (!StringUtils.isEmpty(rabbitQueueArr) && !StringUtils.isEmpty(message)) {

            //队列
            String[] split = rabbitQueueArr.split("\\.");
            //消息平台编码
            String rabbitPlatformCode = split[0];
            //消息队列
            String rabbitQueue = split[1];

            //消息体解析
            switch (rabbitQueue) {
                //设计方案
                case RabbitQueue.DESIGN_PLAN:
                    try {
                        designplanHandle.handle(JsonUtil.fromJson(message, DesignPlanMessage.class));
                    } catch (MessageHandleException e) {
                        log.error(CLASS_LOG_PREFIX + "处理设计方案消息失败! MessageHandleException:{}.", e);
                    } catch (Exception e) {
                        log.error(CLASS_LOG_PREFIX + "处理消息异常，Exception:{},Str:{}.", e, message);
                    }
                    break;
                //产品
                case RabbitQueue.PRODUCT:
                    break;
                default:
                    log.info(CLASS_LOG_PREFIX + "未识别的队列类型,rabbitQueue:{}.", rabbitQueue);
            }
        }

        log.info(CLASS_LOG_PREFIX + "消息分发完成!");
    }
}
