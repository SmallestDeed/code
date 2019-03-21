package com.sandu.pay.system.websocket.obj;

import com.sandu.common.util.Utils;
import com.sandu.system.model.SysMessageRecord;
import org.apache.log4j.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;




public class CommonDecoder implements Decoder.Text<SysMessageRecord>{
	private static Logger logger = Logger.getLogger(CommonDecoder.class);
	
	@Override
	public void init(EndpointConfig config) {
		logger.info("DeviceMessageDecoder - init method called");  
	}

	@Override
	public void destroy() {
		logger.info("DeviceMessageDecoder - destroy method called");  
	}

	@Override
	public SysMessageRecord decode(String s) throws DecodeException {
		SysMessageRecord message = new SysMessageRecord(); 
	    
        JsonObject obj = Json.createReader(new StringReader(s)) 
                .readObject(); 
        
        message.setId(obj.getInt("id"));
        message.setGmtCreate(Utils.parseDate(obj.getString("gmtCreate"), Utils.DATE_TIME));
        message.setSendTime(obj.getString("sendTime"));
        message.setFromUser(obj.getInt("fromUser"));
        message.setTargetUser(obj.getInt("targetUser"));
        message.setContent(obj.getString("content"));
        message.setIsRead(obj.getInt("IsRead"));
        message.setType(obj.getInt("type"));
        return message;
	}

	@Override
	public boolean willDecode(String s) {
		try {
			Json.createReader(new StringReader(s)).readObject();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
