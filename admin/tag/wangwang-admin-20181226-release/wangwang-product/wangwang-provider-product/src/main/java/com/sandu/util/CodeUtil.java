package com.sandu.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author by bvvy
 */
public class CodeUtil {

    public static String formatCode(String starStr, String idstr) {
        if (StringUtils.isBlank(idstr)) {
            idstr = "0";
        }
        idstr = String.valueOf(Integer.parseInt(idstr) + 1);
        int defLen = 10;
        int idLen = idstr.length();
        if (idLen < defLen) {
            StringBuilder zero = new StringBuilder();
            for (int i = 0; i < defLen - idLen; i++) {
                zero.append("0");
            }
            return starStr + zero.toString() + idstr;
        }
        return starStr + idstr;
    }


    public static void main(String[] args) {
        System.out.println(formatCode("M", "4561"));
    }


    public static <T> void fixPartWithList(List<T> list, Integer singleFixNum, Consumer<List<T>> consumer) {
        int threadNum = list.size() / singleFixNum + 1;
        for (int i = 0; i < threadNum; i++) {
            List<T> collect = list.stream().skip(singleFixNum * i).limit(singleFixNum).collect(Collectors.toList());
            if (collect.isEmpty()) {
                return;
            }
            consumer.accept(collect);
        }
    }
}
