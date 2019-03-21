package com.sandu.common.cache.utils;

import com.sandu.common.util.ObjectUtils;
import com.sandu.common.util.StringUtils;

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


     /* byte[]型转换Object
	 *
     * @param key
	 * @return  */
    public static Object toObject(byte[] bytes) {
        return ObjectUtils.unserialize(bytes);
    }

}
