package com.sandu.common.objectconvert.designplan;

import com.sandu.designplan.vo.SpaceShapeVo;
import com.sandu.system.model.ResPic;
import com.sandu.system.model.SysDictionary;

/**
 * @desc 空间形状对象
 * @date 20171016
 * @auth pengxuangang
 */
public class SpaceShapeObject {

    /**
     * 将系统字典数据和图片对象转换为空间形状VO对象
     *
     * @param sysDictionary 系统字典数据
     * @param resPic        图片信息
     * @return SpaceShapeVo 空间形状VO
     */
    public static SpaceShapeVo parseToSpaceShapeVoBySysDictionaryAndPicDate(SysDictionary sysDictionary, ResPic resPic) {

        //创建对象
        SpaceShapeVo spaceShapeVo = null;

        if (null != sysDictionary) {
            //初始化对象
            spaceShapeVo = new SpaceShapeVo();
            spaceShapeVo.setShapeId(sysDictionary.getValue());
            spaceShapeVo.setShapeName(sysDictionary.getName());
            if (null != resPic) {
                spaceShapeVo.setShapePicAddress(resPic.getPicPath());
            }
        }

        return spaceShapeVo;
    }
}
