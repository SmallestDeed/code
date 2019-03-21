package com.nork.common.objectconvert.cityUnion;

import com.nork.cityunion.model.UnionDesignPlanStoreCatory;
import com.nork.cityunion.model.vo.UnionDesignPlanStoreCatoryVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoxc on 2018/1/18 0018.
 */
public class UnionDesignPlanStoreCatoryObject {

    /**
     * 获取设计方案素材分类对象 （转换vo对象）
     * @param storeCatory
     * @return
     */
    public static UnionDesignPlanStoreCatoryVO paramToStoreCatoryVOByStoreCatory(UnionDesignPlanStoreCatory storeCatory) {

        UnionDesignPlanStoreCatoryVO storeCatoryVO = null;
        if (storeCatory != null) {
            storeCatoryVO = new UnionDesignPlanStoreCatoryVO();
            storeCatoryVO.setCatoryId(storeCatory.getId());
            storeCatoryVO.setCatoryName(storeCatory.getCatoryName());
        }
        return storeCatoryVO;
    }

    /**
     * 获取优惠活动列表 （转换vo list集合）
     * @param storeCatoryList
     * @return
     */
    public static List<UnionDesignPlanStoreCatoryVO> paramToStoreCatoryVOListByStoreCatoryList(List<UnionDesignPlanStoreCatory> storeCatoryList) {

        List<UnionDesignPlanStoreCatoryVO> storeCatoryVOList = new ArrayList<>();
        if (storeCatoryList == null || storeCatoryList.size() == 0) {
            return  storeCatoryVOList;
        }
        for (UnionDesignPlanStoreCatory storeCatory : storeCatoryList) {
            UnionDesignPlanStoreCatoryVO storeCatoryListVO = new UnionDesignPlanStoreCatoryVO();
            storeCatoryListVO.setCatoryId(storeCatory.getId());
            storeCatoryListVO.setCatoryName(storeCatory.getCatoryName());
            storeCatoryVOList.add(storeCatoryListVO);
        }
        return storeCatoryVOList;
    }

}
