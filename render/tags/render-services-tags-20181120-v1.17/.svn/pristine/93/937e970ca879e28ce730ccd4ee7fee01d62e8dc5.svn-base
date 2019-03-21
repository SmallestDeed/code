package com.nork.common.objectconvert.baseseries;

import com.nork.product.model.BaseSeries;
import com.nork.product.model.vo.BaseSeriesVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/11 0011.
 */
public class BaseSeriesObject {

    /**
     * 获取系列VO obj转换
     * @param baseSeries
     * @return
     */
    public static BaseSeriesVO parseToBaseSeriesVOByBaseServies(BaseSeries baseSeries){

        BaseSeriesVO baseSeriesVO = new BaseSeriesVO();
        if (null != baseSeries ) {
            baseSeriesVO.setSeriesId(baseSeries.getId());
            baseSeriesVO.setBrandName(baseSeries.getBrandName());
            baseSeriesVO.setPicPath(baseSeries.getPicPath());
            baseSeriesVO.setSeriesName(baseSeries.getName());
            baseSeriesVO.setRemark(baseSeries.getRemark());
        }

        return baseSeriesVO;
    }

}
