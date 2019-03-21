package com.sandu.im.cache;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.sandu.im.common.util.GsonUtil;

@Service
public class RedisService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Resource(name = "stringRedisTemplate")
    ValueOperations<String, String> valOpsStr;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    ValueOperations<Object, Object> valOpsObj;

    /**
     * 根据指定key获取Object
     * @param key
     * @return
     */
    public <T> T getObject(String key, Class<T> objClass){ 
    	if (key == null) return null;
    	String json = valOpsStr.get(key);
        if (StringUtils.isNotBlank(json)) {
            return GsonUtil.fromJson(json, objClass);
        }
        return null;
    }
    
    public <T> List<T> getObjects(List<String> keys, Class<T> objClass) {
        List<T> resultList = new ArrayList<T>();
        List<String> list = valOpsStr.multiGet(keys);
        if (list != null && list.size() > 0) {
            for (String item : list) {
                if (StringUtils.isNoneBlank(item)) {
                    resultList.add(GsonUtil.fromJson(item, objClass));
                }
            }
        }
        return resultList;
    }

    /**
     * 设置Str缓存
     * @param key
     * @param val
     */
    public void set(String key, String val){
        valOpsStr.set(key,val);
    }

    /**
     * 删除指定key
     * @param key
     */
    public void del(String key){
        stringRedisTemplate.delete(key);
    }

    /**
     * 根据指定o获取Object
     * @param o
     * @return
     */
    public Object getObj(Object o){
        return valOpsObj.get(o);
    }

    /**
     * 设置obj缓存
     * @param o1
     * @param o2
     */
    public void setObj(Object o1, Object o2){
        valOpsObj.set(o1, o2);
    }

    /**
     * 删除Obj缓存
     * @param o
     */
    public void delObj(Object o){
        redisTemplate.delete(o);
    }

}