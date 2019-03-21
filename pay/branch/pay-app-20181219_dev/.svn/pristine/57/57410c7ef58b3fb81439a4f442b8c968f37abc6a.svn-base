package com.sandu.pay.system.websocket.obj;

import com.sandu.common.util.JsonObjectUtil;
import com.sandu.common.util.Utils;
import com.sandu.system.model.SysMessageRecord;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.*;

//@ServerEndpoint(value = "/websocket/common/obj/sysMessageRecord/{user}/{targetUser}", encoders = { CommonEncoder.class }, decoders = { CommonDecoder.class })
public class CommonServer{
	private static Logger logger = Logger.getLogger(CommonServer.class);
	private static List<Session> clients = Collections.synchronizedList(new ArrayList<Session>());
	public static Map<Integer,Session> clientMap = Collections.synchronizedMap(new HashMap<Integer, Session>());

	@OnOpen
	public void onOpen(Session session,@PathParam(value = "user")Integer user) {
		logger.info("ServerEndpoint Connected");
		if( !clientMap.containsKey(user) ) {
			clientMap.put(user, session);
			logger.info(user+"已连接");
		}
		sendMessage(session,user+"已连接");
	}

	@OnMessage
	public void onMessage(String content,@PathParam(value = "user")Integer user,@PathParam(value = "targetUser")Integer targetUser) {
		logger.info("发来的消息："+content);
		logger.info("ServerEndpoint onMessage");
		//for (Session session : clients) {
		for (Map.Entry<Integer,Session> entry : clientMap.entrySet()) {
			Session session = entry.getValue();
			if (session.isOpen()) {
				try {
					long start = System.currentTimeMillis();
					SysMessageRecord message = new SysMessageRecord();
					message.setFromUser(user);
					message.setTargetUser(targetUser);
					message.setContent(content);
					message.setType(1);
					message.setIsRead(1);
					message.setSendTime(Utils.getCurrentDateTime());
					session.getBasicRemote().sendObject(message);

					long end = System.currentTimeMillis();

					logger.info("@ServerEndpoint(/websocket/common/obj/sysMessageRecord) @onMessage:times=" +  (end- start)
							+",datasize=("+ message.toString().length()+")"
							+ (message==null?"":JsonObjectUtil.bean2Json(message)));

				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//}
	}

	@OnClose
	public void onClose(Session session,CloseReason c) {
		logger.info("ServerEndpoint Connection Closed");
		clientMap.remove(session);
	}

	@OnError
	public void onError(Throwable e,Session session){
		//////System.out.println(e.getMessage());
		logger.info(e.getMessage());
		clientMap.remove(session);
	}

	/**
	 * 给客户端实时推送消息
	 * @param session
	 * @param message
	 */
	public static void sendMessage(Session session,String message){
		if( session != null ) {
			try {
				session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}