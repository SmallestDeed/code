package com.sandu.pay.system.websocket.str;

import com.sandu.pay.system.websocket.obj.CommonServer;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/25.
 */
//@ServerEndpoint(value = "/websocket/common/obj/payCallBack/{user}", encoders = { CommonEncoder.class }, decoders = { CommonDecoder.class })
public class PayCallBackServer {

    private static Logger logger = Logger.getLogger(CommonServer.class);
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
    public void onMessage(String content,@PathParam(value = "user")Integer user) {

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
