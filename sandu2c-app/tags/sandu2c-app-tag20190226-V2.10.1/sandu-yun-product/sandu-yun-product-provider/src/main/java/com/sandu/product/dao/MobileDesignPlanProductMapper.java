package com.sandu.product.dao;

import com.sandu.product.model.MobileDesignPlanProduct;
import com.sandu.product.model.ProductCostDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 10:57 2018/5/23 0023
 * @Modified By:
 */
@Repository
public interface MobileDesignPlanProductMapper {

    //获取效果图方案的产品清单
    List<ProductCostDetail> getRenderSceneProductList(MobileDesignPlanProduct designPlanProduct);

    //获取推荐方案的产品清单
    List<ProductCostDetail> getRecommendedProductList(MobileDesignPlanProduct designPlanProduct);

    //获取草图方案的产品清单
    List<ProductCostDetail> getDesignPlanProductList(MobileDesignPlanProduct designPlanProduct);
}
