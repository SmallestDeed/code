package com.nork.system.sms;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.nork.system.sms.httpclient.SmsClient;

/**
 * Created by Administrator on 2016/5/17.
 */
public class SmsClientListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SmsClient.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
