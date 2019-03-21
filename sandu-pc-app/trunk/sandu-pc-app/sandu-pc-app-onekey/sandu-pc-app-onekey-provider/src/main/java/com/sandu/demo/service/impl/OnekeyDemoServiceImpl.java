package com.sandu.demo.service.impl;

import com.sandu.demo.service.OnekeyDemoService;
import org.springframework.stereotype.Service;

@Service("onekeyDemoService")
public class OnekeyDemoServiceImpl implements OnekeyDemoService{

    @Override
    public String test(String context) {
        return context;
    }
}
