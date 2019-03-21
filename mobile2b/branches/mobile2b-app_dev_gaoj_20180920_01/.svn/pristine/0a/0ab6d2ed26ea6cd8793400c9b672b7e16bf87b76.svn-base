package com.nork.common.memcached;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.danga.MemCached.MemCachedClient;


public class MemCache {
	private static Logger log = Logger
			.getLogger(MemCache.class);  
	  
    private Set<String> keySet = new HashSet<String>();  
    private final String name;  
    private final int expire;  
    private final MemCachedClient memcachedClient;  
  
    public MemCache(String name, int expire, MemCachedClient memcachedClient) {  
        this.name = name;  
        this.expire = expire;  
        this.memcachedClient = memcachedClient;  
    }  
  
    public Object get(String key) {  
        Object value = null;  
        try {  
            key = this.getKey(key);  
            value = memcachedClient.get(key);  
            /*  } catch (TimeoutException e) {  
            log.error("获取 Memcached 缓存超时", e);  
        } catch (InterruptedException e) {  
            log.error("获取 Memcached 缓存被中断", e);  
        } catch (MemcachedException e) {  
            log.error("获取 Memcached 缓存错误", e);  
        }*/  
        }catch(Exception e){
        	e.printStackTrace();
        }
        return value;  
    }  
  
    public void put(String key, Object value) {  
        if (value == null)  
            return;  
  
        try {  
            key = this.getKey(key);  
           // memcachedClient.delete(key, expire, value);  
            memcachedClient.delete(key);
            keySet.add(key);  
        /*} catch (InterruptedException e) {  
            log.warn("更新 Memcached 缓存被中断", e);  
        } catch (MemcachedException e) {  
            log.warn("更新 Memcached 缓存错误", e);  
        }*/  
        }catch(Exception e){
        	e.printStackTrace();
        }
    }  
  
    public void clear() {  
        for (String key : keySet) {  
            try {  
                //memcachedClient.deleteWithNoReply(this.getKey(key));  
            	memcachedClient.decr(this.getKey(key));
            /* } catch (InterruptedException e) {  
                log.warn("删除 Memcached 缓存被中断", e);  
            } catch (MemcachedException e) {  
                log.warn("删除 Memcached 缓存错误", e);  
            } */ 
            }catch(Exception e){
            	e.printStackTrace();
            }
        }  
    }  
  
    public void delete(String key) {  
        try {  
            key = this.getKey(key);  
           // memcachedClient.deleteWithNoReply(key);  
            memcachedClient.delete(key);
       /*} catch (InterruptedException e) {  
            log.warn("删除 Memcached 缓存被中断", e);  
        } catch (MemcachedException e) {  
            log.warn("删除 Memcached 缓存错误", e);  
        }  */
        }catch(Exception e){
        	e.printStackTrace();
        }
    }  
  
    private String getKey(String key) {  
        return name + "_" + key;  
    }  

}
