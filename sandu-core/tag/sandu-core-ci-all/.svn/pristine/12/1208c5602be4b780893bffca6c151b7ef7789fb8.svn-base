package com.sandu.service;

import com.sandu.BaseProvider;
import com.sandu.api.base.common.bloom.filter.BloomFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/12/20
 * @since : sandu_yun_1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseProvider.class)
public abstract class Tester {
    @Resource(name = "memory")
    private BloomFilter bloomFilter;

    @Test
    public void test() {
        int t = 0;
        int f = 0;
        int e = 0;
        for (int i = 0; i < 200000; i++) {
             boolean b = bloomFilter.existsAndPut(i + "c" + i + "b");
             if (b) {
                 t ++;
             }else {
                 f ++;
             }
             if (bloomFilter.exists(i + "c" + i + "b")) {
                 e ++;
             }
           log.info(b + "");
        }
        log.info("true:" + t + "\nfalse:" + f + "\nexist:" + e);
    }
}
