package com.sandu.cache.service.impl;

import com.google.gson.Gson;
import com.sandu.cache.RedisKeyConstans;
import com.sandu.cache.service.DesignPlanCacheService;
import com.sandu.cache.service.RedisService;
import com.sandu.design.model.DesignTemplet;
import com.sandu.designplan.model.DesignPlan;
import com.sandu.designplan.service.DesignPlanService;
import com.sandu.designtemplate.service.DesignTempletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/***
 * @desc 设计方案缓存
 * @date 20171018
 * @auth pengxuangang
 */
@Service("designPlanCacheService")
public class DesignPlanCacheServiceImpl implements DesignPlanCacheService {

    private final static Gson GSON = new Gson();
    private final static String CLASS_LOG_PREFIX = "[设计方案缓存服务]:";
    private static Logger logger = LoggerFactory.getLogger(DesignPlanCacheServiceImpl.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private DesignPlanService designPlanService;
    @Autowired
    private DesignTempletService designTempletService;

    /**
     * 获取设计方案
     *
     * @param designPlanId 设计方案ID
     * @return 设计方案对象
     */
    @Override
    public DesignPlan getDesignPlanById(int designPlanId) {
        if (0 == designPlanId) {
            return null;
        }

        DesignPlan designPlan;
        String designPlanJson = redisService.getMap(RedisKeyConstans.DESIGN_PLAN_MAP, Integer.toString(designPlanId));

        if (StringUtils.isEmpty(designPlanJson)) {
            //从数据库获取
            designPlan = designPlanService.get(designPlanId);

            if (null == designPlan) {
                logger.warn(CLASS_LOG_PREFIX + "获取设计方案->从数据库获取设计方案完成，设计方案未找到!designPlanId:{}", designPlanId);
                return null;
            }

            //加入缓存
            redisService.addMap(RedisKeyConstans.DESIGN_PLAN_MAP, Integer.toString(designPlanId), GSON.toJson(designPlan));

        } else {
            //转换为对象
            designPlan = GSON.fromJson(designPlanJson, DesignPlan.class);
        }

        return designPlan;
    }


    /***
     * 获取设计方案样板房详情
     *
     * @param designPlanTemplateId  样板房ID
     * @return
     */
    @Override
    public DesignTemplet getDesignTempletById(int designPlanTemplateId) {

        if (0 == designPlanTemplateId) {
            return null;
        }

        DesignTemplet designPlanTemplet;
        //从缓存获取数据
        String designPlanTemplateJson = redisService.getMap(RedisKeyConstans.DESIGN_PLAN_TEMPLATE_MAP, Integer.toString(designPlanTemplateId));

        if (StringUtils.isEmpty(designPlanTemplateJson)) {
            //从数据库获取
            designPlanTemplet = designTempletService.get(designPlanTemplateId);

            if (null == designPlanTemplet) {
                logger.warn(CLASS_LOG_PREFIX + "获取设计方案->从数据库获取设计方案样板房完成，设计方案样板房未找到!designPlanTemplet is null.");
                return null;
            }

            //缓存数据
            redisService.addMap(RedisKeyConstans.DESIGN_PLAN_TEMPLATE_MAP, Integer.toString(designPlanTemplateId), GSON.toJson(designPlanTemplet));
        }

        //转换对象
        designPlanTemplet = GSON.fromJson(designPlanTemplateJson, DesignTemplet.class);

        return designPlanTemplet;
    }
}
