package com.sandu.designplan.dao;

import com.sandu.design.model.DesignPlanProduct;
import com.sandu.design.model.ProductsCost;
import com.sandu.design.model.ProductsCostType;
import com.sandu.designplan.model.DesignPlanProductRenderScene;
import com.sandu.designplan.model.DesignPlanProductResult;
import com.sandu.product.model.ProductCostDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * copy from DesignPlanProductMapper
 * 2017.7.18
 *
 * @author huangsongbo
 */
@Repository
public interface DesignPlanProductRenderSceneMapper {

    /**
     * 通过设计方案id(副本)获取设计方案产品表(副本)
     * @author huangsongbo
     * @param planId
     * @return
     */
    List<DesignPlanProductRenderScene> getListByPlanId(@Param("planId") Integer planId);

    /**
     * 新增一条数据
     * @author huangsongbo
     * @param designPlanProductRenderScene
     */
    void insert(DesignPlanProductRenderScene designPlanProductRenderScene);

    /**
     * 批量新增数据
     * @author huangsongbo
     * @param designPlanProductRenderSceneList
     */
    void insertList(@Param("designPlanProductRenderSceneList") List<DesignPlanProductRenderScene> designPlanProductRenderSceneList);

    /**
     * 通过设计方案id(副本)删除设计方案产品表数据(副本)
     * @author huangsongbo
     * @param planId
     */
    void deleteByPlanId(@Param("planId") Integer planId);

    /**
     * describe: 通过副本设计方案查询产品数量
     * creat_user: yanghz
     * creat_date: 2017-07-20
     * creat_time: 下午 07:11
     **/
    int getProductCount(@Param("planId") Integer planId);

    int planProductCount(DesignPlanProductRenderScene designPlanProductRenderScene);

    List<DesignPlanProductResult> planProductList(DesignPlanProductRenderScene designPlanProductRenderScene);
    List<DesignPlanProductResult> getDesignPlanProductList(DesignPlanProductRenderScene designPlanProductRenderScene);

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

    int costTypeListCountV2(DesignPlanProduct designPlanProduct);

    List<ProductsCostType> costTypeListV2(DesignPlanProduct designPlanProduct);

	Integer getProductCount(ProductCostDetail productCostDetail);
}
