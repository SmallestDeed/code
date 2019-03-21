package com.sandu.designconfig.service;

import com.sandu.designconfig.model.DesignRules;
import com.sandu.designconfig.model.search.DesignRulesSearch;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: DesignRulesService.java
 * @Package com.sandu.designconfig.service
 * @Description:设计配置-设计规则Service
 * @createAuthor pandajun
 * @CreateDate 2016-03-23 19:56:47
 */
public interface DesignRulesService {
    /**
     * 新增数据
     *
     * @param designRules
     * @return int
     */
    int add(DesignRules designRules);

    /**
     * 更新数据
     *
     * @param designRules
     * @return int
     */
    int update(DesignRules designRules);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return DesignRules
     */
    DesignRules get(Integer id);

    /**
     * 所有数据
     *
     * @param designRules
     * @return List<DesignRules>
     */
    List<DesignRules> getList(DesignRules designRules);

    /**
     * 获取数据数量
     *
     * @param designRulesSearch
     * @return int
     */
    int getCount(DesignRulesSearch designRulesSearch);

    /**
     * 分页获取数据
     *
     * @param designRulestSearch
     * @return List<DesignRules>
     */
    List<DesignRules> getPaginatedList(
            DesignRulesSearch designRulestSearch);

    /**
     * 其他
     *
     */
    /**
     * 获取规则
     *
     * @param productIds
     * @param productTypeCode
     * @param productSmallTypeCode
     * @param spaceCommonId
     * @param designTempletId
     * @param designRules
     * @return
     */
    Map<String, String> getRulesSecondaryList(String productIds, String productTypeCode, String productSmallTypeCode,
                                              Integer spaceCommonId, Integer designTempletId, DesignRules designRules, Map<String, String> productAttributeMap);


}
