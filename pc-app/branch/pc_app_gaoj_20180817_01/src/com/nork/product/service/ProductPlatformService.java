package com.nork.product.service;

import com.nork.design.model.DesignPlanProductResult;
import com.nork.design.model.DesignPlanRecommendedProductResult;
import com.nork.design.model.ProductCostDetail;
import com.nork.product.model.*;
import com.nork.product.model.result.SearchProductGroupResult;

import java.math.BigDecimal;

/**
 * @Author chenqiang
 * @Description 产品平台信息service
 * @Date 2018/5/22 0022 13:55
 * @Modified By
 */
public interface ProductPlatformService {

    /**
     * 获取单品产品平台个性化数据
     * @author chenqiang
     * @param productId
     * @return
     */
    ProductPlatform getProductPlatformInfo(int productId, String platformType);

    /**
     * 获取组合单品产品平台个性化数据
     * @author chenqiang
     * @param productId
     * @return
     */
    ProductPlatform getGroupProductPlatformInfo(int productId, String platformType);

    /**
     * 获取品价格单位名称
     * @param advicePrice 产品建议售价
     * @return
     */
    String getSalePriceValueName(String advicePrice);

    /**
     * 获取组合产品价格总价
     * @param groupId 组合产品id
     * @return 价格
     */
    BigDecimal getGroupSalePrice(Integer groupId);

    /**
     * 获取产品图片、缩略图列表
     * @param picIds
     * @param picId
     * @param productId
     * @return 返回产品平台个性信息实体
     */
    ProductPlatform getProductPicList(String picIds,Integer picId,Integer productId);

    /**
     * 获取产品风格名与列表
     * @param styleIds 平台风格ids
     * @param productId 单品产品id
     * @param groupId 组合产品id
     * @return 返回产品平台个性信息实体
     */
    ProductPlatform getProductStyleInfo(String styleIds,Integer productId,Integer groupId);




    /**
     * 草图方案/效果图方案 产品列表
     * @param planProduct
     * @return DesignPlanProductResult
     */
    void getDesignPlanProductResultInfo(int productId,String platformType,DesignPlanProductResult planProduct);

    /**
     * 推荐方案 产品列表
     * @param planProduct
     * @return DesignPlanRecommendedProductResult
     */
    void getDesignPlanRecommendedProductResultInfo(int productId,String platformType,DesignPlanRecommendedProductResult planProduct);


    /**
     * 单品产品详情
     * @param baseProduct
     * @return BaseProduct
     */
    void getBaseProductInfo(int productId,String platformType,BaseProduct baseProduct);


    /**
     * 组合产品详情
     * @param groupProductResult
     * @return BaseProduct
     */
    void getGroupProductResultInfo(int productId,String platformType,SearchProductGroupResult groupProductResult);

    /**
     * 方案已删除产品列表
     * @param productId
     * @param platformType
     * @param baseProduct
     */
    void getDeleteBaseProductInfo(int productId,String platformType,BaseProduct baseProduct);

    /**
     * 方案已使用产品单品列表
     * @param productId
     * @param platformType
     * @param categoryProductResult
     */
    void getUseCategoryProductResultInfo(int productId,String platformType,CategoryProductResult categoryProductResult);

    /**
     * 方案已使用产品组合列表
     * @param groupId
     * @param platformType
     * @param searchProductGroupResult
     */
    void getUseSearchProductGroupResultInfo(int groupId,String platformType,SearchProductGroupResult searchProductGroupResult);


    /**
     * 收藏 单品列表
     * @param productId
     * @param platformType
     * @param userProductsConllect
     */
    void getUserProductsConllectInfo(int productId,String platformType,UserProductsConllect userProductsConllect);

    /**
     * 收藏 组合列表
     * @param groupId
     * @param platformType
     * @param groupProductCollect 组合对象
     */
    void getGroupProductCollectInfo(int groupId,String platformType,GroupProductCollect groupProductCollect);

    /**
     * 结算清单
     * @param productId
     * @param platformType
     * @param productCostDetail
     */
    void getProductCostDetailInfo(int productId,String platformType,ProductCostDetail productCostDetail);


}
