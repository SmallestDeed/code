package com.sandu.test.task;

import com.sandu.api.task.service.CleanDrawResFileTaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/24
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class CleanDrawResFileTaskServiceTests {

    @Autowired
    CleanDrawResFileTaskService cleanDrawResFileTaskService;

    @Test
    public void test() {
        long startup = System.currentTimeMillis();
        cleanDrawResFileTaskService.cleanDrawResFile();
        log.info("处理清除资源耗时：{}", System.currentTimeMillis() - startup);
    }
}
