package com.sandu.listener;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:23 2018/5/28 0028
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */


import com.google.gson.Gson;
import com.sandu.base.model.BaseArea;
import com.sandu.base.service.BaseAreaService;
import com.sandu.cache.service.RedisService;
import com.sandu.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author weisheng
 * @Title: 项目初始化监听器
 * @Package
 * @Description:
 * @date 2018/5/28 0028AM 10:23
 */
@Component
public class BaseAreaInit {

    private final static Gson gson = new Gson();
    private static Logger logger = LogManager.getLogger(BaseAreaInit.class);


    @Autowired
    private RedisService redisService;
    @Autowired
    private BaseAreaService baseAreaService;


    /*初始化基础数据存入到redis中*/
    @PostConstruct
    public void initBaseArea() {
        String areaList = null;
        boolean isContinue = true;
        while (isContinue) {
            try {
                areaList = getMiniProgramAreaList();
                isContinue = false;
            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        if (StringUtils.isNotBlank(areaList)) {
            logger.info("初始化数据,获取省市区缓存信息");
            return;
        }
        isContinue = true;
        List<BaseArea> nList = null;
        String type = null;
        while (isContinue) {
            try {
                nList = baseAreaService.getNewAreaList();
                isContinue = false;
            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (nList != null && nList.size() > 0) {
            redisService.addMap("BaseArea", "MiniProgramAreaList", gson.toJson(nList));
        } else {
            logger.error("无法查询到省市区列表信息,初始化数据失败");
        }
    }



    private String getMiniProgramAreaList() {
        return redisService.getMap("BaseArea", "MiniProgramAreaList");
    }


}
