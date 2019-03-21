package com.sandu.config;

import java.sql.SQLException;

import com.sandu.cat.CatMybatisPlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2017/12/15 10:12
 */
@Configuration
@EnableTransactionManagement
public class MySqlConfig implements EnvironmentAware {

    private String url;
    private String username;
    private String password;

    private int druidPoolInit = 5;
    private int druidPoolMin = 5;
    private int druidPoolMax = 20;

    @Override
    public void setEnvironment(Environment environment) {
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
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
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean registration = new ServletRegistrationBean();
        registration.setServlet(new StatViewServlet());
        registration.addUrlMappings("/druid/*");
        registration.addInitParameter("loginUsername", "sandu");
        registration.addInitParameter("loginPassword", "druid");
        registration.addInitParameter("allow", "192.168.1.0/24,192.168.2.0/24,192.168.3.0/24,127.0.0.1");
        return registration;
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

        //设置CAT插件
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{new CatMybatisPlugin()});

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.sandu.*.mapper");
        sqlSessionFactoryBean.setMapperLocations(
                resolver.getResources("classpath*:com/sandu/service/*/mapper/*.xml"));

        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        //置扫描Dao接口包,动态实现DAO接口,注入到spring容器
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("com.sandu.service.*.dao");

        return mapperScannerConfigurer;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }
}
