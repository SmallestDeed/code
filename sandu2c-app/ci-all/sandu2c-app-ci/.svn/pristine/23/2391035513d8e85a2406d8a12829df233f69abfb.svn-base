package com.sandu.common.objectconvert.designplan;

import com.sandu.designplan.vo.DesignPlanStyleVo;
import com.sandu.system.model.SysDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengxuangang
 * @desc 设计方案风格对象转换
 * @date 20170925
 */

public class DesignPlanStyle {

    /**
     * 转换字典对象，过滤无用值
     *
     * @param sysDictionaryList 字典数据List
     * @param isReverseOrder    是否需要逆序排序(default:否)
     * @return
     */
    public static List<DesignPlanStyleVo> parseToDesignPlanStyleVoList(List<SysDictionary> sysDictionaryList, boolean isReverseOrder) {

        List<DesignPlanStyleVo> designPlanStyleVoList = null;
        if (null != sysDictionaryList) {
            //初始化对象
            designPlanStyleVoList = new ArrayList<>(sysDictionaryList.size());
            DesignPlanStyleVo designPlanStyleVo = null;
            if (isReverseOrder) {
                //逆序
                for (int listLength = sysDictionaryList.size(); listLength > 0; listLength--) {
                    SysDictionary sysDictionary = sysDictionaryList.get(listLength - 1);
                    designPlanStyleVo = new DesignPlanStyleVo();
                    //装入数据
                    designPlanStyleVo.setStyleName(sysDictionary.getName());
                    designPlanStyleVo.setStyleCode(sysDictionary.getValue());
                    designPlanStyleVoList.add(designPlanStyleVo);
                }
            } else {
                //正序
                for (SysDictionary sysDictionary : sysDictionaryList) {
                    designPlanStyleVo = new DesignPlanStyleVo();
                    //装入数据
                    designPlanStyleVo.setStyleName(sysDictionary.getName());
                    designPlanStyleVo.setStyleCode(sysDictionary.getValue());
                    designPlanStyleVoList.add(designPlanStyleVo);
                }
            }
        }
        return designPlanStyleVoList;
    }

    /**
     * 转换字典对象，过滤无用值
     *
     * @param sysDictionaryList 字典数据List
     * @return
     */
    public static List<DesignPlanStyleVo> parseToDesignPlanStyleVoList(List<SysDictionary> sysDictionaryList) {
        return parseToDesignPlanStyleVoList(sysDictionaryList, false);
    }
}
