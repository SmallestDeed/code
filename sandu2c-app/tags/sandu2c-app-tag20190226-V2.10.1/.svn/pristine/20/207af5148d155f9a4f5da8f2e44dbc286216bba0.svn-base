package com.sandu.pay.alipay.util;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sandu.base.QRCode;
import com.sandu.pay.alipay.util.httpClient.MyX509TrustManager;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class WXChatRequest {


    public static Logger log = LoggerFactory.getLogger(WXChatRequest.class);

    private static Gson gson = new Gson();

    /*
     * 处理https GET/POST请求
     * 请求地址、请求方法、参数
     * */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = null;
        try {
            //创建SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManager[] tm = {new MyX509TrustManager()};
            //初始化
            sslContext.init(null, tm, new java.security.SecureRandom());
            ;
            //获取SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            //设置当前实例使用的SSLSoctetFactory
            conn.setSSLSocketFactory(ssf);
            conn.connect();
            //往服务器端写内容
            if (null != outputStr) {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    /*
       * 获取微信小程序二维码并返回图片地址
       *
       * */
    public static String getminiqrQr(String accessToken, String absolutePath, QRCode orCode) throws Exception {
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;

        String url = orCode.getQRUrl() + accessToken;
        Map<String, Object> param = new HashMap<>();
        param.put("scene", orCode.getScene());
        param.put("page", orCode.getPage());
        param.put("width", orCode.getWidth());
        param.put("auto_color", orCode.getAutoColor());
        if (!StringUtils.isEmpty(orCode.getLineColor())) {
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> line_color = gson.fromJson(orCode.getLineColor(), type);
            param.put("line_color", line_color);
        }
        log.info("调用生成微信URL接口传参:" + param);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpEntity requestEntity = new HttpEntity(param, headers);
        ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
        log.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
        byte[] result = entity.getBody();
        log.info(Base64.encodeBase64String(result));
        inputStream = new ByteArrayInputStream(result);
        File file = new File(absolutePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        outputStream = new FileOutputStream(file);
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = inputStream.read(buf, 0, 1024)) != -1) {
            outputStream.write(buf, 0, len);
        }
        outputStream.flush();
        if (inputStream != null) {
            inputStream.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }

        return absolutePath;
    }


}
