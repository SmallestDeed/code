package com.nork.system.sms.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.*;

import com.nork.common.util.Utils;
import com.nork.product.common.PlatformConstants;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientUtil {
    private static Logger logger = Logger.getLogger(HttpClientUtil.class);
    private static HttpClient client = null;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 60000;

    // 构造单例
    private HttpClientUtil() {

        MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        // 默认连接超时时间
        params.setConnectionTimeout(5000);
        // 默认读取超时时间
        params.setSoTimeout(60000);
        // 默认单个host最大连接数
        params.setDefaultMaxConnectionsPerHost(100);// very important!!
        // 最大总连接数
        params.setMaxTotalConnections(256);// very important!!
        httpConnectionManager.setParams(params);

        client = new HttpClient(httpConnectionManager);
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
        //client.getParams().setConnectionManagerTimeout(3000);
//	 client.getParams().setIntParameter("http.socket.timeout", 10000);
//	 client.getParams().setIntParameter("http.connection.timeout", 5000);
    }

    private static class ClientUtilInstance {
        private static final HttpClientUtil ClientUtil = new HttpClientUtil();
    }

    public static HttpClientUtil getInstance() {
        return ClientUtilInstance.ClientUtil;
    }

    /**
     * 发送http GET请求，并返回http响应字符串
     *
     * @param urlstr 完整的请求url字符串
     * @return
     */
    public String doGetRequest(String urlstr) {
        String response = "";
//		urlstr = "http://localhost:8080/timeSpace/online/web/system/sysDictionary/listAll.htm";
        HttpMethod httpmethod = new GetMethod(urlstr);
        try {
            int statusCode = client.executeMethod(httpmethod);
            InputStream _InputStream = null;
            if (statusCode == HttpStatus.SC_OK) {
                _InputStream = httpmethod.getResponseBodyAsStream();
            }
            if (_InputStream != null) {
                response = GetResponseString(_InputStream, "UTF-8");
            }
        } catch (HttpException e) {
            logger.error("获取响应错误，原因：" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("获取响应错误，原因1：" + e.getMessage());
            e.printStackTrace();
        } finally {
            httpmethod.releaseConnection();

        }
        return response;
    }

    /**
     * @param _InputStream
     * @param Charset
     * @return
     */
    public String GetResponseString(InputStream _InputStream, String Charset) {
        String response = "";
        try {
            if (_InputStream != null) {
                StringBuffer buffer = new StringBuffer();
                InputStreamReader isr = new InputStreamReader(_InputStream, Charset);
                Reader in = new BufferedReader(isr);
                int ch;
                while ((ch = in.read()) > -1) {
                    buffer.append((char) ch);
                }
                response = buffer.toString();
                buffer = null;
            }
        } catch (Exception e) {
            logger.error("获取响应错误，原因：" + e.getMessage());
            response = response + e.getMessage();
            e.printStackTrace();
        }
        return response;
    }

    /**
     * post方式提交
     *
     * @param apiUrl  接口url地址
     * @param params  参数map
     * @param headMap 头部信息map
     * @return
     */
    public static String doPost(String apiUrl, Map<String, Object> params, Map<String, Object> headMap) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            if (null != headMap && headMap.size() > 0) {
                for (String key : headMap.keySet()) {
                    httpPost.addHeader(key, String.valueOf(headMap.get(key)));
                }
            }
            httpPost.setConfig(requestConfig);
            if (null != params && params.size() > 0) {
                List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                    pairList.add(pair);
                }
                httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            }
            logger.debug("远程访问接口参数：" + apiUrl);
            long startTime = System.currentTimeMillis();
            response = httpClient.execute(httpPost);
            long endTime = System.currentTimeMillis();
            logger.debug("远程访问接口需要时间：" + (endTime - startTime) + "毫秒");
            System.out.println(response.toString());
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (HttpHostConnectException hhce) {

        } catch (IOException e) {

        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    public static void main(String[] args) {
        String Authorization = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE1MTM5MTUwMjI5MzAsInNpZ25mbGF0IjoidXNlcl9INVRva2VuOiIsInVpZCI6NCwidW5hbWUiOiIxODAyNTQ5NDA4NyIsInV0eXBlIjoxLCJhcHBLZXkiOiIxMWQ2ZTZiMDNhODc0NzQ1YTY2MTljNjU3MTkyYzAwZSIsImlhdCI6MTUxMzkxMTQyMjkzMH0.CRfhBwv4JJ3xZpNQ2_nZVsRjWEsdDcR49PeolyoNR2Y";
        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("Authorization", Authorization);
        headMap.put(PlatformConstants.PLATFORM_CODE_KEY,PlatformConstants.PC_2B);
        String url = ResourceBundle.getBundle("app").getString("pay.server.url") + "/web/pay/payModelConfig/addGiveGroopRef";
        logger.info("url:" + url);
        String result = HttpClientUtil.doPost(url,null,headMap);
        logger.info("http调用赠送包年包月的接口返回结果是：" + result);
        System.out.println("结果是：" + result);
    }
}
