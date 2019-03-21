package com.sandu.common.listener;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class MybatisLogSetListener
 * 
 */
public class MybatisLogSetListener implements ServletContextListener {
	public Logger logger = Logger.getLogger(MybatisLogSetListener.class);
	/**
	 * Default constructor.
	 */
	public MybatisLogSetListener() {

	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 *      设置mybatis默认使用日志为log4j
	 * 
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("MybatisLogSetListener contextInitialized...");
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("MybatisLogSetListener contextDestroyed...");
	}

}
