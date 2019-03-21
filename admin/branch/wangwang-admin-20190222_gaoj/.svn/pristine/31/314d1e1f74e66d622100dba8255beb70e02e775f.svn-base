package com.sandu.commons;

import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author by bvvy
 * @date 2018/4/15
 */
public class ListCopyUtils {

    public static <S, T> void copyListProps(List<S> source, List<T> target) {
        for (int i = 0; i < source.size(); i++) {
            BeanUtils.copyProperties(source.get(i), target.get(i));
        }
    }
}
