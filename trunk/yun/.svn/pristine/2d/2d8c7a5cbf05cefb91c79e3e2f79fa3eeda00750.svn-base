package com.sandu.pay.system.websocket.util;

import com.sandu.common.util.StringUtils;
import com.sandu.pay.system.websocket.obj.WebSocketClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.ResourceBundle;


public class WebSocketUtils {
	private static Logger logger = LogManager.getLogger(WebSocketUtils.class);
	public final static ResourceBundle webSocket = ResourceBundle.getBundle("config/webSocket");

	public static void main(String[] args) throws Exception {
	    //websocket.sanduspace.com
//		sendMessage("wss://114.119.11.236:8001/websocket/common/obj/payOrder/","1549","这是测试数据");
        sendMessage("wss://websocket.ci.sanduspace.com:8001/websocket/common/obj/payOrder/","1549","这是测试数据");
	}
	
	public  static void sendMessage(String webSocketServer, String userId, String message) throws Exception {
			Session serverSession=null;
			//String webSocketServer = webSocket.getString(key);

			if(webSocketServer==null||"".equals(webSocketServer)){
				throw new RuntimeException("sendMessage  not found  webSocketServer = " +webSocketServer +";message:"+message);
			}
			if(StringUtils.isBlank(userId)){
				logger.error("sendMessage  not found  webSocketServer = " +webSocketServer +";userId:"+userId+";message:"+message);
				throw new RuntimeException("sendMessage  not found  webSocketServer = " +webSocketServer +";userId:"+userId+";message:"+message);
			}
			String url =webSocketServer+userId;
			logger.info("websocket发送消息参数："+url);
			WebSocketContainer container= ContainerProvider.getWebSocketContainer();
			if(container!=null){
				serverSession = container.connectToServer(WebSocketClient.class, URI.create(url));
				if(serverSession!=null){
					if(serverSession.getBasicRemote()!=null){
						serverSession.getBasicRemote().sendText(message);
						logger.info("sendMessage success!");
					}else{
						logger.error("websocket send message error："+url+":serverSession acquire faild！is null");
						throw new RuntimeException("websocket send message error："+url+":serverSession acquire faild！is null");
					}
				}else{
					logger.error("websocket send message error："+url+":serverSession acquire error！ is null");
					throw new RuntimeException("websocket send message error："+url+":serverSession acquire error！ is null");
				}
				/*if(serverSession != null){
					serverSession.close();
				}*/
			}else{
				logger.error("websocket send message error："+url+"container  acquire error！ is null");
				throw new RuntimeException("websocket send message error："+url+"container  acquire error！ is null");
			}

	}
}
