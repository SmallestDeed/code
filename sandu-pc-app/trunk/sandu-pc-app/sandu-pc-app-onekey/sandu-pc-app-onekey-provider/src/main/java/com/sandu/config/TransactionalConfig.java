package com.sandu.config;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * 
 * @author Sandu
 *
 */
@Configuration
public class TransactionalConfig {

	private static final String PROPAGATION_REQUIRED = "PROPAGATION_REQUIRED,-Throwable";
	private static final String PROPAGATION_REQUIRED_READ = "PROPAGATION_REQUIRED,-Throwable,readOnly";
	private static final String[] REQUIRED_RULE_TRANSACTION = { "insert*", "add*", "update*", "del*", "create*", "auto*" };
	private static final String[] READ_RULE_TRANSACTION = { "select*", "get*", "count*", "find*", "query*","list*"};

	/**
	 * aop
	 * @param platformTransactionManager 自动注入 无需手动
	 * @return
	 */
	@Bean(name = "txAdvice")
	public TransactionInterceptor transactionInterceptor(PlatformTransactionManager transactionManager) {
		TransactionInterceptor interceptor = new TransactionInterceptor();
		Properties properties = new Properties();
		for (String s : REQUIRED_RULE_TRANSACTION) {
			properties.setProperty(s, PROPAGATION_REQUIRED);
		}
		for (String s : READ_RULE_TRANSACTION) {
			properties.setProperty(s, PROPAGATION_REQUIRED_READ);
		}
		interceptor.setTransactionManager(transactionManager);
		interceptor.setTransactionAttributes(properties);
		return interceptor;
	}

	@Bean
    public BeanNameAutoProxyCreator txProxy() {
	    BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
	    creator.setInterceptorNames("txAdvice");
	    creator.setBeanNames("*Service", "*ServiceImpl");
	    creator.setProxyTargetClass(true);
	    return creator;
    }

}
