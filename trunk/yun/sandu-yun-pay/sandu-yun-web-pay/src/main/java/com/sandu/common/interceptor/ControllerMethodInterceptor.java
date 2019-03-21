package com.sandu.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.common.util.ForMatJSONStr;
import com.sandu.common.util.Utils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ControllerMethodInterceptor implements MethodInterceptor {
    private final static String CLASS_LOG_PREFIX = "[Controller服务]:";
    private final Logger logger = LogManager.getLogger(ControllerMethodInterceptor.class);
    private final static ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        logger.info(CLASS_LOG_PREFIX + "Before: interceptor name: {}", invocation.getMethod().getName());

        logger.info(CLASS_LOG_PREFIX + "Arguments: {}", invocation.getArguments());

        Object result = invocation.proceed();

        logger.info(CLASS_LOG_PREFIX + "After: result: {}", "true".equals(Utils.getValue("json_fomate", "false").trim()) ? ForMatJSONStr.format(jsonMapper.writeValueAsString(result)) : jsonMapper.writeValueAsString(result));

        return result;
    }

}
