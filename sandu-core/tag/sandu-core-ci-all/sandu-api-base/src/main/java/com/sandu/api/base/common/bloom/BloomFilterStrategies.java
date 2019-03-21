package com.sandu.api.base.common.bloom;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;
import com.sandu.api.base.common.bloom.filter.BloomFilter;
import com.sandu.api.base.common.bloom.operator.DataOperator;

public enum BloomFilterStrategies implements BloomFilter.Strategy {
    MURMUR128_MITZ_64 {
        public boolean put(String key, String value, int numHashFunctions, long bits, DataOperator dataOperator) {
            byte[] bytes = Hashing.murmur3_128().hashObject(value, (k, t) -> t.putString(k, Charsets.UTF_8)).asBytes();
            long hash1 = this.lowerEight(bytes);
            long hash2 = this.upperEight(bytes);
            boolean bitsChanged = false;
            long combinedHash = hash1;
            for(int i = 0; i < numHashFunctions; ++i) {
                bitsChanged |= !dataOperator.put(key, (combinedHash & 9223372036854775807L) % bits);
                combinedHash += hash2;
            }
            return bitsChanged;
        }

        public boolean mightContain(String key, String value, int numHashFunctions, long bits, DataOperator dataOperator) {
            byte[] bytes = Hashing.murmur3_128().hashObject(value, (k, t) -> t.putString(k, Charsets.UTF_8)).asBytes();
            long hash1 = this.lowerEight(bytes);
            long hash2 = this.upperEight(bytes);
            long combinedHash = hash1;
            for(int i = 0; i < numHashFunctions; ++i) {
                if (!dataOperator.get(key, (combinedHash & 9223372036854775807L) % bits)) {
                    return false;
                }
                combinedHash += hash2;
            }
            return true;
        }

        private long lowerEight(byte[] bytes) {
            return Longs.fromBytes(bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]);
        }

        private long upperEight(byte[] bytes) {
            return Longs.fromBytes(bytes[15], bytes[14], bytes[13], bytes[12], bytes[11], bytes[10], bytes[9], bytes[8]);
        }
    };
}
