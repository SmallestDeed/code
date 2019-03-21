package com.sandu.service.solution.dao;


import com.sandu.api.solution.model.FullHouseDesignPlanDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 15:18 2018/8/22
 */

@Repository
public interface FullHouseDesignPlanDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FullHouseDesignPlanDetail record);

    int insertSelective(FullHouseDesignPlanDetail record);

    FullHouseDesignPlanDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FullHouseDesignPlanDetail record);

    int updateByPrimaryKey(FullHouseDesignPlanDetail record);

    int deleteByFullHousePlanId(@Param("planId") Integer planId);

    List<FullHouseDesignPlanDetail> selectByPlanId(@Param("planId") Integer planId);
}