package com.nork.system.websocket.str;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.apache.log4j.Logger;


public class CommonEncoder implements Encoder.Text<String>{
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
	public String encode(String message) throws EncodeException {
		return message;
	} 
	
	 

}
