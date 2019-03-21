package com.sandu.websocket;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class SocketConfig {

    private Logger logger = LoggerFactory.getLogger(SocketConfig.class);

    @Value("${wss.server.url}")
    private String SOCKETURL;

    @Bean
    public Socket socket(){
        Socket socket = null;
        try {
            String uuid = UUID.randomUUID().toString().replace("-","");
            SOCKETURL = SOCKETURL + "?userSessionId=union_"+uuid+"&appId=12";
            socket = IO.socket(SOCKETURL);
            socket.on(Socket.EVENT_CONNECT, args -> {
                logger.info("success  connect to socket");
            }).on("event", args -> {

            }).on(Socket.EVENT_DISCONNECT, args -> {

            });
            socket.connect();
        } catch (Exception e) {
            logger.error("连接socket失败",e);
        }
        return socket;
    }
}
