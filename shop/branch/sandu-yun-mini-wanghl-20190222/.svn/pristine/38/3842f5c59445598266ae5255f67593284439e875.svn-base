package com.sandu.company.util;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/11/14
 * @since : sandu_yun_1.0
 */
@Getter
public class MappingHelper<K,V> {

    private K key;
    private V value;

    /**
     * Return map from {@link MappingHelper} list.
     * @param list DTO list
     * @param <K> key
     * @param <V> value
     * @return map
     */
    public static <K, V> Map<K, V> toMap(List<MappingHelper<K, V>> list) {
        if (list == null) {
            return Collections.emptyMap();
        }
        return list.parallelStream().collect(Collectors.toMap(MappingHelper::getKey, MappingHelper::getValue));
    }
}
