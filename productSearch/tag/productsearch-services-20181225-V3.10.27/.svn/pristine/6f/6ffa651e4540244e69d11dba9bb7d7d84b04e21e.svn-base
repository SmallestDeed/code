package com.nork.system.websocket.obj;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2016/9/25.
 */
//@ServerEndpoint(value = "/websocket/common/obj/payOrder/{orderId}", encoders = { com.nork.system.websocket.obj.CommonEncoder.class }, decoders = { CommonDecoder.class })
public class PayCallBackServer {

    private static Logger logger = Logger.getLogger(CommonServer.class);
    public static Map<Integer,Session> clientMap = Collections.synchronizedMap(new HashMap<Integer, Session>());

    @OnOpen
    public void onOpen(Session session,@PathParam(value = "orderId")Integer orderId) {
        if( !clientMap.containsKey(orderId) ) {
            clientMap.put(orderId, session);
            logger.info(orderId+"已连接");
         }
    }

    @OnMessage
    public void onMessage(String content,@PathParam(value = "orderId")Integer orderId) {

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
