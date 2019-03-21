package com.nork.design.test;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.nork.common.util.CryptUtils;

public class DesignPlanTest {
	
	/*点击样板房设计方案新增数据*/
	@SuppressWarnings("deprecation")
	private static void HttpPostDataPlanSave() {
		try {

			HttpClient httpclient = new DefaultHttpClient();
			String uri = "http://192.168.1.222:8080/onlineDecorate/small/design/designPlan/planSave.htm";
			HttpPost httppost = new HttpPost(uri);

			// 添加http头信息
			String username = "{\"appKey\":\"1234\",\"token\":\"69040142035889576057784703698284\",\"msgId\":\"login\",\"deviceId\":\"1\"}";

			String author = CryptUtils.encryptBASE64((username).getBytes()).replace("\r\n", "");
			// "your token"
			httppost.addHeader("Authorization", author); // 认证token

			httppost.addHeader("Content-Type", "application/json");

			httppost.addHeader("User-Agent", "imgfornote");

			// http post的json数据格式： {"longinname": "saadmin","passwd": "admin"}

			JSONObject obj = new JSONObject();
			obj.put("templeId", "29");
			//obj.put("password", "1234");

			httppost.setEntity(new StringEntity(obj.toString()));

			HttpResponse response;

			response = httpclient.execute(httppost);

			// 检验状态码，如果成功接收数据
			int code = response.getStatusLine().getStatusCode();

			if (code == 200) {

				String rev = EntityUtils.toString(response.getEntity());

				// 返回json格式：
				// {"success":true,"message":"ok","data":null,"totalSize":0,"totalCount":0,"state":true,"list":null,"result":null,"obj":true}

				JSONObject obj2 = JSONObject.fromObject(rev);
				//////System.out.println("return=" + obj2);

				String success = obj2.getString("success");
				//////System.out.println("success=" + success);

				String message = obj2.getString("message");
				//////System.out.println("message=" + message);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*点击设计方案 设计方案表新增数据*/
	@SuppressWarnings("deprecation")
	private static void HttpPostDataPlanReform() {
		try {

			HttpClient httpclient = new DefaultHttpClient();
			String uri = "http://localhost:8080/onlineDecorate/small/design/designPlan/planReform.htm";
			HttpPost httppost = new HttpPost(uri);

			// 添加http头信息
			String username = "{\"appKey\":\"1234\",\"token\":\"69040142035889576057784703698284\",\"msgId\":\"login\",\"deviceId\":\"1\"}";

			String author = CryptUtils.encryptBASE64((username).getBytes()).replace("\r\n", "");
			// "your token"
			httppost.addHeader("Authorization", author); // 认证token

			httppost.addHeader("Content-Type", "application/json");

			httppost.addHeader("User-Agent", "imgfornote");

			// http post的json数据格式： {"longinname": "saadmin","passwd": "admin"}

			JSONObject obj = new JSONObject();
			obj.put("id", "1");
			//obj.put("password", "1234");

			httppost.setEntity(new StringEntity(obj.toString()));

			HttpResponse response;

			response = httpclient.execute(httppost);

			// 检验状态码，如果成功接收数据
			int code = response.getStatusLine().getStatusCode();

			if (code == 200) {

				String rev = EntityUtils.toString(response.getEntity());

				// 返回json格式：
				// {"success":true,"message":"ok","data":null,"totalSize":0,"totalCount":0,"state":true,"list":null,"result":null,"obj":true}

				JSONObject obj2 = JSONObject.fromObject(rev);
				//////System.out.println("return=" + obj2);

				String success = obj2.getString("success");
				//////System.out.println("success=" + success);

				String message = obj2.getString("message");
				//////System.out.println("message=" + message);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*根据空间名称模糊查询设计方案/个人设计方案*/
	@SuppressWarnings("deprecation")
	private static void HttpPostDataPlanList() {
		try {

			HttpClient httpclient = new DefaultHttpClient();
			String uri = "http://localhost:8080/onlineDecorate/small/design/designPlan/getPlanList.htm";
			HttpPost httppost = new HttpPost(uri);

			// 添加http头信息
			String username = "{\"appKey\":\"1234\",\"token\":\"69040142035889576057784703698284\",\"msgId\":\"login\",\"deviceId\":\"1\"}";

			String author = CryptUtils.encryptBASE64((username).getBytes()).replace("\r\n", "");
			// "your token"
			httppost.addHeader("Authorization", author); // 认证token

			httppost.addHeader("Content-Type", "application/json");

			httppost.addHeader("User-Agent", "imgfornote");

			// http post的json数据格式： {"longinname": "saadmin","passwd": "admin"}

			JSONObject obj = new JSONObject();
//			obj.put("spaceName", "1");//空间名称
			obj.put("userId", "1");//用户id

			httppost.setEntity(new StringEntity(obj.toString()));

			HttpResponse response;

			response = httpclient.execute(httppost);

			// 检验状态码，如果成功接收数据
			int code = response.getStatusLine().getStatusCode();

			if (code == 200) {

				String rev = EntityUtils.toString(response.getEntity());

				// 返回json格式：
				// {"success":true,"message":"ok","data":null,"totalSize":0,"totalCount":0,"state":true,"list":null,"result":null,"obj":true}

				JSONObject obj2 = JSONObject.fromObject(rev);
				//////System.out.println("return=" + obj2);

				String success = obj2.getString("success");
				//////System.out.println("success=" + success);

				String message = obj2.getString("message");
				//////System.out.println("message=" + message);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*查询所有设计方案*/
	@SuppressWarnings("deprecation")
	private static void HttpPostDataDesignPlanList() {
		try {

			HttpClient httpclient = new DefaultHttpClient();
			String uri = "http://localhost:8080/onlineDecorate/small/design/designPlan/designPlanList.htm";
			HttpPost httppost = new HttpPost(uri);

			// 添加http头信息
			String username = "{\"appKey\":\"1234\",\"token\":\"69040142035889576057784703698284\",\"msgId\":\"login\",\"deviceId\":\"1\"}";

			String author = CryptUtils.encryptBASE64((username).getBytes()).replace("\r\n", "");
			// "your token"
			httppost.addHeader("Authorization", author); // 认证token

			httppost.addHeader("Content-Type", "application/json");

			httppost.addHeader("User-Agent", "imgfornote");

			// http post的json数据格式： {"longinname": "saadmin","passwd": "admin"}

			JSONObject obj = new JSONObject();
//			obj.put("spaceName", "1");
//			obj.put("style", "small");

			httppost.setEntity(new StringEntity(obj.toString()));

			HttpResponse response;

			response = httpclient.execute(httppost);

			// 检验状态码，如果成功接收数据
			int code = response.getStatusLine().getStatusCode();

			if (code == 200) {

				String rev = EntityUtils.toString(response.getEntity());

				// 返回json格式：
				// {"success":true,"message":"ok","data":null,"totalSize":0,"totalCount":0,"state":true,"list":null,"result":null,"obj":true}

				JSONObject obj2 = JSONObject.fromObject(rev);
				//////System.out.println("return=" + obj2);

				String success = obj2.getString("success");
				//////System.out.println("success=" + success);

				String message = obj2.getString("message");
				//////System.out.println("message=" + message);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*查询所有设计方案*/
	@SuppressWarnings("deprecation")
	private static void HttpPostDataDesignPlanProductList() {
		try {

			HttpClient httpclient = new DefaultHttpClient();
			String uri = "http://localhost:8080/onlineDecorate/small/design/designPlanProduct/designPlanProductList.htm";
			HttpPost httppost = new HttpPost(uri);

			// 添加http头信息
			String username = "{\"appKey\":\"1234\",\"token\":\"69040142035889576057784703698284\",\"msgId\":\"login\",\"deviceId\":\"1\"}";

			String author = CryptUtils.encryptBASE64((username).getBytes()).replace("\r\n", "");
			// "your token"
			httppost.addHeader("Authorization", author); // 认证token

			httppost.addHeader("Content-Type", "application/json");

			httppost.addHeader("User-Agent", "imgfornote");

			// http post的json数据格式： {"longinname": "saadmin","passwd": "admin"}

			JSONObject obj = new JSONObject();
//			obj.put("planId", "1"); 

			httppost.setEntity(new StringEntity(obj.toString()));

			HttpResponse response;

			response = httpclient.execute(httppost);

			// 检验状态码，如果成功接收数据
			int code = response.getStatusLine().getStatusCode();

			if (code == 200) {

				String rev = EntityUtils.toString(response.getEntity());

				// 返回json格式：
				// {"success":true,"message":"ok","data":null,"totalSize":0,"totalCount":0,"state":true,"list":null,"result":null,"obj":true}

				JSONObject obj2 = JSONObject.fromObject(rev);
				//////System.out.println("return=" + obj2);

				String success = obj2.getString("success");
				//////System.out.println("success=" + success);

				String message = obj2.getString("message");
				//////System.out.println("message=" + message);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
//		HttpPostDataPlanSave();
//		HttpPostDataPlanReform();
//		HttpPostDataPlanList();
//		HttpPostDataDesignPlanList();
		HttpPostDataDesignPlanProductList();
	}

}
