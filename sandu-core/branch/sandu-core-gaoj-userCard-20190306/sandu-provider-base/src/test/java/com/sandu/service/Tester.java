package com.sandu.service;

import com.sandu.BaseProvider;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/12/20
 * @since : sandu_yun_1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseProvider.class)
@Transactional
@Rollback
public abstract class Tester {
}
