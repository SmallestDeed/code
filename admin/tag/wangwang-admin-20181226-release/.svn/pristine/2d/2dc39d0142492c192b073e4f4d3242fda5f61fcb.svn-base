package com.sandu.api.resmodel.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResModelServiceTest {

    @Autowired
    private ResModelService resModelService;

    @Test
    public void delete() throws Exception {
        List<Integer> integers = Arrays.asList(1, 23, 5454, 6, 23, 23, 6, 6);
        System.out.println("pre------------->"+integers);
        integers = integers.stream().distinct().collect(Collectors.toList());
        System.out.println("new------------->"+integers);

    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void save() throws Exception {
    }

    @Test
    public void page() throws Exception {
//        resModelService.listResModel().forEach(System.out::println);
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void queryByExample() {
//        Example example = new Example(ResModel.class);
//        example.createCriteria().andGreaterThan("id", 1600)
//                .andLessThan("id", 2000);
//        resModelService.queryByExample(example).forEach(System.out::println);
    }

    @FunctionalInterface
    interface Bi<T> {
        default void ad(Integer a) {
            System.out.println(a);
        }

        void accept(T t, Integer i, String str);
    }

    public static <T> void forEach(List<T> list, Bi<T> biConsumer) {
        for (T t : list) {
            biConsumer.accept(t, list.indexOf(t), t.toString().substring(0, 1));
            biConsumer.ad(1);
        }
    }

    @Test
    public void queryNameDto() {
        List<String> list = Arrays.asList("dfd", "hello", "java", "python");
        forEach(list, (s, i, str) -> System.out.println(s+i+str));
    }

    public static void main(String[] args) {
        String str = "1";
        Object obj = "1";
        System.out.println(str.equals(obj));
        System.out.println(obj.equals(str));
    }
}