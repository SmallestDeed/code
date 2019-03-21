package com.sandu.im.dao;

import com.sandu.im.model.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DesignPlanDao {
    List<DesignPlanInfo> selectSingleDesinPlanInfo(@Param("singlePlanIds") Set<Integer> singlePlanIds);

    ResRenderPic selectResRenderCoverPicByDesignSceneId(Integer businessId);

    String selectDesianPlanArea(Integer templateId);

    List<DesignPlanRecommendedResult> selectselectFullHouseDesignPlanDetail(@Param("fullHouseIds")Set<Integer> fullHouseIds);

//    List<DesignPlanRecommendedResult> getDesignPlanRecommendedInfoList(@Param("singleDesignPlanIds")Set<Integer> singleDesignPlanIds);

    DesignPlanRecommendedResult getFullHouseInfo(Integer fullHouseId);

    DesignPlanRenderScene selectDesignPlanRenderSceneInfo(int planId);

    FullHouseMainTaskInfo selectMianTaskInfo(Integer fullHouseId);

    String getFullHouseCoverPicture(int fullHouseId);

    List<ResRenderPic> selectResRenderCoverPics(@Param("businessIds") Set<Integer> businessIds);
}
