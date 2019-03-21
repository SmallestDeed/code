package com.sandu.job;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 11:19 2018/7/4 0004
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sandu.cache.service.RedisService;
import com.sandu.common.util.StringUtils;
import com.sandu.pay.alipay.util.WXChatRequest;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.model.ProCategoryPo;
import com.sandu.product.service.BaseCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

/**
 * @Title: 产品运营分类
 * @Package
 * @Description:
 * @author weisheng
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
        int number = 0 ;
        /*清除缓存中的产品运营分类*/
        for(int i = 0; i < 9999;i++){
            boolean b = redisService.delMap("BaseProductCategory", "MiniProgramProductCategory" + i);
            if(b){
                number++;
            }
        }
        log.info("清除缓存中的产品运营分类数据完成,总共清除条数:"+number+"耗时:"+ (System.currentTimeMillis()-now)+"毫秒");

    }



















}
