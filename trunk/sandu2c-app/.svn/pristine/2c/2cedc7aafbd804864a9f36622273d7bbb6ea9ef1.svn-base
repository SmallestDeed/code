package com.sandu.web.websocket;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:43 2018/6/8 0008
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weisheng
 * @Title:
 * @Package
 * @Description:
 * @date 2018/6/8 0008AM 10:43
 */
@Slf4j
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {

    private static Gson gson = new Gson();

    private Session session;
    private static Map<Integer, Session> sessionPool = new HashMap<>();
    private static Map<String, Integer> sessionIds = new HashMap<>();

    //连接客户端创建session
    @OnOpen
    public void open(Session session) {
        log.info("连接websocket客户端创建session开始");
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            onClose();
        }
        this.session = session;
        sessionPool.put(loginUser.getId(), session);
        sessionIds.put(session.getId(), loginUser.getId());
        log.info("连接websocket客户端创建session完成");
    }


    //接受客户端消息
    @OnMessage
    public void onMessage(String message) {

    }


    //关闭与客户端连接
    @OnClose
    public void onClose() {
        sessionPool.remove(sessionIds.get(session.getId()));
        sessionIds.remove(session.getId());
        log.info("socket关闭连接");
    }


    //发生错误时触发
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("socket连接异常,关闭连接",error);
        error.printStackTrace();
    }


    //向客户端发送消息
    public static void sendMessage(Object obj, Integer userId) {
        String message = gson.toJson(obj);
        Session s = sessionPool.get(userId);
        if (s != null) {

            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    //获取客户端连接数
    public static int getOnlineNum(){
        return sessionPool.size();
    }

    //获取所有客户端信息
    public static String getOnlineUsers(){
        StringBuffer users = new StringBuffer();
        for(String key : sessionIds.keySet()){
            users.append(sessionIds.get(key)+",");
        }
        return users.toString();
    }




    //向所有连接的客户端发送消息
    public static void sendAll(String msg){
        for(String key : sessionIds.keySet()){
            sendMessage(msg,sessionIds.get(key));
        }

    }
}





































