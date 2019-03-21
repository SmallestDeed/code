package com.nork.mobile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.design.model.PlanRecommendedListModel;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.mobile.service.MobileDesignPlanRecommendedService;

@Service("mobileDesignPlanRecommendedService")
public class MobileDesignPlanRececommendedServiceImpl implements MobileDesignPlanRecommendedService {

    @Autowired
    private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;

    /**
     * 获取设计方案推荐列表
     */
    @Override
    public Object getPlanRecommendedList(String creator, String brandName, String houseType,
                                         String livingName, String areaValue, String designRecommendedStyleId, String isMainList, String msgId,
                                         LoginUser loginUser, Integer limit, Integer start, Integer templateId, String planName, Integer platformId) {

        PlanRecommendedListModel model = new PlanRecommendedListModel();
        model.setCreator(creator);
        model.setBrandName(brandName);
        model.setHouseType(houseType);
        model.setLivingName(livingName);
        model.setAreaValue(areaValue);
        model.setDesignRecommendedStyleId(designRecommendedStyleId);
        model.setDisplayType(isMainList);
        model.setMsgId(msgId);
        model.setLoginUser(loginUser);
        model.setLimit(limit);
        model.setStart(start);
        model.setTemplateId(templateId);
        model.setPlanName(planName);
        model.setPlatformId(platformId);

        return designPlanRecommendedServiceV2.getPlanRecommendedList(model);
    }

}
