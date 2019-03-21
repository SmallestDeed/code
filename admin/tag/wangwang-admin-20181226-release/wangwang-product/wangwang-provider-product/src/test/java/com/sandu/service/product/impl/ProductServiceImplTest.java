package com.sandu.service.product.impl;

import com.sandu.api.product.service.ProductService;
import com.sandu.api.restexture.input.ResTextureAdd;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {
    @Autowired
    private ProductService productService;

    @Test
    public void synProductPutawayState() {
        productService.synProductPutawayState();
    }

    @Test
    public void synProductCompanyInfoWithBrandId() {
        productService.synProductCompanyInfoWithBrandId();
    }


    public static <T> void testFix(List<T> list, Consumer<List<T>> consumer) {
        int threadNum = list.size() / 10;
        for (int i = 0; i < threadNum; i++) {
            List<T> collect = list.stream().skip(10 * i).limit(10).collect(Collectors.toList());
            consumer.accept(collect);
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        testFix(list, partList -> {
            executorService.execute(() ->
                    partList.forEach(it ->
                            System.out.println(String.format("Thread name is %s-------->%s", Thread.currentThread().getName(), it))));
        });


    }

}