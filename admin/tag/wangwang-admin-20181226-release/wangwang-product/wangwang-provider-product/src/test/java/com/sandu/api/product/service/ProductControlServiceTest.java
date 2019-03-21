package com.sandu.api.product.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ProductControlServiceTest {

    @Autowired
    private ProductControlService productControlService;
    @Test
    public void batchChangeProductSecrecy() {
        List<Integer> ids = new ArrayList<>();
        ids.add(124);
        ids.add(467);
        productControlService.batchChangeProductSecrecy(ids,1);



    }

    static private ThreadFactory proPool = new ThreadFactoryBuilder().setNameFormat("product biz server pool").build();
    static private final ThreadPoolExecutor executorService =
            new ThreadPoolExecutor(
                    30,
                    60,
                    30,
                    TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(512),
                    proPool,
                    new ThreadPoolExecutor.AbortPolicy()
            );

    @Test
    public void saveProductControl() {
    }

    @Test
    public void saveProductControlList() {
    }

    @Test
    public void batchUpdate() {
    }

    public static void main(String[] args) {

        int fixCount = 8;
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            ids.add(i);
            //
        }

        int i = ids.size() / 8 + 1;
        CountDownLatch doneSignal = new CountDownLatch(i);
        for (int j = 0; j < i; j++) {
            int finalJ = j;
            Future<?> submit =
                    executorService.submit(
                            () -> {
                                ids.stream()
                                        .skip(finalJ * fixCount)
                                        .limit(fixCount)
                                        .forEach(
                                                it -> {
                                                    System.out.println(Thread.currentThread().getName() + "---------" + it);
                                                });
                                doneSignal.countDown();
                            });

        }
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("it's done!!!");

        //
    }
}