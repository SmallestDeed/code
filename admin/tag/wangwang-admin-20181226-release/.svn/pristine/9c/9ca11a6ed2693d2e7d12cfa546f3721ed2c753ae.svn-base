package com.sandu.util;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
public class ValidRequest {

    private final Map<String, String> cache = new ConcurrentHashMap<>();

    @Pointcut("execution(public **.controller..*Controller.*(..))")
    public void allController() {
    }

    @Before("allController()")
    public void validCommit() {

    }

}
