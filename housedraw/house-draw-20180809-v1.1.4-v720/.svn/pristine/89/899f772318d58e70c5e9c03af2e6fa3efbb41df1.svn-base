package com.sandu.web.redis.controller;

import com.google.common.collect.Lists;
import com.sandu.api.redis.RedisService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/redis")
public class RedisController extends BaseController {

    @Autowired
    RedisService redisService;

    @PostMapping("/clear")
    public ReturnData clear() {
        redisService.clearAllCache();
        return ReturnData.builder().status(true).message("成功");
    }

    @PostMapping("/clear/{key}")
    public ReturnData clear(@PathVariable String key) {
        redisService.clearCacheByKeys(Lists.newArrayList(key));
        return ReturnData.builder().status(true).message("成功");
    }
}
