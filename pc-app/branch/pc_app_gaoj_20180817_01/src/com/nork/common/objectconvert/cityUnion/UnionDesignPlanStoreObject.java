package com.nork.common.objectconvert.cityUnion;

import com.nork.cityunion.model.UnionDesignPlanStore;
import com.nork.cityunion.model.vo.UnionDesignPlanStoreVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoxc on 2018/1/17 0017.
 */
public class UnionDesignPlanStoreObject {

    /**
     * 获取设计方案素材库列表
     * 转换voList
     * @param planStoreList
     * @return
     */
    public static List<UnionDesignPlanStoreVO> parseToPlanStoreVOListByPlanStoreList(List<UnionDesignPlanStore> planStoreList){

        List<UnionDesignPlanStoreVO>  planStoreVOList = new ArrayList<>();
        if (planStoreList == null || planStoreList.size() == 0) {
            return  planStoreVOList;
        }
        for (UnionDesignPlanStore planStore : planStoreList) {
            UnionDesignPlanStoreVO planStoreVO = new UnionDesignPlanStoreVO();
            planStoreVO.setDesignPlanStoreId(planStore.getId());
            planStoreVO.setDesignPlanId(planStore.getDesignPlanId());
            planStoreVO.setDesignPlanName(planStore.getDesignPlanName());
            planStoreVO.setRenderPicId(planStore.getRenderPicId());
            planStoreVO.setPicSmallPath(planStore.getPicSmallPath());
            planStoreVOList.add(planStoreVO);
        }
        return planStoreVOList;
    }
}
