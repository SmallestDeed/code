package com.sandu.designplan.dao;

import com.sandu.company.model.vo.ShopPlanInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.sandu.common.persistence.CrudDao;
import com.sandu.designplan.model.DesignPlan;
import com.sandu.designplan.model.query.DesignPlanQuery;
import com.sandu.designplan.model.vo.DesignPlanVo;

import java.util.List;
import java.util.Map;

/**
 * Created by kono on 2018/5/24 0024.
 */
@Repository
public interface DesignPlanRecommendedDao extends CrudDao<DesignPlan,DesignPlanQuery,DesignPlanVo>{

    /**
     * 同type类型和企业ID获取推荐方案数量及公开方案数量
     * @param query 查询条件
     * @return
     */
    int findRecommendedPlanCount(DesignPlanQuery query);

    /**
     * 同type类型和企业ID获取推荐方案或公开方案集合
     * @param  query 查询条件
     * @return
     */
    List<DesignPlanVo> findRecommendedPlanList(DesignPlanQuery query);

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
    List<ShopPlanInfo> findShopPlanInfo(@Param("queryMap") Map<String,DesignPlanQuery> queryMap);
    
}
