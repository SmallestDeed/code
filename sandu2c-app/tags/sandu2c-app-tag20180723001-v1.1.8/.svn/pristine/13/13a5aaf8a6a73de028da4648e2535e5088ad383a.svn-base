package com.sandu.designplan.model;

import com.sandu.designplan.vo.DesignPlanAreaVo;
import com.sandu.system.model.SysDictionary;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 设计方案空间面积对象
 */
public class DesignPlanSpaceArea {

    /**
     * 转换系统字典值为设计方案空间面积对象List
     *
     * @param sysDictionaryList
     * @return
     */
    public static List<DesignPlanAreaVo> parseToDesignPlanAreaListFromSySDirection(List<SysDictionary> sysDictionaryList) {

        //待返回结果集
        List<DesignPlanAreaVo> designPlanAreaList = new ArrayList<>(sysDictionaryList.size());
        if (sysDictionaryList != null) {
            sysDictionaryList.stream().sorted((orgSysDictionary, nowSysDictionary) -> (orgSysDictionary.getOrdering() - nowSysDictionary.getOrdering())).collect(toList()).forEach(sysDictionary -> {
                DesignPlanAreaVo designPlanAreaVo = new DesignPlanAreaVo();
                //装入参数
                designPlanAreaVo.setAreaId(sysDictionary.getValue());
                designPlanAreaVo.setAreaName(sysDictionary.getName());
                //装入List
                designPlanAreaList.add(designPlanAreaVo);
            });
        }
        return designPlanAreaList;
    }
}
