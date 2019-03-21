package com.sandu.im.service;

import com.sandu.im.dao.DesignPlanDao;
import com.sandu.im.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DesignPlanService {

    @Autowired
    private DesignPlanDao designPlanDao;


    public List<DesignPlanInfo> selectSingleDesinPlanInfo(Set<Integer> singlePlanIds) {
        return designPlanDao.selectSingleDesinPlanInfo(singlePlanIds);
    }

    public ResRenderPic selectResRenderCoverPicByDesignSceneId(Integer businessId) {
       return designPlanDao.selectResRenderCoverPicByDesignSceneId(businessId);
    }

    public String selectDesianPlanArea(Integer templateId) {
        return designPlanDao.selectDesianPlanArea(templateId);
    }

    public List<DesignPlanRecommendedResult> selectselectFullHouseDesignPlanDetail(Set<Integer> fullHouseIds) {
       return designPlanDao.selectselectFullHouseDesignPlanDetail(fullHouseIds);
    }

    public DesignPlanRecommendedResult getFullHouseInfo(Integer fullHouseId) {
        return designPlanDao.getFullHouseInfo(fullHouseId);
    }

    public DesignPlanRenderScene selectDesignPlanRenderSceneInfo(int planId) {
        return designPlanDao.selectDesignPlanRenderSceneInfo(planId);
    }

    public FullHouseMainTaskInfo getMianTaskInfo(Integer fullHouseId) {
        return designPlanDao.selectMianTaskInfo(fullHouseId);
    }

    public String getFullHouseCoverPicture(int fullHouseId) {
        return designPlanDao.getFullHouseCoverPicture(fullHouseId);
    }

    public List<ResRenderPic> selectResRenderCoverPics(Set<Integer> businessIds) {
        return designPlanDao.selectResRenderCoverPics(businessIds);
    }
}
