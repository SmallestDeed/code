package com.sandu.web.resmodel.controller;

import com.sandu.util.ModelTransBalanceHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sandu.util.ModelTransBalanceHelper.READ_TRANS_ACTION;
import static com.sandu.util.ModelTransBalanceHelper.WRITE_TRANS_ACTION;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ResModelControllerTest {


	@Autowired
	private ModelTransBalanceHelper helper;

	@Test
	public void queue() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(5);

		for (int i = 0; i < 5; i++) {
			//
			executorService.submit(
					() -> {
						try {
							Thread thread = Thread.currentThread();
							for (int j = 0; j < 2; j++) {
								String type = j / 2 == 0 ? WRITE_TRANS_ACTION : READ_TRANS_ACTION;
								log.info("type is {}, result is {},---request model....in thread:{}", helper.getMachineIp(type), type, thread.getName());
								Thread.sleep(1000);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
		}
		Thread.sleep(60000);
	}
}