package com.nork.design.dao;

import java.util.List;

import com.nork.product.model.result.DesignProductResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 方案产品DAO
 * @author xiaoxc
 * @date 20171108
 */
@Repository
public interface DesignPlanProductDao {

    List<DesignProductResult> findPlanSeriesProductByPlanProuctIds(@Param("planProductIdList") List<String> planProductIdList);
 
}
