package com.sandu.optimizeplan.dao;

import com.sandu.design.model.DesignPlanProduct;
import com.sandu.design.model.ProductsCost;
import com.sandu.design.model.ProductsCostType;
import com.sandu.designplan.model.DesignPlan;
import com.sandu.product.model.ProductCostDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 一键装修流程优化分表
 * @author Xiaozunp
 *
 */
@Repository
public interface OptimizePlanMapper {

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
    
    DesignPlan selectByPrimaryKeyPlan(Integer id);
}
