package com.sandu.test.mail;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.google.common.collect.Lists;
import com.sandu.api.house.service.DrawBakeTaskService;
import com.sandu.api.mail.model.PerfectHouseMailMessage;
import com.sandu.mail.DefaultsFreeMailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailTests {
	
	@Autowired
	TemplateMailService mailService;

	@Autowired
	DrawBakeTaskService drawBakeTaskService;

	@Autowired
	DefaultsFreeMailService defaultsFreeMailService;

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

//	@Value("#{'${mail.bake.fail.subscribe:songjianming@sanduspace.cn,}'.split(',')}")
//	@Value("#{'${mail.bake.fail.subscribe:songjianming@sanduspace.cn}'?:'fffff'}")
	private List<String> targets;

	@Value("#{'${mail.bake.fail.subscribe}'.split(',')}")
	private List<String> targets1;

	@Test
	public void testPushBakeFailMail() throws InterruptedException {
//		drawBakeTaskService.pushBakeFailMail();
//		drawBakeTaskService.resetTaskDetail();

		// 邮件服务
//		defaultsFreeMailService.push(Lists.newArrayList(targets), "线上烘焙失败的任务", "测试邮件发送多人");
//		Thread.sleep(5000);

		log.info("########### {}", targets);
		log.info("########### 111111111 {}", targets1);
	}
}
