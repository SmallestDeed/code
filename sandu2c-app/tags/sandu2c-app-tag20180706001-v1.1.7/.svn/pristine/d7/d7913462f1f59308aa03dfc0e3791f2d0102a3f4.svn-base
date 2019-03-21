package com.sandu.designplan.service;

import com.sandu.design.model.DesignPlanProduct;
import com.sandu.design.model.ProductsCost;
import com.sandu.design.model.ProductsCostType;
import com.sandu.designplan.model.DesignPlanProductRenderScene;
import com.sandu.designplan.model.DesignPlanProductResult;
import com.sandu.product.model.ProductCostDetail;
import com.sandu.user.model.UserSO;

import java.util.List;

/**
 * @author huangsongbo
 * @desc: 设计方案产品渲染场景服务
 * copy from DesignPlanProductService
 */
public interface DesignPlanProductRenderSceneService {

    List<DesignPlanProductResult> getDesignPlanProductList(DesignPlanProductRenderScene designPlanProductRenderScene);
    /**
     * List<DesignPlanProduct> -> List<DesignPlanProductEctype>
     * @author huangsongbo
     * @param designPlanProductList
     * @param planId
     * @return
     */
    boolean copyFromDesignPlanProductList(List<DesignPlanProduct> designPlanProductList, long planId);

    /**
     * 添加设计方案产品副本数据
     * @author huangsongbo
     * @param designPlanProductRenderScene
     */
    void add(DesignPlanProductRenderScene designPlanProductRenderScene);

    /**
     * 批量添加设计方案产品副本数据
     * @author huangsongbo
     * @param designPlanProductRenderSceneList
     */
    void add(List<DesignPlanProductRenderScene> designPlanProductRenderSceneList);

    /**
     * 通过planId删除设计方案产品表(副本)数据
     * @author huangsongbo
     * @param id
     */
    void deleteByPlanId(Integer id);

    /**
     * 通过设计方案id(副本)获取设计方案产品表(副本)
     * @author huangsongbo
     * @param planId
     * @return
     */
    List<DesignPlanProductRenderScene> getListByPlanId(Integer planId);
    /**
     *
     *通过副本id获得产品数量（副本产品表）
     * @Author yanghz
     * @create
     */
    int getProductCount(Integer designPlanId);

    /**
     * 设计方案产品汇总
     * @author huangsongbo
     * @param designPlanProductRenderScene
     * @return
     */
    int planProductCount(DesignPlanProductRenderScene designPlanProductRenderScene);

    /**
     * 获得设计方案产品列表副本
     * @author huangsongbo
     * @param designPlanProductRenderScene
     * @return
     */
    List<DesignPlanProductResult> planProductList(DesignPlanProductRenderScene designPlanProductRenderScene);

    /**
     * 获取设计方案副本费用列表
     * @param userSo
     * @param designPlanProductRenderScene
     */
    List<ProductsCostType> costList(UserSO userSo, DesignPlanProductRenderScene designPlanProductRenderScene);

    int costTypeListCount(DesignPlanProductRenderScene designPlanProductRenderScene);

    /**
     * 结算类型汇总清单
     * @param designPlanProductRenderScene
     * @return
     */
    List<ProductsCostType> costTypeList(DesignPlanProductRenderScene designPlanProductRenderScene);

    /**
     * 结算汇总清单
     * @return
     */
    List<ProductsCost> costList(ProductsCostType productsCostType);

    /**
     * 结算清单明细
     * @param cost
     * @return
     */
    List<ProductCostDetail> costDetail(ProductsCost cost);

    int costTypeListCount(DesignPlanProduct designPlanProduct);

    List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct);
}
