package com.sandu.im.event.handler;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.sandu.im.cache.CacheInstances;
import com.sandu.im.event.entity.LocMessage;

@Component
public class IMLocEventHandler     
{  
	private static final Logger logger = LoggerFactory.getLogger(IMLocEventHandler.class);
	protected SocketIOServer server;
		
    @Autowired  
    public IMLocEventHandler(SocketIOServer server)   
    {  
        this.server = server ;
    }  
    
    @OnConnect  
    public void onConnect(SocketIOClient client)  
    {
    	
    }  
      
    @OnDisconnect  
    public void onDisconnect(SocketIOClient client)  
    {
    	
    }
    
    
    @OnEvent(value = "im_loc_msg_event")  
    public void onEvent(SocketIOClient client, AckRequest request, LocMessage data) throws UnsupportedEncodingException   
    {
    	logger.debug("收到位置参数:"+data);
    	if(validateParam(data)) {
    		CacheInstances.USER_LOC_MAPPING_CACHE.put(data.getAppId()+":"+data.getUserSessionId(), data);
    	}
    	CacheInstances.USER_LOC_MAPPING_CACHE.toString();
    	logger.debug("缓存数量:"+CacheInstances.USER_LOC_MAPPING_CACHE.size());

    }

	private boolean validateParam(LocMessage data) {
		if(data.getAppId()==null) {
			logger.warn("无效参数:"+data);
			return false;
		}
			
		if(StringUtils.isNotBlank(data.getUserSessionId()) && data.getLoc()!=null) {
			if((data.getLoc().intValue()==LocMessage.LOC_CHAT_VIEW || data.getLoc().intValue()==LocMessage.LOC_SUPPLY_VIEW)
					&& data.getRelatedObjType()!=null 
					&& data.getRelatedObjId()!=null && StringUtils.isNotBlank(data.getContactSessionId())) {
				return true;
			}else if(data.getLoc().intValue()==LocMessage.LOC_OHTER_VIEW) {
				return true;
			}
		}
		
		logger.warn("无效参数:"+data);
		return false;
	}
    
    
    
}  