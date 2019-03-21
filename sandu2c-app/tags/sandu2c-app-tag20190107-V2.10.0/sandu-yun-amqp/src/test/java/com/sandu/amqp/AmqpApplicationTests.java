package com.sandu.amqp;

import com.sandu.amqp.common.constant.ActionType;
import com.sandu.amqp.common.constant.PlatformType;
import com.sandu.amqp.entity.queue.DesignPlanMessage;
import com.sandu.amqp.exception.RabbitException;
import com.sandu.amqp.service.RabbitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmqpApplicationTests {

    @Autowired
    RabbitService rabbitService;

    @Test
    public void contextLoads() throws RabbitException{

        for (int i = 0; i < 15; i++) {
            DesignPlanMessage designPlanMessage = new DesignPlanMessage();
            designPlanMessage.setPlatformType(PlatformType.PLATFORM_CODE_CITY_UNION);
            designPlanMessage.setActionType(ActionType.COLLECT);
            designPlanMessage.setDesignPlanId(1000 + i);
            designPlanMessage.setUserId(3323);
            rabbitService.sendMsg(designPlanMessage);
        }
    }

    public static Date getDay(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        date = calendar.getTime();
        return date;
    }

}
