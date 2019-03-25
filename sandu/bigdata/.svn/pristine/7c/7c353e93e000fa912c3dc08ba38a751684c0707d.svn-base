package com.sandu.collect.sdk.test;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.sandu.collect.model.EventLog;
import com.sandu.collect.sdk.Tracker;

public class Test {

	public static void main(String[] args) {
		EventLog eventLog = new EventLog();
		eventLog.setUid("0001");
		eventLog.setClientTime(System.currentTimeMillis());
		eventLog.setEventType(EventLog.EVENT_TYPE_PV);
		eventLog.setEventName(EventLog.EVENT_NAME_PV_COMMON);
		Map evMap = new HashMap();
		evMap.put("curpage", "page002");
		evMap.put("refferpage", "page001");
		eventLog.setEventProperty(JSON.toJSONString(evMap));
		eventLog.setAppid("app001");
		eventLog.setSdk(EventLog.SDK);
		eventLog.setVersion(EventLog.VERSION);
		
		System.out.println(JSON.toJSONString(eventLog));
		Tracker.track(eventLog);
	}
}
