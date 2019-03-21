package com.sandu.cache.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sandu.cache.service.DesignRuleCacheService;
import com.sandu.cache.service.RedisService;
import com.sandu.designconfig.model.DesignRules;
import com.sandu.designconfig.service.DesignRulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sandu.cache.RedisKeyConstans.DESIGN_RULE_KEY;

/***
 * 设计规则缓存服务
 *
 * @date 20171024
 * @auth pengxuangang
 */
@Service("designRuleCacheService")
public class DesignRuleCacheServiceImpl implements DesignRuleCacheService {

    private final static Gson GSON = new Gson();
    private final static String CLASS_LOG_PREFIX = "[设计规则缓存服务]:";
    private static Logger logger = LoggerFactory.getLogger(DesignRuleCacheServiceImpl.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private DesignRulesService designRulesService;

    //过滤符合条件设计规则
    private static List<DesignRules> filterConformConditionDesignRule(List<DesignRules> dbDesignRulesList, DesignRules designRules) {

        if (null == dbDesignRulesList || dbDesignRulesList.size() == 0) {
            return null;
        }

        if (null == designRules) {
            return dbDesignRulesList;
        }

        List<DesignRules> designRulesList = new ArrayList<>(dbDesignRulesList.size());

        //遍历数据
        dbDesignRulesList.forEach(dbDesignRules -> {
            //条件为空时不作为过滤条件
            if ((org.springframework.util.StringUtils.isEmpty(designRules.getRulesType()) || designRules.getRulesType().equals(dbDesignRules.getRulesType()))
                    && (org.springframework.util.StringUtils.isEmpty(designRules.getRulesBusiness()) || designRules.getRulesBusiness().equals(dbDesignRules.getRulesBusiness()))
                    && (org.springframework.util.StringUtils.isEmpty(designRules.getRulesLevel()) || designRules.getRulesLevel().equals(dbDesignRules.getRulesLevel()))
                    && (org.springframework.util.StringUtils.isEmpty(designRules.getRulesMainValue()) || designRules.getRulesMainValue().equals(dbDesignRules.getRulesMainValue()))
                    && (org.springframework.util.StringUtils.isEmpty(designRules.getRulesMainObj()) || designRules.getRulesMainObj().equals(dbDesignRules.getRulesMainObj()))) {
                designRulesList.add(dbDesignRules);
            }
        });

        return designRulesList;
    }

    @Override
    public List<DesignRules> getDesignRuleListByCondition(DesignRules designRules) {
        List<DesignRules> designRulesList = null;

        //获取设计规则缓存数据所有数据
        String designRuleListJson = redisService.get(DESIGN_RULE_KEY, null);
        if (org.springframework.util.StringUtils.isEmpty(designRuleListJson)) {
            //从数据库获取数据装入缓存
            List<DesignRules> dbDesignRulesList = loadDesignRuleDateToCache(designRules);
            if (null != dbDesignRulesList && dbDesignRulesList.size() > 0) {
                //过滤符合条件设计规则
                designRulesList = filterConformConditionDesignRule(dbDesignRulesList, designRules);
            }
        } else {
            //缓存中设计规则List
            List<DesignRules> redisDesignRulesList = GSON.fromJson(designRuleListJson, new TypeToken<List<DesignRules>>() {
            }.getType());
            //过滤符合条件设计规则
            designRulesList = filterConformConditionDesignRule(redisDesignRulesList, designRules);
            //若未查询到数据则从数据库重新获取
            if (null == designRulesList || designRulesList.size() < 1) {
                //从数据库获取数据装入缓存
                List<DesignRules> dbDesignRulesList = loadDesignRuleDateToCache(designRules);
                if (null != dbDesignRulesList && dbDesignRulesList.size() > 0) {
                    //过滤符合条件设计规则
                    designRulesList = filterConformConditionDesignRule(dbDesignRulesList, designRules);
                }
            }
        }

        return designRulesList;
    }

    //从数据库加载设计规则至缓存
    private List<DesignRules> loadDesignRuleDateToCache(DesignRules designRules) {
        logger.info(CLASS_LOG_PREFIX + "从数据库加载设计规则至缓存--读取数据开始:DesignRules:{}", designRules.toString());
        List<DesignRules> designRulesList = designRulesService.getList(designRules);
        logger.info(CLASS_LOG_PREFIX + "从数据库加载设计规则至缓存--读取数据完成:JSON[List<DesignRules>]:{}", GSON.toJson(designRulesList));

        if (null != designRulesList && designRulesList.size() > 0) {
            logger.info(CLASS_LOG_PREFIX + "从数据库加载设计规则至缓存--写入缓存开始:JSON[List<DesignRules>]:{}", GSON.toJson(designRulesList));
            boolean setStatus = redisService.set(DESIGN_RULE_KEY, GSON.toJson(designRulesList));
            logger.info(CLASS_LOG_PREFIX + "从数据库加载设计规则至缓存--写入缓存完成写入状态:{}", setStatus);
        }

        return designRulesList;
    }
}
