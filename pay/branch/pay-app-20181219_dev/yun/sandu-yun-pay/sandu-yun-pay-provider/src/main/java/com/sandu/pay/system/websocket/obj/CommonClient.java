package com.sandu.pay.system.websocket.obj;

import com.sandu.user.service.SysUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.Date;
import java.util.ResourceBundle;

@ClientEndpoint(encoders = { CommonEncoder.class }, decoders = { CommonDecoder.class })
public class CommonClient{
	private static Logger logger = Logger.getLogger(CommonClient.class);
	private Session session;
	private WebSocketContainer container;
	private String ws = ResourceBundle.getBundle("app").getString("app.ws.url").trim();

    @Autowired
	private SysUserService sysUserService;
    
	public void init(){
		try {
		
			if(container == null){
		       container = ContainerProvider.getWebSocketContainer();
		    }

	    	if(session == null || !session.isOpen()){
			   session = container.connectToServer(CommonClient.class, URI.create(ws+"/obj"));
			   //session.getUserProperties().put("light", Boolean.TRUE);
			}
		
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	} 
	
	public void send(){

		try {
				init();
				push();
				
		}catch (Exception e) {
			e.printStackTrace();
		}
	} 
		 
	public void push(){
		try {
			         Message message = new Message();
					 message.setMessageDate(new Date());
					session.getBasicRemote().sendObject(message);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
 
}