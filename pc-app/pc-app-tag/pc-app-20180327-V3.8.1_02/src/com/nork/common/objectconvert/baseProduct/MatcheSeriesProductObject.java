package com.nork.common.objectconvert.baseProduct;

import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.result.MatcheSeriesProductResult;

/**
 * Created by Administrator on 2017/11/8 0008.
 * @desc 获取匹配系列产品对象
 * @author xiaoxc
 */
public class MatcheSeriesProductObject {

    /**
     * 获取系列产品属性
     * @param productInfo 匹配系列产品对象
     * @return
     */
    public static MatcheSeriesProductResult parseToMatcheSeriesProductResultByBaseProduct(CategoryProductResult productInfo){

        MatcheSeriesProductResult result = new MatcheSeriesProductResult();
        if (null != productInfo) {
            result.setProductCode(productInfo.getProductCode());
            result.setProductId(productInfo.getId());
            result.setProductTypeValue(productInfo.getProductTypeValue().toString());
            result.setProductSmallTypeValue(productInfo.getProductSmallTypeValue());
            result.setProductTypeCode(productInfo.getProductTypeCode());
            result.setProductSmallTypeCode(productInfo.getProductSmallTypeCode());
            result.setProductHeight(productInfo.getProductHeight());
            result.setProductLength(productInfo.getProductLength());
            result.setProductWidth(productInfo.getProductWidth());
            result.setU3dModelPath(productInfo.getU3dModelPath());
        }
        return result;
    }
}
