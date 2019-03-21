package com.sandu.pay.system.websocket.str;

import org.apache.log4j.Logger;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;


public class CommonDecoder implements Decoder.Text<String>{
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
	public String decode(String s) throws DecodeException {
        return s;
	}

	@Override
	public boolean willDecode(String s) {
		try {
			//Json.createReader(new StringReader(s)).readObject();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
