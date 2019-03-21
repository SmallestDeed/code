package com.sandu.common.objectconvert.designplan;

import com.sandu.common.model.ResponseEnvelope;
import com.sandu.designplan.model.DesignPlanRecommendedResult;
import com.sandu.designplan.model.DesignPlanRecommendedVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 设计方案推荐
 */
public class DesignPlanRecommend {

    public static ResponseEnvelope parseToDesignPlanRecommendedVoList(ResponseEnvelope recommendedResult) {
        ResponseEnvelope recommendedVo = new ResponseEnvelope();

        if (null != recommendedResult) {
            //获取List---------------------Obj
            List<DesignPlanRecommendedResult> datalist = (List<DesignPlanRecommendedResult>) recommendedResult.getObj();
            if (null != datalist) {
                List<DesignPlanRecommendedVo> designPlanRecommendedVoList = new ArrayList<>(datalist.size());
                DesignPlanRecommendedVo designPlanRecommendedVo = null;
                for (DesignPlanRecommendedResult designPlanRecommendedResult : datalist) {
                    designPlanRecommendedVo = new DesignPlanRecommendedVo();
                    designPlanRecommendedVo.setDesignPlanName(designPlanRecommendedResult.getPlanName());
                    designPlanRecommendedVo.setDesignPlanId(designPlanRecommendedResult.getPlanId());
                    designPlanRecommendedVo.setDesignPlanRecommendId(designPlanRecommendedResult.getPlanRecommendedId());
                    if(designPlanRecommendedResult.getResRenderPicPath()==null){
                        designPlanRecommendedVo.setDesignPlanCoverPath(designPlanRecommendedResult.getCoverPath());
                    }else{
                        designPlanRecommendedVo.setDesignPlanCoverPath(designPlanRecommendedResult.getResRenderPicPath());
                    }
                    designPlanRecommendedVo.setSpaceType(designPlanRecommendedResult.getSpaceFunctionId());
                    designPlanRecommendedVo.setSpaceArea(designPlanRecommendedResult.getSpaceAreas());
                    designPlanRecommendedVo.setSpaceShape(designPlanRecommendedResult.getSpaceShape());
                    designPlanRecommendedVo.setIsFavorite(designPlanRecommendedResult.getIsFavorite());
                    designPlanRecommendedVo.setSpaceStyleName(designPlanRecommendedResult.getStyleName());
                    designPlanRecommendedVo.setRenderCount(designPlanRecommendedResult.getRenderCount());
                    designPlanRecommendedVo.setReleaseTime(designPlanRecommendedResult.getReleaseTime());
                    designPlanRecommendedVo.setIsLike(designPlanRecommendedResult.getIsLike());
                    designPlanRecommendedVo.setLikeNum(designPlanRecommendedResult.getLikeNum());
                    designPlanRecommendedVo.setCollectNum(designPlanRecommendedResult.getCollectNum());
                    designPlanRecommendedVo.setApplySpaceAreas(designPlanRecommendedResult.getApplySpaceAreas());
                    designPlanRecommendedVo.setGroupPrimaryId(designPlanRecommendedResult.getGroupPrimaryId());
                    designPlanRecommendedVo.setPlanDecoratePriceList(designPlanRecommendedResult.getPlanDecoratePriceList());

                    designPlanRecommendedVoList.add(designPlanRecommendedVo);
                   
                }
                //数据过滤后重装List
                recommendedVo.setObj(designPlanRecommendedVoList);
            }

            //重装total-------------------TotalCount
            recommendedVo.setTotalCount(recommendedResult.getTotalCount());
            recommendedVo.setStatus(recommendedResult.isStatus());
        }

        return recommendedVo;
    }
}
