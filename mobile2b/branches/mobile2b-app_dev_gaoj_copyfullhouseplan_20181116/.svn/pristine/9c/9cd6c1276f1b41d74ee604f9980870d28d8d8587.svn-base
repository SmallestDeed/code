package com.nork.design.dao;

import com.nork.design.model.DecorateDesignPlanImgInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author WangHaiLin
 * @date 2018/10/20  18:03
 */
@Repository
public interface DecorateDesignPlanImgMapper {
    /**
     *  通过Id查询推荐方案封面图
     * @param planId 方案Id
     * @return
     */
    DecorateDesignPlanImgInfo getRecommendedById(@Param("planId") Long planId);

    /**
     *  通过Id查询全屋方案封面图
     * @param planId 方案Id
     * @return
     */
    DecorateDesignPlanImgInfo getWholeHouseById(@Param("planId") Long planId);
}
