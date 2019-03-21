package com.sandu.queue;

import com.sandu.api.queue.SyncMessage;
import com.sandu.api.queue.service.QueueService;
import com.sandu.constant.Constants;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2018/3/24 13:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QueueServiceTest {

    @Autowired
    private QueueService queueService;

    @Test
    public void testSend() {
        SyncMessage message = new SyncMessage();
        message.setMessageId("P-" + System.currentTimeMillis());
        message.setAction(SyncMessage.ACTION_ADD);
        message.setModule(SyncMessage.MODULE_PRODUCT);
        message.setObject(Arrays.asList(123456));
        message.setPlatformType(Constants.PLATFORM_CODE_MERCHANT_MANAGE);
        boolean result = queueService.send(message);

        result = queueService.send(message);
        result = queueService.send(message);

        Assert.isTrue(result, "Can not send!");
    }
}
