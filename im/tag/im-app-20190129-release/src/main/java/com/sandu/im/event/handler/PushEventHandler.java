package com.sandu.im.event.handler;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.sandu.im.cache.CacheInstances;
import com.sandu.im.common.util.GsonUtil;
import com.sandu.im.event.entity.LocMessage;
import com.sandu.im.event.entity.PushMessage;

@Component
public class PushEventHandler     
{  
	private static final Logger logger = LoggerFactory.getLogger(PushEventHandler.class);
	
	private static final Integer[] APP_IDS = new Integer[] {LocMessage.APP_ID_MOBILE2B,LocMessage.APP_ID_PC2B,LocMessage.APP_ID_MINI_SD};
	
	protected SocketIOServer server;
	 
	
    @Autowired  
    public PushEventHandler(SocketIOServer server)   
    {  
        this.server = server ;
    }
    

    @OnEvent(value = "im_push_msg_event")  
    public void onEvent(SocketIOClient client, AckRequest request, Object obj) throws UnsupportedEncodingException   
    {
    	PushMessage data = null;
    	if(obj!=null) {
    		if(obj instanceof String) {
    			data = GsonUtil.fromJson(obj.toString(), PushMessage.class);
    		}else {
    			data = GsonUtil.fromJson(GsonUtil.toJson(obj), PushMessage.class);
    		}
    		if(data!=null) {
    			logger.info("收接数据:"+data.toString());
            	for(int i=0;i<APP_IDS.length;i++) { 
            		UUID targetId = CacheInstances.CHAT_SESSION_MAPPING_CACHE.get(APP_IDS[i]+":"+data.getTargetSessionId());
            		//接收方在线
                	if(targetId!=null) {
                		logger.info("开始推送:("+APP_IDS[i]+":"+data.getTargetSessionId()+")");
                		SocketIOClient targetClient = client.getNamespace().getClient(targetId);
                		targetClient.sendEvent("im_push_msg_event", data);
                	}
            	}
    		}else {
    			logger.warn("data为空!"+obj);
    		}
    		
    	}else {
    		logger.warn("obj为空!");
    	}
    	
    }
    
}  