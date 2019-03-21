package com.sandu.common.util;

public class JedisUtils {

    /**
     * 获取byte[]类型Key
     *
     * @param key
     * @return
     */
    public static byte[] getBytesKey(Object object) {
        if (object instanceof String) {
            return StringUtils.getBytes((String) object);
        } else {
            return ObjectUtils.serialize(object);
        }
    }

    /**
     * Object转换byte[]类型
     *
     * @param key
     * @return
     */
    public static byte[] toBytes(Object object) {
        return ObjectUtils.serialize(object);
    }

}
