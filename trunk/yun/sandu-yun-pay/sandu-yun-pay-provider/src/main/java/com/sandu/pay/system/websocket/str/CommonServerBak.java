package com.sandu.pay.system.websocket.str;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@ServerEndpoint(value = "/websocket/common/str/", encoders = { CommonEncoder.class }, decoders = { CommonDecoder.class })
public class CommonServerBak {
	private static Logger logger = Logger.getLogger(CommonServerBak.class);
	private static final List<Session> clients = Collections.synchronizedList(new ArrayList<Session>());
	
	@OnOpen
	public void onOpen(Session session,@PathParam(value = "user")String user) {
		//////System.out.println("Client connected");
		logger.info("Client connected:" + session.getId());
		if( !clients.contains(session) ) {
			clients.add(session);
		}else{
			logger.info("Client back:" + session.getId());
		}
	}


	@OnMessage
	public void onMessage(String message,@PathParam(value = "targetUser")String targetUser) {
		for (Session session : clients) {
				if (session.isOpen()) {
					try {
						long start = System.currentTimeMillis();

					    session.getBasicRemote().sendObject(message);

						long end = System.currentTimeMillis();

						logger.info("@ServerEndpoint(/websocket/common) @onMessage:times=" +  (end- start)
								+",datasize=("+ (StringUtils.isEmpty(message) ? 0 : message.toString().length()) +")"
								+ (message==null?"":message));

					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		}
	}
	
	@OnClose
	public void onClose(Session peer) {
		 clients.remove(peer);
		 logger.info("Connection closed");
	}
}