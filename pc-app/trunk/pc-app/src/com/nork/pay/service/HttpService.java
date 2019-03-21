package com.nork.pay.service;

import com.nork.pay.common.HttpResult;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpService {

    private static class InnerInstance{
        private static final HttpService instance = new HttpService();
    }
    private HttpService(){}
    public static HttpService ImageLoader(){
        return InnerInstance.instance;
    }

    private static RequestConfig config;


    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url, List<? extends Header> headers) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);
        if (headers != null && !headers.isEmpty()) {
            httpGet.setHeaders((Header[]) headers.toArray());
        }
        // 装载配置信息
        httpGet.setConfig(config);
        // 发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // 判断状态码是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回响应体的内容
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }

        return null;
    }

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, String> map, List<? extends Header> headers) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (map != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, String> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }

        // 调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString(), headers);

    }

    public static HttpResult doPost(String url, Map<String, String> map, List<? extends Header> headers) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);
        if (headers != null && !headers.isEmpty()) {
            httpPost.setHeaders((Header[]) headers.toArray());
        }

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        List<NameValuePair> list = new ArrayList<>();
        UrlEncodedFormEntity urlEncodedFormEntity = null;
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 构造from表单对象
            urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        //----------判断是否重定向开始
        int code = response.getStatusLine().getStatusCode();
        String newuri="";
        if (code == 302) {

            Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
            newuri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。

            httpPost = new HttpPost(newuri);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");

            httpPost.setConfig(config);
            if (headers != null && !headers.isEmpty()) {
                httpPost.setHeaders((Header[]) headers.toArray());
            }

            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);

            response = httpClient.execute(httpPost);
        }

        //------------重定向结束

        //返回
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

}
