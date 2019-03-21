package com.sandu.api.base.common.bloom.filter;

import com.sandu.api.base.common.bloom.AbstractBloomFilterBuilder;
import com.sandu.api.base.common.bloom.BloomFilterStrategies;
import com.sandu.api.base.common.bloom.operator.DataOperator;

public class SimpleBloomFilter implements BloomFilter {
    private final int numHashFunctions;
    private final long bits;
    private final DataOperator dataOperator;
    private final String name;

    private SimpleBloomFilter(DataOperator dataOperator, int numHashFunctions, long bits, String name){
        this.numHashFunctions = numHashFunctions;
        this.bits = bits;
        this.dataOperator = dataOperator;
        this.name = name;
    }

    public static AbstractBloomFilterBuilder builder() {
        return new AbstractBloomFilterBuilder() {
            @Override
            public BloomFilter build() {
                long bits = optimalNumOfBits(expectedInsertions, fpp);
                return new SimpleBloomFilter(dataOperator.init(bits), optimalNumOfHashFunctions(expectedInsertions, bits), bits, name);
            }
        };
    }

    @Override
    public boolean exists(String value) {
        return BloomFilterStrategies.MURMUR128_MITZ_64.mightContain(name, value, numHashFunctions, bits, dataOperator);
    }

    @Override
    public boolean existsAndPut(String value) {
        return !BloomFilterStrategies.MURMUR128_MITZ_64.put(name, value, numHashFunctions, bits, dataOperator);
    }
}
