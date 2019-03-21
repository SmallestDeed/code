package app.test.dubbo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import app.test.dubbo.model.User;

public class RestConsumer {

	public static void main(String[] args) {
		RestConsumer restConsumer = new RestConsumer();
		restConsumer.testRest();
	}

	void testRest() {
		 User user = new User();
		 user.setName("root");
		 user.setAge(35);
		 Client client = ClientBuilder.newClient();
		 WebTarget target = client.target("http://127.0.0.1:48080/app4/demo/user.json");
		 Response response = target.request().post(Entity.entity(user,MediaType.APPLICATION_JSON_TYPE));
		 try {
			 if (response.getStatus() != 200) {
				 throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
			 }
			 //System.out.println("The result_json is: " + response.readEntity(String.class));
		 } catch (Exception e) {
			 e.printStackTrace();
		 }finally {
			 response.close();
			 client.close(); // 在真正开发中不要每次关闭client，比如HTTP长连接是由client持有的
		 }
	}
}
