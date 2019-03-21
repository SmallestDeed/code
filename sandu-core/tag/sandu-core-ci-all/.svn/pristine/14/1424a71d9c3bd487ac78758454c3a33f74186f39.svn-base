package com.sandu.api.base.common.bloom;

import com.sandu.api.base.common.bloom.filter.BloomFilter;
import com.sandu.api.base.common.bloom.operator.DataOperator;

public abstract class AbstractBloomFilterBuilder {
    protected String name;
    protected double fpp;
    protected long expectedInsertions;
    protected DataOperator dataOperator;

    public AbstractBloomFilterBuilder operator(DataOperator dataOperator) {
        this.dataOperator = dataOperator;
        return this;
    }

    public AbstractBloomFilterBuilder name(String name) {
        this.name = name;
        return this;
    }

    public AbstractBloomFilterBuilder fpp(double fpp) {
        this.fpp = fpp;
        return this;
    }

    public AbstractBloomFilterBuilder expectedInsertions(long expectedInsertions) {
        this.expectedInsertions = expectedInsertions;
        return this;
    }

    public abstract BloomFilter build();

    protected long optimalNumOfBits(long n, double p) {
        if (p == 0.0D) {
            p = 4.9E-324D;
        }
        return (long)((double)(-n) * Math.log(p) / (Math.log(2.0D) * Math.log(2.0D)));
    }

    protected int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int)Math.round((double)m / (double)n * Math.log(2.0D)));
    }
}
