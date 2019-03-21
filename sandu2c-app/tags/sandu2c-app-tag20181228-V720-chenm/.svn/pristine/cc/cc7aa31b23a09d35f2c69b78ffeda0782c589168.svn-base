package com.sandu.job;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 11:19 2018/7/4 0004
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.google.gson.Gson;
import com.sandu.cache.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author weisheng
 * @Title: 产品运营分类
 * @Package
 * @Description:
 * @date 2018/7/4 0004AM 11:19
 */
@Component
@Slf4j
public class ProductOperationCategory {

    private final static Gson gson = new Gson();


    @Autowired
    private RedisService redisService;

    public void synchronizeProductOperationCategory() {
        log.info("清除缓存中的产品运营分类数据开始");
        long now = System.currentTimeMillis();
        int number = 0;
        /*清除缓存中的产品运营分类*/
        Map<String, String> map = redisService.getMap("BaseProductCategory");
        List<String> list = new ArrayList<>(map.size());
        if (null != map && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                list.add(entry.getKey());
            }
        }
        if (null != list && list.size() > 0) {
            for (String str : list) {
                boolean b = redisService.delMap("BaseProductCategory", str);
                if (b) {
                    number++;
                }
            }

        }
        log.info("清除缓存中的产品运营分类数据完成,总共清除条数:" + number + "耗时:" + (System.currentTimeMillis() - now) + "毫秒");

    }


}
