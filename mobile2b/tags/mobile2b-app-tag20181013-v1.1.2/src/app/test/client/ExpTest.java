package app.test.client;

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

public class ExpTest {

	@SuppressWarnings("deprecation")
	private static void HttpPostDataLogin() {
		try {

			HttpClient httpclient = new DefaultHttpClient();
			String uri = "http://localhost:8081/onlineDecorate/jsp/login/login.htm";
			HttpPost httppost = new HttpPost(uri);

			// 添加http头信息
			String username = "{\"appKey\":\"nork_app\",\"token\":\"1234\",\"msgId\":\"login\",\"deviceId\":\"ios\"}";

			String author = "";// CryptUtils.encryptBASE64((username).getBytes()).replace("\r\n",
								// "");
			// "your token"
			httppost.addHeader("Authorization", author); // 认证token

			httppost.addHeader("Content-Type", "application/json");

			httppost.addHeader("User-Agent", "imgfornote");

			// http post的json数据格式： {"longinname": "saadmin","passwd": "admin"}

			JSONObject obj = new JSONObject();
			obj.put("account", "nork");
			obj.put("password", "1234");

			httppost.setEntity(new StringEntity(obj.toString()));

			HttpResponse response;
			long start =System.currentTimeMillis();
			response = httpclient.execute(httppost);
			long end = System.currentTimeMillis();
			//////System.out.println("times = " + (end -start));

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

	@SuppressWarnings("deprecation")
	private static void HttpPostDataPlanList() {
		try {

			HttpClient httpclient = new DefaultHttpClient();
			String uri = "http://192.168.1.222:8080/onlineDecorate/jsp/design/designPlan/planSave.htm";
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
			obj.put("templeId", "1");
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

	public static void main(String[] args) {
		HttpPostDataLogin();
//		HttpPostDataPlanList();
	}
}
