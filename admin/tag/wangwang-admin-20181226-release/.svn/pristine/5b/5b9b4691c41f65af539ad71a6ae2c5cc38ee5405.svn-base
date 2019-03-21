package com.sandu.service.solution.dao;


import com.sandu.api.solution.model.PlanDecoratePrice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: YuXingchi
 * @Description:
 */

@Repository
public interface PlanDecoratePriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlanDecoratePrice record);

    int insertSelective(PlanDecoratePrice record);

    PlanDecoratePrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlanDecoratePrice record);

    int updateByPrimaryKey(PlanDecoratePrice record);

    List<PlanDecoratePrice> selectByFullHouseId(@Param("fullHouseId") Integer fullHouseId);

    int deleteByFullHouseId(@Param("fullHouseId") Integer fullHouseId);

    List<PlanDecoratePrice> selectByDesignPlanId(@Param("recommendId") Integer recommendId);

    int deleteByRecommonedId(@Param("recommendId") Integer recommendId);

    int updateByPlanRecommendId(PlanDecoratePrice planDecoratePrice);

}