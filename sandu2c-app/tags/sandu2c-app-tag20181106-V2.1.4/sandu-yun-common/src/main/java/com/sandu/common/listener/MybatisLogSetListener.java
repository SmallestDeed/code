package com.sandu.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class MybatisLogSetListener
 */
public class MybatisLogSetListener implements ServletContextListener {

    public Logger logger = LoggerFactory.getLogger(MybatisLogSetListener.class);

    /**
     * Default constructor.
     */
    public MybatisLogSetListener() {

    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     * 设置mybatis默认使用日志为log4j2
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        logger.info("MybatisLogSetListener contextInitialized...");
        org.apache.ibatis.logging.LogFactory.useLog4J2Logging();
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        logger.info("MybatisLogSetListener contextDestroyed...");
    }

}
