package com.sandu.api.redis;

import java.util.List;
import java.util.Set;

public interface RedisService {
	
	public Set<String> keys(String pattern);

    public boolean set(String key, String value, int seconds);

    public boolean set(String key, String value);

    public String get(String key);

    public List<String> mget(String... keys);

    boolean del(String key);

    public void expire(String key, int seconds);

    boolean sadd(String key, String... value);

    void saddObject(String key, Set set);

    Set<String> smembers(String key);

    Set<String> sunion(String... keys);

    Set<String> zrange(String key);

    <T> T getObject(String key, Class<T> objClass);

    public <T> List<T> getObjects(String[] keys, Class<T> objClass);

    public <T> Set<T> sunionObjects(String[] keys, Class<T> objClass);

    <T> Set<T> smembersObject(String key, Class<T> objClass);

    void setObject(String key, Object obj);

    long incr(String key);

    long decr(String key);
    
    boolean addMap(String mapName, String key, String value);
    public String getMap(String mapName, String key);
    public boolean delMap(String mapName, String key);
}
