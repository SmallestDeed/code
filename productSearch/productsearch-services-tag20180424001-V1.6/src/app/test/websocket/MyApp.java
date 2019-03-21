package app.test.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class MyApp {

	public Session session;

	protected void start() {

		WebSocketContainer container = ContainerProvider
				.getWebSocketContainer();

//		String uri = "ws://localhost:8080/onlineDecorate//websocket/common/obj/sysMessageRecord/1/90";
//		String uri = "ws://120.76.247.82:9080/app/websocket/common/obj/message/2";
//		String uri = "ws://120.77.23.15:9080/app/websocket/common/obj/message/2";
		String uri = "ws://120.77.23.15:9080/app/websocket/common/obj/payOrder/2";
		
		//////System.out.println("Connecting to " + uri);
		try {
			session = container
					.connectToServer(MyClient.class, URI.create(uri));
		} catch (DeploymentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		MyApp client = new MyApp();
		client.start();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			do {
				input = br.readLine();
				if (!input.equals("exit"))
					//client.session.getBasicRemote().getSendWriter().flush();
					client.session.getBasicRemote().sendText(input);
					

			} while (!input.equals("exit"));

		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}