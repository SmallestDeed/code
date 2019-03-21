package com.sandu.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * demo
 *
 * @author Yoco (yocome@gmail.com)
 * @datetime 2017/12/11 22:36
 */

@Configuration
@EnableTransactionManagement
public class MySqlConfig implements EnvironmentAware {

	private String url;
	private String username;
	private String password;

	@Override
	public void setEnvironment(Environment environment) {
		RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(environment, "spring.datasource.");

		this.url = resolver.getProperty("url");
		this.username = resolver.getProperty("username");
		this.password = resolver.getProperty("password");
	}

	/**
	 * 数据库源
	 *
	 * @return
	 */
	@Bean(initMethod = "init", destroyMethod = "close")
	public DruidDataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(this.url);
		dataSource.setUsername(this.username);
		dataSource.setPassword(this.password);

		// 配置连接池
		dataSource.setInitialSize(3);
		dataSource.setMinIdle(3);
		dataSource.setMaxActive(30);
		// 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，
		// 执行validationQuery检测连接是否有效
		// v1.1.0 解决 Mysql Communications link failure问题
		dataSource.setTestWhileIdle(true);

		try {
			// 配置监控统计拦截的filters，wall用于防止sql注入，stat用于统计分析
			dataSource.setFilters("wall,stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dataSource;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);

		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setUseGeneratedKeys(true);
		configuration.setUseColumnLabel(true);
		// 支持驼峰转换
		configuration.setMapUnderscoreToCamelCase(true);
		sqlSessionFactoryBean.setConfiguration(configuration);

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:com/sandu/service/**/mapper/*.xml"));

		return sqlSessionFactoryBean;
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		// 置扫描Dao接口包,动态实现DAO接口,注入到spring容器
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
		mapperScannerConfigurer.setBasePackage("com.sandu.service.**.dao");

		return mapperScannerConfigurer;
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
