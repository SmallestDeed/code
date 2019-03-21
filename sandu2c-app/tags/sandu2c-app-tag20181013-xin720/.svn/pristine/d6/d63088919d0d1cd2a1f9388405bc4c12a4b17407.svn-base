package com.sandu.optimizeplan.service;

import com.sandu.design.model.DesignPlanProduct;
import com.sandu.design.model.ProductsCost;
import com.sandu.design.model.ProductsCostType;
import com.sandu.designplan.model.DesignPlan;
import com.sandu.product.model.ProductCostDetail;

import java.util.List;

/**
 *  优化方案服务
 * ------此接口在运营网站V1版本没有，因为系统架构调整，不能通过其他Service直接调用Dao方法，所以新增接口从Service调用
 *
 * @date 20171113
 * @auth pengxuangang
 *
 */
public interface OptimizePlanService {

    /**
     * 根据设计方案id，获取designPlan表中的所有数据详情
     * @param id
     * @return  DesignPlan
     */
    DesignPlan getPlan(Integer id);

    int costTypeListCount(DesignPlanProduct designPlanProduct);

    /**
     * 结算类型汇总清单
     *
     * @param designPlanProduct
     * @return
     */
    List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct);

    /**
     * 结算汇总清单
     * @param productsCostType
     * @return
     */
    List<ProductsCost> costList(ProductsCostType productsCostType);

    /**
     * 结算清单明细
     * @param cost
     * @return
     */
    List<ProductCostDetail> costDetail(ProductsCost cost);

   
}
