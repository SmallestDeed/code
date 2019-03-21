package com.nork.common.util;

import java.net.URI;
import java.util.ResourceBundle;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.log4j.Logger;

import com.nork.system.websocket.obj.WebSocketClient;


public class WebSocketUtils {
	private static Logger logger = Logger.getLogger(WebSocketUtils.class);
	public final static ResourceBundle webSocket = ResourceBundle.getBundle("config/webSocket");

		
	
		public static void main(String[] a){
			
		}
	
	
	
		public  static void sendMessage(String key,String userId, String message) throws Exception {
				Session serverSession=null;
				String webSocketServer = webSocket.getString(key);
				 
				if(webSocketServer==null||"".equals(webSocketServer)){
					logger.error("sendMessage  not found  webSocketServer = " +webSocketServer +";key:"+key+";message:"+message);
					throw new RuntimeException("sendMessage  not found  webSocketServer = " +webSocketServer +";key:"+key+";message:"+message);
				}
				if(StringUtils.isBlank(userId)){
					logger.error("sendMessage  not found  webSocketServer = " +webSocketServer +";userId:"+userId+";message:"+message);
					throw new RuntimeException("sendMessage  not found  webSocketServer = " +webSocketServer +";userId:"+userId+";message:"+message);
				}
				String url =webSocketServer+userId;
				logger.error("websocket发送消息参数："+url);
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
