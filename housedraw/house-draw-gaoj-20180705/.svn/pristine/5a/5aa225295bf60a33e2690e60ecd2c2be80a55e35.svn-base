package com.sandu.test.mail;

import java.util.concurrent.CountDownLatch;

import com.sandu.api.house.service.DrawBakeTaskService;
import com.sandu.api.mail.model.PerfectHouseMailMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sandu.api.mail.service.TemplateMailService;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年4月3日
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailTests {
	
	@Autowired
	TemplateMailService mailService;

	@Autowired
	DrawBakeTaskService drawBakeTaskService;

//	@Test
	public void testSendSimpleMail() throws Exception {
		
		int threadCount = 5;
		CountDownLatch latch = new CountDownLatch(threadCount);
		for (int i = 0; i < threadCount; i++) {
			int idx = i;
			new Thread(() -> {
				PerfectHouseMailMessage msg = new PerfectHouseMailMessage(new String[] { idx + "", idx + "" }, idx + "");
				latch.countDown();
				mailService.push(msg);
			}).start();
		}
		
		latch.await();
		Thread.sleep(10000);
	}

	@Test
	public void testPushBakeFailMail() throws InterruptedException {
//		drawBakeTaskService.pushBakeFailMail();
		drawBakeTaskService.resetTaskDetail();
		Thread.sleep(5000);
	}
}
