package com.sandu.search.common.tools;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.Map.Entry;


@Slf4j
/**
 * 可以设置内存map过期时间的工具类
 */
public class ExpireMapUtil {

    private static Map<String, String> dataMap = new HashMap<>();//存放保存的数据
    private static Map<String, Long> timeMap = new HashMap<>();//存放key和存入时间
    private static final long EXPIRATION_TIME = 1000 * 60;//过期时间60s
    private static final int START = 0;//设置执行开始时间
    private static final int INTERVAL = 10000;//设置间隔执行时间 单位：毫秒

    public static void put(String key, String value) {
        dataMap.put(key, value);
        timeMap.put(key, new Date().getTime());
    }

    public static String get(String key) {
        return dataMap.get(key);
    }

    public static boolean containsKey(String key){
        boolean isExist = dataMap.containsKey(key);
        if (isExist) {
            //重新设置过期时间，避免现在存在但之后获取的时候过期的问题
            timeMap.put(key, new Date().getTime());
        }
        return isExist;
    }

    static {
        Timer tt = new Timer();//定时类
        tt.schedule(new TimerTask() {//创建一个定时任务
            @Override
            public void run() {
                long nd = new Date().getTime();//获取系统时间
                Iterator<Entry<String, Long>> entries = timeMap.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry) entries.next();
                    String key = (String) entry.getKey(); //获取key
                    long value = (Long) entry.getValue(); //获取value
                    long rt = nd - value;//获取当前时间跟存入时间的差值
                    if (rt > EXPIRATION_TIME) {//判断时间是否已经过期  如果过期则清楚key 否则不做处理
                        dataMap.put(key, null);
                        entries.remove();
                        log.info("内存map==>timeKey Key:" + key + " 已过期!!!清空");
                    }
                }
            }
        }, START, INTERVAL);//从0秒开始，每隔10秒执行一次
    }
}

