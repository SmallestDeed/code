package com.sandu.jwt;

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
    public static Map<String, Object> resolveToken(String token) {
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
    	//String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzaWduZmxhdCI6InBjXzJiX3VzZXJfdG9rZW46IiwiZXh0IjoxNTIwMTc1NTk0MDY3LCJ1aWQiOjU4MiwibXR5cGUiOiIzIiwidW5hbWUiOiIxODY4MTUyMzAzMiIsInV0eXBlIjozLCJhcHBLZXkiOiJiODVhNDFjNy1jZWZhLTQ1ZjMtOGE0Zi02NjdjMTE3NTg2ODAiLCJ1cGhvbmUiOiIxODY4MTUyMzAzMiIsInVrZXkiOiJiODVhNDFjNy1jZWZhLTQ1ZjMtOGE0Zi02NjdjMTE3NTg2ODAiLCJpYXQiOjE1MjAxMzIzOTQwNjd9.6lI0iyrHWMjef8KP8aoxCQjDoIvgwP3HgLflixE1A58";
    	//String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzaWduZmxhdCI6Im1vYmlsZV8yYl91c2VyX3Rva2VuOiIsImV4dCI6MTUyMjQ2NzMxMzI1NiwidWlkIjo1ODAsIm10eXBlIjo1LCJ1bmFtZSI6IjE3NjAzMDgzOTUxIiwidXR5cGUiOjEsImFwcEtleSI6IjhmMTUyNmUyNjRhZDRjMDQ5Yjg0OGVjMDJmNmFhZThiIiwibWVkaWFUeXBlIjo1LCJzZXNzaW9uVGltZW91dCI6MjU5MjAwLCJ1a2V5IjoiOGYxNTI2ZTI2NGFkNGMwNDliODQ4ZWMwMmY2YWFlOGIiLCJpYXQiOjE1MjIyMDgxMTMyNTZ9._CvHncGfauXL5lhulEPvme8FQCtqQ9MYsBtA9ODK42U";
    	//String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzaWduZmxhdCI6InBjXzJiX3VzZXJfdG9rZW46IiwiZXh0IjoxNTIyNDM2ODI4NDE2LCJ1aWQiOjMwNDgsIm10eXBlIjoiMyIsInVuYW1lIjoiSjAwMTE5RUlVMDAwMiIsInV0eXBlIjoyLCJhcHBLZXkiOiI2OWZlMGU3ZC04ZWI1LTQ0NjMtOTI4OC03MzdlY2JiODcyMmQiLCJzZXNzaW9uVGltZW91dCI6MjU5MjAwLCJ1cGhvbmUiOiIxMzA0OTgzMjIyOSIsInVrZXkiOiI2OWZlMGU3ZC04ZWI1LTQ0NjMtOTI4OC03MzdlY2JiODcyMmQiLCJpYXQiOjE1MjIzOTM2Mjg0MTZ9.Z45RpFm1KgcXFE1Vd6Ga6U427bVs2zgdAAcKXix4oVM";	
    	String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzaWduZmxhdCI6InBjXzJiX3VzZXJfdG9rZW46IiwiZXh0IjoxNTQzOTE5MTg4NTAzLCJ1aWQiOjY1MzM0LCJtdHlwZSI6IjMiLCJ1bmFtZSI6IkMwMDAyNzgwRUlVMDAwMiIsInV0eXBlIjoyLCJhcHBLZXkiOiJlYmJjMmMwOC1kN2ZjLTQ2ZTYtOGZjMC05ZDIzMTRkZWIzMTMiLCJzZXNzaW9uVGltZW91dCI6MjU5MjAwLCJ1cGhvbmUiOm51bGwsInVrZXkiOiJlYmJjMmMwOC1kN2ZjLTQ2ZTYtOGZjMC05ZDIzMTRkZWIzMTMiLCJpYXQiOjE1NDM2NTk5ODg1MDN9.UGs7lcfO0T6ZnR1j54Bg5YG52A0j9iXYUvBOnBjK2JI";
    	resolveToken(token);

	}
}
