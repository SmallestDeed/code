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

//@Service
//@ServerEndpoint(value = "/websocket/common/str/test", encoders = { CommonEncoder.class }, decoders = { CommonDecoder.class }, configurator = GetHttpSessionConfigurator.class)
public class CommonServer{
	private static Logger logger = Logger.getLogger(CommonServer.class);
	private static final List<Session> clients = Collections.synchronizedList(new ArrayList<Session>());
//	private static final Map<Integer,Session> clients = Collections.synchronizedMap(new HashMap<Integer, Session>());

	@OnOpen
	public void onOpen(Session session,@PathParam(value = "user")Integer user) {
		logger.info("server open...");
	}


	@OnMessage
	public void onMessage(String message) {
		logger.info("server onMessage...");
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
	public void onClose(Session peer,@PathParam(value = "user")Integer user) {
		 clients.remove(user);
		 logger.info("Connection closed");
	}

}