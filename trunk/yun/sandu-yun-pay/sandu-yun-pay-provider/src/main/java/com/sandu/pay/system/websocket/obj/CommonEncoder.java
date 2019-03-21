package com.sandu.pay.system.websocket.obj;


import com.sandu.system.model.SysMessageRecord;
import org.apache.log4j.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class CommonEncoder implements Encoder.Text<SysMessageRecord>{
	private static Logger logger = Logger.getLogger(CommonEncoder.class);

	@Override
	public void init(EndpointConfig config) {
		logger.info("DeviceMessageEncoder - init method called");  
	}

	@Override
	public void destroy() {
		logger.info("DeviceMessageEncoder - destroy method called");  
	}
	
	@Override
	public String encode(SysMessageRecord message) throws EncodeException {
		JsonObject jsonObject = Json.createObjectBuilder()
	            //.add("id", message.getId())
	            //.add("gmtCreate", Utils.formatDate(message.getGmtCreate(), Utils.DATE_TIME))
	            .add("sendTime", message.getSendTime())
	            .add("fromUser", message.getFromUser())
	            .add("targetUser", message.getTargetUser())
	            .add("content", message.getContent())
	            .add("IsRead", message.getIsRead())
	            .add("type", message.getType())
	            .build();
		return jsonObject.toString();
	} 
	
	 

}
