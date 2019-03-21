package com.sandu.designconfig.service.impl;

import com.sandu.cache.service.DesignRuleCacheService;
import com.sandu.common.util.Utils;
import com.sandu.common.util.collections.Lists;
import com.sandu.design.model.DesignTemplet;
import com.sandu.designconfig.dao.DesignRulesMapper;
import com.sandu.designconfig.model.DesignRules;
import com.sandu.designconfig.model.search.DesignRulesSearch;
import com.sandu.designconfig.service.DesignRulesService;
import com.sandu.designtemplate.service.DesignTempletService;
import com.sandu.home.model.SpaceCommon;
import com.sandu.home.service.SpaceCommonService;
import com.sandu.product.model.BaseProduct;
import com.sandu.product.service.BaseProductService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @version V1.0
 * @Title: DesignRulesServiceImpl.java
 * @Package com.sandu.designconfig.service.impl
 * @Description:设计配置-设计规则ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2016-03-23 19:56:47
 */
@Service("designRulesService")
public class DesignRulesServiceImpl implements DesignRulesService {

    private static Logger logger = LoggerFactory.getLogger(DesignRulesServiceImpl.class);

    @Autowired
    private DesignRulesMapper designRulesMapper;
    @Autowired
    private SpaceCommonService spaceCommonService;
    @Autowired
    private BaseProductService baseProductService;
    @Autowired
    private DesignTempletService designTempletService;
    @Autowired
    private DesignRuleCacheService designRuleCacheService;

    /**
     * 新增数据
     *
     * @param designRules
     * @return int
     */
    @Override
    public int add(DesignRules designRules) {
        designRulesMapper.insertSelective(designRules);
        return designRules.getId();
    }

