package com.sandu.api.base.common.bloom.filter;

import com.sandu.api.base.common.bloom.operator.DataOperator;

public interface BloomFilter {
    boolean exists(String value);

    boolean existsAndPut(String value);

    interface Strategy {
        boolean put(String key, String value, int numHashFunctions, long bits, DataOperator dataOperator);

        boolean mightContain(String key, String value, int numHashFunctions, long bits, DataOperator dataOperator);
    }
}
