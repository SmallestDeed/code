package com.sandu.pay.wexin.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

public class HttpsRequest implements IServiceRequest {

	/**
	 * URL编码（utf-8）
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param inputData
	 *            提交的数据
	 * 
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String inputData) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyTrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != inputData) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(inputData.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String sendPost(String api_url, Object xmlObj) throws UnrecoverableKeyException, KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException, IOException {
		OutputStream os = null;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer buffer = new StringBuffer();
		HttpsURLConnection connection=null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyTrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(api_url);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setSSLSocketFactory(ssf);

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			// 设置请求方式（GET/POST）
			connection.setRequestMethod("POST");
			//connection.setRequestProperty("Content-Type", "text/xml");
			connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8"); 
			//httpUrlConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 解决XStream对出现双下划线的bug
			XStream xStreamForRequestPostData = new XStream(
					new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

			// 将要提交给API的数据对象转换成XML格式数据Post给API
			String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
			//postDataXML=new String(postDataXML.toString().getBytes(), "ISO8859-1");
			//////System.out.println("postDataXML:"+postDataXML);
			os = connection.getOutputStream();
			// 注意编码格式，防止中文乱码
			os.write(postDataXML.getBytes("UTF-8"));
			//os.write(postDataXML.getBytes());
			os.flush();

			// 将返回的输入流转换成字符串
			is = connection.getInputStream();
			isr = new InputStreamReader(is, "utf-8");
			br = new BufferedReader(isr);

			String str = null;
			while ((str = br.readLine()) != null) {
				buffer.append(str);
			}
			return buffer.toString();
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (is != null) {
				is.close();
				is = null;
			}
			if(connection!=null){
			connection.disconnect();
			}
		}
		return null;
	}
}
