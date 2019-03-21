package com.sandu.service.queue;

import com.sandu.api.collect.model.RecordMachineInfoOperation;
import com.sandu.queue.impl.QueueServiceImpl;
import com.sandu.queue.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-system
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 10/25/2018 4:36 PM
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class QueueServiceTest {

//    @TestConfiguration
//    static class QueueServiceTestConfiguration {
//
//        @Bean
//        public QueueService queueService(){
//            return new QueueServiceImpl();
//        }
//    }

    @Autowired
    QueueService queueService;

    @Test
    public void test1SendDeviceInfo() {
        RecordMachineInfoOperation info = new RecordMachineInfoOperation();
        info.setAppIp("192.168.1.130");
        info.setAppName("随选网");
        info.setAppVersion("v1.0.0");
        info.setUuid("123-456-789");
        info.setRecordType(1);

        boolean result = queueService.sendDeviceInfo(info);

        Assert.state(result, "Don't send device info!");
    }


    @Test
    public void test2FetchDeviceInfo() {
        RecordMachineInfoOperation info = queueService.fetchDeviceInfo(RecordMachineInfoOperation.class);

        log.info("Fetch device info: {}", info);

        Assert.notNull(info, "Don't fetch device info!");
    }

}
