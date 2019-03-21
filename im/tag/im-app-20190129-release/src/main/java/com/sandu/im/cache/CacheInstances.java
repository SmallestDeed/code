package com.sandu.im.cache;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.sandu.im.event.entity.LocMessage;

public class CacheInstances {
	
	/**
	 * 用户sessionId,会话id映射
	 */
	public static ConcurrentHashMap<String,UUID> CHAT_SESSION_MAPPING_CACHE = new ConcurrentHashMap<String,UUID>();
		
	/**
	 * 用户sessionId与位置loc映射(key:appId+userSessionId,表示一个用户可以同时登录多个应用)
	 */
	public static ConcurrentHashMap<String,LocMessage> USER_LOC_MAPPING_CACHE = new ConcurrentHashMap<String,LocMessage>();
}
