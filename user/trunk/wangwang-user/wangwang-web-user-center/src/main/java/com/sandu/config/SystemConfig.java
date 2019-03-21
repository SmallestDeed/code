package com.sandu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

	public static boolean DEBUGMODEL;
	
	@Value("${system.debugModel}")
	public void setDEBUGMODEL(boolean debugModel) {
		SystemConfig.DEBUGMODEL = debugModel;
	}
	
}
