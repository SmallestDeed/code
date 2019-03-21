package com.sandu.analyze;

import com.sandu.amqp.entity.queue.DesignPlanMessage;
import com.sandu.amqp.exception.RabbitException;
import com.sandu.amqp.service.RabbitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnalyzeApplicationTests {

    @Autowired
    RabbitService rabbitService;

    @Test
    public void rabbitServiceTest() throws RabbitException {
        rabbitService.sendMsg(new DesignPlanMessage(1, 2, 3, 4));
    }

}
