package com.sandu.fullhouse.dao;


import com.sandu.fullhouse.model.FullHouseDesignPlanDetail;
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

    List<FullHouseDesignPlanDetail> selectByPlanId(@Param("planId") Integer planId);
}