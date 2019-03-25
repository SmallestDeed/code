package com.sandu.collect.sdk;

import java.util.logging.Logger;

import com.sandu.collect.model.EventLog;


public class Tracker {
	// 日志打印对象
	private static final Logger log = Logger.getGlobal();

	public static void track(EventLog eventLog) {
		String url = eventLog.buildRequestUrl();
		SendDataMonitor.addSendUrl(url);
	}

}
