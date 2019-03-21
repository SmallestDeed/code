package app.test.websocket;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.apache.log4j.Logger;


@ClientEndpoint
public class MyClient {
	private static Logger logger = Logger.getLogger(MyClient.class);

	@OnOpen
	public void onOpen(Session session) {

	}

	@OnMessage
	public void onMessage(String message) {
		logger.info("get message: " + message);
	}

	@OnError
	public void onError(Throwable t) {
		t.printStackTrace();
	}
}
