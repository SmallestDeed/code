package com.nork.common.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletRegistration.Dynamic;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static Logger logger = LoggerFactory.getLogger(DispatcherServletInitializer.class);

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void customizeRegistration(Dynamic registration) {
        // TODO Auto-generated method stub
        super.customizeRegistration(registration);
    }
}
