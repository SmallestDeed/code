package com.sandu.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2018/3/7 16:12
 */
@RestController
public class RunningCheck {

    @GetMapping(value = "/running/check")
    public int check(){
        return 200;
    }
}
