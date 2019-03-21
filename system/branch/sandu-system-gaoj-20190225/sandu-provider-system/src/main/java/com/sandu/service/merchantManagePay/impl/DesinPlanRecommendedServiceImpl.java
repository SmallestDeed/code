package com.sandu.service.merchantManagePay.impl;

import com.sandu.api.merchantManagePay.model.DesignPlanBO;
import com.sandu.api.merchantManagePay.model.DesignPlanRecommended;
import com.sandu.api.merchantManagePay.model.FullHouseDesignBo;
import com.sandu.api.merchantManagePay.service.DesinPlanRecommendedService;
import com.sandu.service.merchantManagePay.dao.DesinPlanRecommendedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service(value ="desinPlanRecommendedService")
public class DesinPlanRecommendedServiceImpl implements DesinPlanRecommendedService{

    @Autowired
    private DesinPlanRecommendedDao desinPlanRecommendedDao;

    @Override
    public List<DesignPlanBO> getListsByIds(Set<Long> designPlanIds) {
        return desinPlanRecommendedDao.getListsByIds(designPlanIds);
    }

    @Override
    public List<FullHouseDesignBo> getFullDesignPlans(Set<Long> designPlanIds) {
        return desinPlanRecommendedDao.getFullDesignPlans(designPlanIds);
    }
}
