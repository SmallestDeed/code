package com.nork.base.websocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

@ServerEndpoint(value = "/websocket/test/{client-id}")
public class MyServerEndpoint {
	private static Logger logger = Logger.getLogger(MyServerEndpoint.class);
	private static final List<Session> clients = Collections
			.synchronizedList(new ArrayList<Session>());

	@OnOpen
	public void onOpen(Session session, @PathParam("client-id") String clientId) {
		session.getUserProperties().put(clientId, true);
		clients.add(session);
	}

	@OnMessage
	public void onMessage(String message,
			@PathParam("client-id") String clientId) {
		if (clients == null || clients.size() == 0)
			return;

		for (Session client : clients) {
			if (client == null)
				continue;

			try {
				if (client.isOpen()) {
					String str = clientId + ": " + message;
					//client.setMaxTextMessageBufferSize(str.length());
					client.getAsyncRemote().sendText(str);
					//client.setMaxIdleTimeout(30000);
					//client.getUserProperties().put(clientId, false);
					/*	Session newclient = client;
					onClose(newclient);
					onOpen(newclient,clientId);*/
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


	}

	@OnClose
	public void onClose(Session peer) {
		clients.remove(peer);
	}
}