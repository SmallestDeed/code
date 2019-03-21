package com.sandu.test.task;

import com.sandu.api.house.bo.DrawBakeTaskBO;
import com.sandu.api.house.service.DrawBakeTaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/31
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DrawBakeTaskTests {

    @Autowired
    DrawBakeTaskService drawBakeTaskService;

    // @Test
    public void test() {
        List<DrawBakeTaskBO> subTasks = drawBakeTaskService.listBakeTask("fix_cupboard", 16L);
        log.info("{}", subTasks);
    }
}
