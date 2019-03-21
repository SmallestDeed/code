package com.sandu.common.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	 private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	/**
	 * 调用http接口
	 * 
	 * @param url
	 * @param paramsMap
	 * @return
	 */
	public static String doPostMethod(String url, Map<String, String> paramsMap,String token) {
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);

			//httppost.addHeader("Platform-Code", platformCode); // 平台编码
			httppost.addHeader("Authorization", token); // token
			httppost.addHeader("Content-Type", "application/json");
			httppost.addHeader("User-Agent", "imgfornote");

			List<NameValuePair> params = new ArrayList<>();
			if (paramsMap != null && paramsMap.size() > 0) {
				for (String key : paramsMap.keySet()) {
					String value = paramsMap.get(key);
					if (StringUtils.isNotBlank(value)) {
						params.add(new BasicNameValuePair(key, value));
					}
				}
			}
			httppost.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse response;
			long start = System.currentTimeMillis();
			response = httpclient.execute(httppost);
			long end = System.currentTimeMillis();

			int code = response.getStatusLine().getStatusCode();
			logger.info("code:"+code);
			if (code == 200) {
				result = EntityUtils.toString(response.getEntity());
				logger.info("result:"+result);
			}
		} catch (Exception e) {
			logger.error("doPostMethod访问异常:",e);
		}
		return result;
	}
	
	  /**
     * 发送 GET 请求（HTTP），不带输入数据
     * @param url
     * @return
     */
    public static String doGetMethod(String url) {
        return doGetMethod(url, new HashMap<String, Object>());
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     * @param url
     * @param params
     * @return
     */
    public static String doGetMethod(String url, Map<String, Object> params) {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;
        String result = null;
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            HttpResponse response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            System.out.println("执行状态码 : " + statusCode);
            int code = response.getStatusLine().getStatusCode();
			logger.info("code:"+code);
			if (code == 200) {
				result = EntityUtils.toString(response.getEntity());
				logger.info("result:"+result);
			}
			
        } catch (IOException e) {
        	logger.error("doGetMethod访问异常:",e);
        }
        return result;
    }
}
