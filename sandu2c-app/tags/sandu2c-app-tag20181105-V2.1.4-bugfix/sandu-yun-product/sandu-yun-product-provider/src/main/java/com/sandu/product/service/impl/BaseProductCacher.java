package com.sandu.product.service.impl;

import com.google.gson.Gson;
import com.sandu.cache.RedisKeyConstans;
import com.sandu.cache.service.RedisService;
import com.sandu.common.util.SpringContextHolder;
import com.sandu.product.model.BaseProduct;
import com.sandu.product.service.BaseProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 基本产品缓存层
 *
 * @date 20171024
 * @auth pengxuangang
 */
public class BaseProductCacher {

    private final static Gson GSON = new Gson();
    private final static String CLASS_LOG_PREFIX = "[基本产品缓存层]:";
    private static Logger logger = LoggerFactory.getLogger(BaseProductCacher.class);
    private static RedisService redisService = SpringContextHolder.getBean(RedisService.class);
    private static BaseProductService baseProductService = SpringContextHolder.getBean(BaseProductService.class);

    /***
     * 获取基本产品的信息
     *
     * @param productId 基本产品ID
     * @return BaseProduct
     */
    public static BaseProduct get(int productId) {
        BaseProduct baseProduct = null;

        if (0 != productId) {
            //初始化对象
            String baseProductJson = redisService.getMap(RedisKeyConstans.BASE_PRODUCT_MAP, productId + "");
            if (org.springframework.util.StringUtils.isEmpty(baseProductJson)) {
                //从数据库获取数据
                logger.info(CLASS_LOG_PREFIX + "从数据库获取基本产品的信息->productId:{}", productId);
                BaseProduct dbBaseProduct = baseProductService.get(productId);
                logger.info(CLASS_LOG_PREFIX + "从数据库获取基本产品的信息完成->BaseProduct:{}", GSON.toJson(dbBaseProduct));
                if (null != dbBaseProduct) {
                    //装入缓存
                    logger.info(CLASS_LOG_PREFIX + "从数据库获取基本产品的信息后装入缓存->BaseProduct:{}", GSON.toJson(dbBaseProduct));
                    boolean addRedisStatus = redisService.addMap(RedisKeyConstans.BASE_PRODUCT_MAP, productId + "", GSON.toJson(dbBaseProduct));
                    logger.info(CLASS_LOG_PREFIX + "从数据库获取基本产品的信息后装入缓存完成->addRedisStatus:{}", addRedisStatus);
                    //返回数据
                    baseProduct = dbBaseProduct;
                }
            } else {
                //返回数据
                baseProduct = GSON.fromJson(baseProductJson, BaseProduct.class);
            }
        }

        return baseProduct;
    }

    /***
     * 移除基本产品缓存
     *
     * @param productId 基本产品ID
     */
    public static void remove(int productId) {
        logger.info(CLASS_LOG_PREFIX + "移除基本产品缓存->productId:{}", productId);
        boolean delStatus = redisService.delMap(RedisKeyConstans.BASE_PRODUCT_MAP, productId + "");
        logger.info(CLASS_LOG_PREFIX + "移除基本产品缓存完成->delStatus:{}", delStatus);
    }
}
