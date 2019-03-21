package com.sandu.service.shop.impl;

import com.sandu.api.shop.model.CompanyShopDesignPlan;
import com.sandu.api.shop.service.CompanyShopDesignPlanService;
import com.sandu.service.shop.dao.CompanyShopDesignPlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/8/30  17:01
 */
@Service("companyShopDesignPlanService")
public class CompanyShopDesignPlanServiceImpl implements CompanyShopDesignPlanService {

    @Autowired
    private CompanyShopDesignPlanDao companyShopDesignPlanDao;

    @Override
    public Integer deleteDesignPlanByShopId(List<Integer> shopIds) {
        return companyShopDesignPlanDao.deleteDesignPlanByShopId(shopIds);
    }

    @Override
    public Integer getDesignPlanByShopId(List<Integer> shopIds) {
        return companyShopDesignPlanDao.getDesignPlanByShopId(shopIds);
    }

    @Override
    public Integer updateMainShopDesignStatus(Integer mainShopId, Integer shopId, Integer status) {
        return companyShopDesignPlanDao.updateMainShopDesignStatus(mainShopId, shopId, status);
    }
}