    /**
     * 更新数据
     *
     * @param designRules
     * @return int
     */
    @Override
    public int update(DesignRules designRules) {
        return designRulesMapper.updateByPrimaryKeySelective(designRules);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return designRulesMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return DesignRules
     */
    @Override
    public DesignRules get(Integer id) {
        return designRulesMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param designRules
     * @return List<DesignRules>
     */
    @Override
    public List<DesignRules> getList(DesignRules designRules) {
        return designRulesMapper.selectList(designRules);
    }

    /**
     * 获取数据数量
     *
     * @param designRulesSearch
     * @return int
     */
    @Override
    public int getCount(DesignRulesSearch designRulesSearch) {
        return designRulesMapper.selectCount(designRulesSearch);
    }

    /**
     * 分页获取数据
     *
     * @param designRulesSearch
     * @return List<DesignRules>
     */
    @Override
    public List<DesignRules> getPaginatedList(DesignRulesSearch designRulesSearch) {
        return designRulesMapper.selectPaginatedList(designRulesSearch);
    }

    @Override
    public Map<String, String> getRulesSecondaryList(String productIds, String productTypeCode, String productSmallTypeCode,
                                                     Integer spaceCommonId, Integer designTempletId, DesignRules designRules,
                                                     Map<String, String> productAttributeMap) {
        return this.getRulesSecondaryList(productIds, null, productTypeCode, productSmallTypeCode,
                spaceCommonId, designTempletId, designRules, productAttributeMap);
    }

    private Map<String, String> getRulesSecondaryList(String productIds, String productCode, String productTypeCode, String productSmallTypeCode,
                                                      Integer spaceCommonId, Integer designTempletId, DesignRules designRules,
                                                      Map<String, String> productAttributeMap) {
        /*logger.warn("====================================================================");*/
        /*logger.warn("productSmallTypeCode:"+productSmallTypeCode);*/
        Map<String, String> rulesMap = new HashMap<>();
        List<DesignRules> rulesList = new ArrayList<>();
        // 规则类型.由顺序决定优先级。下标越小优先级越高
        String[] rulesTypeArr = new String[]{"templetRulesType", "spaceRulesType", "commonRulesType"};
        // 规则级别.由顺序决定优先级。下标越小优先级越高
        String[] rulesLevelArr = new String[]{"productLevel", "attributeLevel", "smallLevel", "bigLevel"};

        // 按优先级来查询规则。只返回优先级最高的规则
        DesignRules newDesignRules;
        for (String rulesType : rulesTypeArr) {
            DesignTemplet designTemplet = null;
            if ("templetRulesType".equals(rulesType)) {
                if (designTempletId == null) {
                    continue;
                }
                if (Utils.enableRedisCache()) {
//					designTemplet = DesignCacher.getTemplet(designTempletId);
                } else {
                    designTemplet = designTempletService.get(designTempletId);
                }
                //检测样板房是否有规则，目的是阻止往下循环
                if (designTemplet != null) {
                    DesignRules rules = new DesignRules();
                    rules.setRulesBusiness(designTemplet.getDesignCode());
                    rules.setIsDeleted(0);
                    List<DesignRules> drList;
                    if (Utils.enableRedisCache()) {
                        drList = designRuleCacheService.getDesignRuleListByCondition(rules);
                    } else {
                        drList = designRulesMapper.selectList(rules);//设计规则
                    }
                    if (Lists.isEmpty(drList)) {
                        continue;
                    }
                }
            }
            SpaceCommon spaceCommon = null;
            if ("spaceRulesType".equals(rulesType)) {
                if (spaceCommonId == null) {
                    continue;
                }

                spaceCommon = spaceCommonService.get(spaceCommonId);
                //检测空间是否有规则，目的是阻止往下循环
                if (spaceCommon != null) {
                    DesignRules rules = new DesignRules();
                    rules.setRulesBusiness(spaceCommon.getSpaceCode());
                    rules.setIsDeleted(0);
                    List<DesignRules> drList;
                    if (Utils.enableRedisCache()) {
                        drList = designRuleCacheService.getDesignRuleListByCondition(rules);
                    } else {
                        drList = designRulesMapper.selectList(rules);
                    }
                    if (Lists.isEmpty(drList)) {
                        continue;
                    }
                }
            }
            for (String rulesLevel : rulesLevelArr) {
                newDesignRules = designRules.copy();
                newDesignRules.setRulesType(rulesType);
                newDesignRules.setRulesLevel(rulesLevel);
                /* TODO 规则类型设置 */
                // 如果是样板房规则，则传入样板房编码参数
                if ("templetRulesType".equals(rulesType)) {
                    if (designTemplet != null) {
                        newDesignRules.setRulesBusiness(designTemplet.getDesignCode());
                    }
                }
                // 如果是空间规则，则传入空间编码参数
                if ("spaceRulesType".equals(rulesType)) {
                    if (spaceCommon != null) {
                        newDesignRules.setRulesBusiness(spaceCommon.getSpaceCode());
                    }
                }
				/* TODO 规则级别设置 */
                // 产品级别
                if ("productLevel".equals(rulesLevel)) {
                    if (StringUtils.isBlank(productIds)) {
                        continue;
                    }


                    // 菜刀楼 2016-12-27 防止多余查询优化 start
                    if (StringUtils.isEmpty(productCode)) {
                        BaseProduct baseProduct = baseProductService.get(Integer.valueOf(productIds));
                        newDesignRules.setRulesMainValue(baseProduct.getProductCode());
                    } else {
                        newDesignRules.setRulesMainValue(StringUtils.trim(productCode));
                    }
                    // 菜刀楼 2016-12-27 防止多余查询优化 end

                    if (Utils.enableRedisCache()) {
                        rulesList = designRuleCacheService.getDesignRuleListByCondition(newDesignRules);
                    } else {
                        rulesList = designRulesMapper.selectList(newDesignRules);
                    }

                    // 如果查到有规则配置则直接返回
                    if (rulesList != null && rulesList.size() > 0) {
                        for (DesignRules rules : rulesList) {
                            if (StringUtils.isBlank(rulesMap.get(rules.getRulesSign()))) {
                                rulesMap.put(rules.getRulesSign(), rules.getRulesSecondaryValues());
                            }
                        }
                        // return rulesMap;
                        continue;
                    }
                }
                //属性级别
                if ("attributeLevel".equals(rulesLevel)) {
                    boolean flag = true;
                    if (StringUtils.isBlank(productSmallTypeCode)) {
                        continue;
                    }
                    if (productAttributeMap == null || productAttributeMap.size() == 0) {
                        continue;
                    }

                    newDesignRules.setRulesMainObj(productSmallTypeCode);

                    if (Utils.enableRedisCache()) {
                        rulesList = designRuleCacheService.getDesignRuleListByCondition(newDesignRules);
                    } else {
                        rulesList = designRulesMapper.selectList(newDesignRules);
                    }
                    // 如果查到有规则配置则直接返回
                    if (rulesList != null && rulesList.size() > 0) {
                        Map<String, String> rulesProductAttributeMap = null;
                        for (DesignRules rules : rulesList) {
                            if (StringUtils.isNotBlank(rules.getRulesMainValue())) {
                                JSONObject json = JSONObject.fromObject(rules.getRulesMainValue());
                                if (json != null) {
                                    // 规则中配置的属性
                                    rulesProductAttributeMap = new HashMap<>();
                                    for (String jsonKey : (Set<String>) json.keySet()) {
                                        if (StringUtils.isNotBlank(jsonKey)) {
                                            rulesProductAttributeMap.put(jsonKey, json.getString(jsonKey));
                                        }
                                    }
                                    int count = 0;// 记录一致属性的数量

                                    for (Map.Entry<String, String> entry : rulesProductAttributeMap.entrySet()) {
                                        if (productAttributeMap.containsKey(entry.getKey()) && rulesProductAttributeMap.get(entry.getKey()).equals(productAttributeMap.get(entry.getKey()))) {
                                            count++;
                                        } else {
                                            continue;
                                        }
                                    }

                                    //产品属性等于或包含则匹配属性
                                    if (rulesProductAttributeMap.size() == count) {
                                        rulesMap.put(rules.getRulesSign(), rules.getRulesSecondaryValues());
                                    }
                                    // 比较产品本身属性和规则中的属性是否一致
                                }
                            }
                        }
                        continue;
                    }
                }
                // 小类级别
                if ("smallLevel".equals(rulesLevel)) {
                    if (StringUtils.isBlank(productIds)) {
                        continue;
//						return rulesMap;
                    }
                    newDesignRules.setRulesMainValue(productSmallTypeCode);
                    if (Utils.enableRedisCache()) {
                        rulesList = designRuleCacheService.getDesignRuleListByCondition(newDesignRules);
                    } else {
                        rulesList = designRulesMapper.selectList(newDesignRules);
                    }
                    //rulesList=DesignRulesCacher.getList(newDesignRules);
                    // 如果查到有规则配置则直接返回
                    if (rulesList != null && rulesList.size() > 0) {
                        for (DesignRules rules : rulesList) {
                            if (StringUtils.isBlank(rulesMap.get(rules.getRulesSign()))) {
                                rulesMap.put(rules.getRulesSign(), rules.getRulesSecondaryValues());
                            }
                        }
                        // return rulesMap;
                        continue;
                    }
                }
                // 大类级别
                if ("bigLevel".equals(rulesLevel)) {
                    if (StringUtils.isBlank(productIds)) {
                        continue;
                    }
                    newDesignRules.setRulesMainValue(productTypeCode);
                    if (Utils.enableRedisCache()) {
                        rulesList = designRuleCacheService.getDesignRuleListByCondition(newDesignRules);
                    } else {
                        rulesList = designRulesMapper.selectList(newDesignRules);
                    }
                    // 如果查到有规则配置则直接返回
                    if (rulesList != null && rulesList.size() > 0) {
                        for (DesignRules rules : rulesList) {
                            if (StringUtils.isBlank(rulesMap.get(rules.getRulesSign()))) {
                                rulesMap.put(rules.getRulesSign(), rules.getRulesSecondaryValues());
                            }
                        }
                        continue;
                    }
                }
            }
        }

        return rulesMap;
    }

}
