package com.nork.imPush.init;

import com.nork.common.util.Utils;
import com.nork.imPush.utils.SocketIOUtil;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;

import java.util.UUID;

@Component
public class ImClientInit {

    private Logger logger = LoggerFactory.getLogger(ImClientInit.class);

    private String socketUrl = Utils.getPropertyName("app","wss.server.url","");;

    @Bean
    public Socket socket(){
        Socket socket = null;
        try {
            String uuid = UUID.randomUUID().toString().replace("-","");
            socketUrl = socketUrl + "?userSessionId=mobile2b_"+uuid+"&appId=1";
            socket = IO.socket(socketUrl);
            socket.on(Socket.EVENT_CONNECT, args -> {
                logger.error("connect to socket!!!");
            }).on(SocketIOUtil.IM_PUSH_MSG_EVENT, args -> {

            }).on(Socket.EVENT_DISCONNECT, args -> {

            });
            socket.connect();
        } catch (Exception e) {
            logger.error("连接socket失败",e);
        }
        return socket;
    }
}
