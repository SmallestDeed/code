package com.sandu.im.service.handlermsg.impl;

import com.sandu.im.service.handlermsg.HandlerMsgProvider;
import com.sandu.im.service.handlermsg.HistoryMessageHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class HnadlerMsgBeanFactory implements ApplicationContextAware {

    public static ApplicationContext applicationContext;


    public static HistoryMessageHandler produce(String produceType) {
        return (HistoryMessageHandler)applicationContext.getBean(produceType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
