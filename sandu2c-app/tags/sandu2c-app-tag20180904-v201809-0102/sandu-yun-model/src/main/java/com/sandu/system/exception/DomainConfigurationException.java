package com.sandu.system.exception;

/**
 * 域名配置异常
 * @date 20171204
 * @auth pengxuangang
 */
public class DomainConfigurationException extends Exception{
    public DomainConfigurationException() {
    }

    public DomainConfigurationException(String message) {
        super(message);
    }

    public DomainConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainConfigurationException(Throwable cause) {
        super(cause);
    }

    protected DomainConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
