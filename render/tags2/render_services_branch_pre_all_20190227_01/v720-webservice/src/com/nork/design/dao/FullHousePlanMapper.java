package com.nork.design.dao;

import com.nork.design.model.FullHouseDesignPlan;
import org.springframework.stereotype.Repository;

@Repository
public interface FullHousePlanMapper {

    FullHouseDesignPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(FullHouseDesignPlan fullHouseDesignPlan);

    /**
     * 根据720资源uuid 查找非PC端制作的全屋方案
     * @param fullHouseDesignPlan
     * @return
     */
    FullHouseDesignPlan selectPlanByVrResourceUuid(FullHouseDesignPlan fullHouseDesignPlan);


}
