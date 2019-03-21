package com.sandu.common.util;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;

public class HttpClientUtil {
	
	private static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(120000).setSocketTimeout(60000)  
            .setConnectionRequestTimeout(60000).setCookieSpec(CookieSpecs.STANDARD_STRICT).  
                    setExpectContinueEnabled(true).  
                    setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST)).  
                    setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();  
	
	private static SSLConnectionSocketFactory socketFactory;
	
	 public static CloseableHttpResponse getResponse(String uri, Object o) throws IOException {
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	//绕过认证
        try {  
            SSLContext sslContext = SSLContext.getInstance("TLS");  
            sslContext.init(null, new TrustManager[]{truseAllManager}, null);  
            socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (KeyManagementException e) {  
            e.printStackTrace();  
        }  
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
                .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();  
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
        httpclient = HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).build(); 
		
		Gson gson = new Gson();
		
		CloseableHttpResponse response = null; // Variable be returned		
		String requestEntity; // Request entity contents
		HttpEntity entity; // Request entity object
		
		HttpPost httppost = new HttpPost(uri);			
		httppost.setHeader("content-type", "application/json");			
		// Convert Object to JSON			
		requestEntity = gson.toJson(o);			
		entity = new StringEntity(requestEntity);
		httppost.setEntity(entity);			
		response = httpclient.execute(httppost);
		
		return response;
	}
	 
	private static TrustManager truseAllManager = new X509TrustManager(){  
   	  
        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] arg0, String arg1)  
                throws CertificateException {  
        }  
  
        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] arg0, String arg1)  
                throws CertificateException {  
        }  
  
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;  
        }  
          
    }; 
    
    
    //执行批处理文件
    public static boolean callCmd(String locationCmd){
    	String cmd = "cmd /C start /b "+locationCmd + " ";
	    try {
	        //////System.out.println(cmd);
	        Process ps = Runtime.getRuntime().exec(cmd);
	        InputStream inputStream = ps.getInputStream();
	        byte[] by = new byte[1024];
	        while(inputStream.read(by) != -1){
	            if(new String(by,"utf-8").contains("finish!")){
	                break;
	            }
	            //////System.out.println(new String(by,"utf-8"));
	        }
	        inputStream.close();
	    } catch (Exception ioe) {
	        ioe.printStackTrace();
	        return false;
	    }
	    return true;
    }
}
