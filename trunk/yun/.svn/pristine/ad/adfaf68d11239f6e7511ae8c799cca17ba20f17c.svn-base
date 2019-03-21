package com.sandu.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * yun
 *
 * @author Yoco (yocome@gmail.com)
 * @datetime 2017/11/19 22:19
 */
@Configuration
@EnableTransactionManagement
public class MySqlConfig implements EnvironmentAware {

    private String url;
    private String username;
    private String password;

    private int druidPoolInit = 3;
    private int druidPoolMin = 3;
    private int druidPoolMax = 20;

    @Override
    public void setEnvironment(Environment environment) {
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(environment, "jdbc.");
        this.url = resolver.getProperty("url");
        this.username = resolver.getProperty("username");
        this.password = resolver.getProperty("password");


    }

    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);

        dataSource.addFilters("config");

        //配置连接池
        dataSource.setInitialSize(druidPoolInit);
        dataSource.setMinIdle(druidPoolMin);
        dataSource.setMaxActive(druidPoolMax);
//        dataSource.setDefaultAutoCommit(false);

        try {
            //配置监控统计拦截的filters，wall用于防止sql注入，stat用于统计分析
            dataSource.setFilters("wall,stat");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setUseGeneratedKeys(true);
        configuration.setUseColumnLabel(true);
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
      // bk  sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:com/sandu/pay/*/mapper/*.xml"));
        Resource[] r1 = resolver.getResources("classpath*:com/sandu/pay/*/mapper/*.xml");
        Resource[] r2 = resolver.getResources("classpath*:com/sandu/gateway/pay/trade/mapper/*.xml");
        Resource[] r3 = resolver.getResources("classpath*:com/sandu/gateway/pay/config/mapper/*.xml");
        Resource[] r = new Resource[r1.length + r2.length + r3.length];
        System.arraycopy(r1, 0, r, 0, r1.length);
        System.arraycopy(r2, 0, r, r1.length, r2.length);
        System.arraycopy(r3, 0, r, (r1.length+r2.length), r3.length);
        
        sqlSessionFactoryBean.setMapperLocations(r);
        
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        //置扫描Dao接口包,动态实现DAO接口,注入到spring容器
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        //bk mapperScannerConfigurer.setBasePackage("com.sandu.pay.*.dao");
        mapperScannerConfigurer.setBasePackage("com.sandu.pay.*.dao,com.sandu.gateway.pay.trade.dao,com.sandu.gateway.pay.config.dao");
        return mapperScannerConfigurer;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }
}
