package com.sandu.service.merchantManagePay.dao;

import com.sandu.api.merchantManagePay.model.DesignPlanBO;
import com.sandu.api.merchantManagePay.model.DesignPlanRecommended;
import com.sandu.api.merchantManagePay.model.FullHouseDesignBo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DesinPlanRecommendedDao {
    List<DesignPlanBO> getListsByIds(@Param("designPlanIds") Set<Long> designPlanIds);

    List<FullHouseDesignBo> getFullDesignPlans(@Param("designPlanIds")Set<Long> designPlanIds);
}
