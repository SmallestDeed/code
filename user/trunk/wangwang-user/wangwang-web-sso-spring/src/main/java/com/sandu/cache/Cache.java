package com.sandu.cache;

public interface Cache {
	
    public String get(String key);
    
    public void expire(String key,int seconds);

    Long del(String key);
}
