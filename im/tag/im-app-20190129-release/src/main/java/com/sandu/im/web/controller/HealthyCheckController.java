package com.sandu.im.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthyCheckController {

	@GetMapping(value = "/running/check")
    public int check() {
        return 200;
    }
}
