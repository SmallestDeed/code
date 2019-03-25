package com.sandu.analysis.util;

import javax.sql.DataSource;

import com.sandu.analysis.conf.ConfigurationManager;
import com.sandu.analysis.constant.ConfigConstants;
import com.zaxxer.hikari.HikariDataSource;


public class JdbcUtils {

    private static HikariDataSource datasource = new HikariDataSource();
    
    static {
    	String runEnv = ConfigurationManager.getProperty(ConfigConstants.RUN_ENV);
    	/*//数据库连接信息,必须的
        datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");*/
        //对象连接池中的连接数量配置,可选的
        if(ConfigConstants.RUN_ENV_LOCAL.equals(runEnv)) {
        	datasource.setJdbcUrl(ConfigurationManager.getProperty(ConfigConstants.JDBC_URL_LOCAL));
            datasource.setUsername(ConfigurationManager.getProperty(ConfigConstants.JDBC_USER_LOCAL));
            datasource.setPassword(ConfigurationManager.getProperty(ConfigConstants.JDBC_PASSWORD_LOCAL));            
        }else if(ConfigConstants.RUN_ENV_PROD.equals(runEnv)) {
        	datasource.setJdbcUrl(ConfigurationManager.getProperty(ConfigConstants.JDBC_URL_PROD));
            datasource.setUsername(ConfigurationManager.getProperty(ConfigConstants.JDBC_USER_PROD));
            datasource.setPassword(ConfigurationManager.getProperty(ConfigConstants.JDBC_PASSWORD_PROD));
        }else {
        	throw new RuntimeException("app.property 数据配置不正确!");
        }
    }
    
    
    public static DataSource getDataSource() {
        return datasource;
            
    }
}