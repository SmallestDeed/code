package com.sandu.pay.system.websocket.obj;

import com.sandu.common.util.StringUtils;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/24.
 */
@ServerEndpoint(value = "/websocket/common/obj/{model}/{userId}")
public class WebSocketServer {

    private static Logger logger = Logger.getLogger(WebSocketServer.class);
    
    /**
     * 存放 用户ID 的 MAP
     */
    public static Map<String,Session> userMap = Collections.synchronizedMap(new HashMap<String, Session>());

    @OnOpen
    public void onOpen(Session session,@PathParam(value = "model")String model,@PathParam(value = "userId")String userId,@PathParam(value = "type")String type) throws Exception{
    	MessageResponse messageResponse=new MessageResponse();
    	String userKey = model+"_"+userId;
		if(!userMap.containsKey(userKey)){
			userMap.put(userKey, session);
			messageResponse.setMessage("用户: "+userId+" 链接 webSocketServer 成功");
		}
    }


    @OnClose
    public void onClose(@PathParam(value = "userId")String userId,@PathParam(value = "model")String model,@PathParam(value = "userType")String userType,Session session,CloseReason c) {
    	String userKey = model+"_"+userId;
    	userMap.remove(userKey);
    }

    @OnError
    public void onError(@PathParam(value = "userId")String userId,@PathParam(value = "model")String model,@PathParam(value = "userType")String userType,Throwable e,Session session){
    	String userKey = model+"_"+userId;
    	userMap.remove(userKey);
    }
    
     
    /**
     * w e b s o k e t 接收消息
     * @param content
     */
    @OnMessage
    public void onMessage(String content,@PathParam(value = "userId")String userId,@PathParam(value = "model")String model) {
    	
    	if(StringUtils.isBlank(userId)){
    		logger.info("userId"+userId+ "content="+content+" error.....");
    		return; 
    	}	
    	if(StringUtils.isBlank(model)){
    		logger.info("model"+model+ "content="+content+" error.....");
    		return; 
    	}
    	String userKey = model+"_"+userId;
    	Session session=null;
    	if(userMap.containsKey(userKey)){
			   session=userMap.get(userKey);
		}else{
			logger.info("该用户userId："+userId+ "未登录！！！");
		}
    	if(session!=null){
    		try{
    			session.getBasicRemote().sendText(content);
    			logger.info("向用户userId："+userId+ "消息发送成功！！！");
    		}catch(Exception e){
        		e.printStackTrace();
        	}
    	}
    } 
    
    
   
}
