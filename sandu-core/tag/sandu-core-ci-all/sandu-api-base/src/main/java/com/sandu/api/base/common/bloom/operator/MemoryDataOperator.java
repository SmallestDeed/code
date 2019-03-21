package com.sandu.api.base.common.bloom.operator;


public class MemoryDataOperator implements DataOperator {
    private byte[] bitMap = new byte[0];

    public MemoryDataOperator() {}

    @Override
    public DataOperator init(long bits) {
        long bytes = (long) Math.ceil(bits / 8.0);
        if (bytes > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Array size is to big.");
        }
        bitMap = new byte[(int) bytes];
        return this;
    }

    @Override
    public boolean put(String key, long bit) {
        long index = (long)(bit / 8.0);
        int i = (int)(7 - bit % 8);
        if (index > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Bit size is to big.");
        }
        byte b = bitMap[(int) index];
        byte bt = (byte)(1 << i);
        bitMap[(int) index] = (byte)(b | bt);
        return Math.abs(((b >>> i) % 2)) == 1 ? true : false;
    }

    @Override
    public boolean get(String key, long bit) {
        long index = (long)(bit / 8.0);
        int i = (int)(7 - bit % 8);
        if (index > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Bit size is to big.");
        }
        return Math.abs(((bitMap[(int) index] >>> i) % 2)) == 1 ? true : false;
    }
}
