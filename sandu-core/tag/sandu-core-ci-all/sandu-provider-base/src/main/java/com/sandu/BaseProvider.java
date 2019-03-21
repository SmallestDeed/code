package com.sandu;

import com.sandu.websocket.SocketIOUtil;
import io.socket.client.IO;
import io.socket.client.Socket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/26 16:03
 */
@Slf4j
@SpringBootApplication
@ImportResource(value = {"classpath:spring/spring.xml"})
public class BaseProvider {

    private final static ReentrantLock LOCK = new ReentrantLock();
    private final static Condition STOP = LOCK.newCondition();
    @Value("${wss.server.url}")
    private String socketUrl;

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder()
                .sources(BaseProvider.class)
                .web(false)
                .run(args);
        log.info("--- Strart Proovider finish! ---");

        try {
            LOCK.lock();
            STOP.await();
        } catch (InterruptedException e) {
            log.error("{}", e.getLocalizedMessage());
        } finally {
            LOCK.unlock();
        }
    }

    @Bean
    public Socket socket(){
        Socket socket = null;
        try {
            String uuid = UUID.randomUUID().toString().replace("-","");
            socketUrl = socketUrl + "?userSessionId=mobile2b_"+uuid+"&appId=1";
            socket = IO.socket(socketUrl);
            socket.on(Socket.EVENT_CONNECT, args -> {
            }).on(SocketIOUtil.IM_PUSH_MSG_EVENT, args -> {
            }).on(Socket.EVENT_DISCONNECT, args -> {

            });
            socket.connect();
        } catch (Exception e) {
        }
        return socket;
    }

}
