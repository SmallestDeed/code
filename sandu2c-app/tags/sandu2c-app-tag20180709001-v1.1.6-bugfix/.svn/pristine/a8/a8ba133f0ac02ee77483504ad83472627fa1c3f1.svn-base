package com.sandu.designplan.service;

import com.sandu.design.model.DesignPlanProduct;
import com.sandu.design.model.ProductsCostType;
import com.sandu.user.model.UserSO;

import java.util.List;

/**
 * @version V1.0
 * @Title: DesignPlanProductService.java
 * @Package com.sandu.design.service
 * @Description:设计方案-设计方案产品库Service
 * @createAuthor pandajun
 * @CreateDate 2015-06-26 11:26:11
 */
public interface DesignPlanProductService {
    /**
     * 新增数据
     *
     * @param designPlanProduct
     * @return int
     */
    int add(DesignPlanProduct designPlanProduct);

    /**
     * 更新数据
     *
     * @param designPlanProduct
     * @return int
     */
    int update(DesignPlanProduct designPlanProduct);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return DesignPlanProduct
     */
    DesignPlanProduct get(Integer id);
    
    /**
     * 获取设计方案费用列表
     *
     * @param designPlanProduct
     */
    List<ProductsCostType> costList(DesignPlanProduct designPlanProduct, costListEnum type, UserSO userSo);
    
    /**
	 * 所有数据
	 * 
	 * @param  designPlanProduct
	 * @return   List<DesignPlanProduct>
	 */
    List<DesignPlanProduct> getList(DesignPlanProduct designPlanProduct);

    enum costListEnum {
        designPlan, designPlanRenderScene, designPlanRecommended, oneKeyDesignPlan
    }
}
