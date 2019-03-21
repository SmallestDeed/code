package com.sandu.service.fullhouse.dao;


import com.sandu.api.fullhouse.input.FullHouseDesignPlanQuery;
import com.sandu.api.fullhouse.model.BaseHousePicFullHousePlanRel;
import com.sandu.api.fullhouse.model.FullHouseDesignPlan;
import com.sandu.api.fullhouse.output.FullHouseDesignPlanVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FullHouseDesignPlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FullHouseDesignPlan record);

    int insertSelective(FullHouseDesignPlan record);

    FullHouseDesignPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FullHouseDesignPlan record);

    int updateByPrimaryKey(FullHouseDesignPlan record);

    List<FullHouseDesignPlanVO> selectFullHouseDesignPlan(FullHouseDesignPlanQuery query);

    Integer selectFullHouseDesignPlanCount(FullHouseDesignPlanQuery query);

    FullHouseDesignPlan selectFullHouseDesignPlanByUuid(String uuid);

    int logicDeleteFullHouseDesignPlan(Integer id);

    List<BaseHousePicFullHousePlanRel> getBaseHousePicFullHousePlanRel(@Param("fullHousePlanId") Integer fullHousePlanId);

    int insertBatchBaseHousePicFullHousePlanRel(@Param("baseHousePicFullHousePlanRelList") List<BaseHousePicFullHousePlanRel> baseHousePicFullHousePlanRelList);
}