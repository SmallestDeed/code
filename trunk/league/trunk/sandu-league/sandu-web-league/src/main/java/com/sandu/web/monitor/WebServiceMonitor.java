package com.sandu.web.monitor;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebServiceMonitor {


    @GetMapping(value = "/running/check")
    public int check() {
        return 200;
    }

}
