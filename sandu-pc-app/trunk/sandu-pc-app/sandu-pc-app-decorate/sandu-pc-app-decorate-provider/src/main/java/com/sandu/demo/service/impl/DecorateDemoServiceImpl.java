package com.sandu.demo.service.impl;

import org.springframework.stereotype.Service;

import com.sandu.demo.service.DecorateDemoService;

@Service("decorateDemoService")
public class DecorateDemoServiceImpl implements DecorateDemoService {

    @Override
    public String test(String context) {
        return context;
    }
}
