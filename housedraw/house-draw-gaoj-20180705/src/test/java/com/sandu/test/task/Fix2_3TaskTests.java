package com.sandu.test.task;

import com.sandu.api.house.service.DrawBakeTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Fix2_3TaskTests {

    @Autowired
    DrawBakeTaskService drawBakeTaskService;

    @Test
    public void testFix2_3Task() {
        drawBakeTaskService.fix2_3Task();
    }
}
