package com.sandu.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandu.exception.BizException;
import com.sandu.util.MD5;
import com.sandu.util.Utils;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Slf4j
public class Signature {

    /**
     * 获取签名
     * <pre>
     *     eg:
     *       1、加密的数据 = data + timestamp + clientType(顺序不能乱，null的不用处理)
     *       2、MD5
     * </pre>
     *
     * @param requestBody
     * @return
     */
    public static String getSignature(RequestBody requestBody) {
        StringBuilder buf = new StringBuilder();
        buf.append(Objects.toString(requestBody.getData(), Utils.VOID_VALUE));
        buf.append(Objects.toString(requestBody.getTimestamp(), Utils.VOID_VALUE));
        buf.append(Objects.toString(requestBody.getClientType(), Utils.VOID_VALUE));
        return getSignature(buf.toString());
    }

    public static String getSignature(String data) {
        try {
            return MD5.encrypt(data);
        } catch (UnsupportedEncodingException e) {
            throw new BizException(e);
        }
    }

    /**
     * 验签、不区分大小写
     *
     * @param requestBody
     * @return
     */
    public static boolean isSignature(RequestBody requestBody) {
        String encrypt = getSignature(requestBody);
        boolean isSignature = (requestBody.getSign() != null && requestBody.getSign().equalsIgnoreCase(encrypt));
        if (!isSignature) {
            log.error("签名验证失败，sign: {}, encrypt: {}", requestBody.getSign(), encrypt);
        }
        return isSignature;
    }
}
