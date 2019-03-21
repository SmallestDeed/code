package com.sandu.common.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Jwt {

    /**
     * 秘钥
     */
    private static final byte[] SECRET = "3d990d2276917dfac04467df11fff26d".getBytes();

    /**
     * 初始化head部分的数据为
     * {
     * "alg":"HS256",
     * "type":"JWT"
     * }
     */
    private static final JWSHeader header = new JWSHeader(JWSAlgorithm.HS256, JOSEObjectType.JWT, null, null, null, null, null, null, null, null, null, null, null);

    /**
     * 生成token，该方法只在用户登录成功后调用
     *
     * @param payload Map集合，可以存储用户id，token生成时间，token过期时间等自定义字段
     * @return token字符串, 若失败则返回null
     */
    public static String createToken(Map<String, Object> payload) {
        String tokenString = null;
        // 创建一个 JWS object
        JWSObject jwsObject = new JWSObject(header, new Payload(new JSONObject(payload)));
        try {
            // 将jwsObject 进行HMAC签名
            jwsObject.sign(new MACSigner(SECRET));
            tokenString = jwsObject.serialize();
        } catch (JOSEException e) {
            System.err.println("签名失败:" + e.getMessage());
            e.printStackTrace();
        }
        return tokenString;
    }


    /**
     * 校验token是否合法，返回Map集合,集合中主要包含    state状态码   data鉴权成功后从token中提取的数据
     * 该方法在过滤器中调用，每次请求API时都校验
     *
     * @param token
     * @return Map<String, Object>
     */
    public static Map<String, Object> validToken(String token) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            Payload payload = jwsObject.getPayload();
            JWSVerifier verifier = new MACVerifier(SECRET);

            if (jwsObject.verify(verifier)) {
                JSONObject jsonOBj = payload.toJSONObject();
                // token校验成功（此时没有校验是否过期）
                resultMap.put("state", TokenState.VALID.toString());
                // 若payload包含ext字段，则校验是否过期
                if (jsonOBj.containsKey("ext")) {
                    long extTime = Long.valueOf(jsonOBj.get("ext").toString());
                    long curTime = System.currentTimeMillis();
                    // 过期了
                    if (curTime > extTime) {
                        resultMap.clear();
                        resultMap.put("state", TokenState.EXPIRED.toString());
                    }
                }
                resultMap.put("data", jsonOBj);

            } else {
                // 校验失败
                resultMap.put("state", TokenState.INVALID.toString());
            }

        } catch (Exception e) {
            //e.printStackTrace();
            // token格式不合法导致的异常
            resultMap.clear();
            resultMap.put("state", TokenState.INVALID.toString());
        }
        return resultMap;
    }

    public static void main(String[] args) {
        String toekn = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzaWduZmxhdCI6Im1pbmlfcHJvZ3JhbV91c2VyX3Rva2VuOiIsImV4dCI6MTU1MDg5Mjc1MzcxNiwidWlkIjozNjM0NywidW5hbWUiOiJvLUQ5TjVaNjl2anhzTU9JdW9mUUdTNE5Jeng0IiwidXR5cGUiOjgsImFwcEtleSI6IjE4YWQ3ZTBiOGViMDQ3OThiNGQ4ZTFmMzg2YzY2ZDJiIiwic2Vzc2lvblRpbWVvdXQiOjI1OTIwMH0.PxvPzdjgtd1grXmHX31q9lZIm4xU_Asevq27yshhDJ8";
        Map<String, Object> map = validToken(toekn);
        System.out.println(map);
    }
}
