package com.sandu.designplan.service;

import java.util.List;
import java.util.Map;

import com.sandu.company.model.vo.ShopPlanInfo;
import com.sandu.designplan.model.query.DesignPlanQuery;
import com.sandu.designplan.model.vo.DesignPlanVo;
import com.sandu.matadata.Page;

/**
 * Created by kono on 2018/5/24 0024.
 */
public interface DesignPlanRecommendedService {

    /**
     * 同type类型和企业ID获取推荐方案数量及公开方案数量
     * @param query
     * @return
     */
    int findRecommendedPlanCount(DesignPlanQuery query);


    /**
     * 同type类型和企业ID获取推荐方案或公开方案集合
     * @param query
     * @return
     */
    List<DesignPlanVo> findRecommendedPlanList(DesignPlanQuery query);
    
    /***
     * 分页查询推荐方案
     * @param query
     * @return
     */
    Page<DesignPlanVo> list(DesignPlanQuery query);


    /**
     * 获取平台Id
     * @param platformCode
     * @return
     */
    Integer getPlatformId(String platformCode);


    /**
     * 获取店铺列表方案信息
     * @param queryList
     * @return
     */
    List<ShopPlanInfo> getShopPlanInfo(Map<String,DesignPlanQuery> queryMap);
}
