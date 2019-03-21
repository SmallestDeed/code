package com.sandu.product.service;


import com.sandu.design.model.ProductsCostType;
import com.sandu.pano.model.scene.PanoramaVo;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 10:34 2018/5/23 0023
 * @Modified By:
 */
public interface MobileDesignPlanProductService {


    /**
     * 查询所有c端方案产品列表
     * @param panoramaVo
     * @return
     */
    List<ProductsCostType> getDesignPlanProductList2C(PanoramaVo panoramaVo);


    /**
     * 查询移动端B端方案产品列表
     * @param panoramaVo
     * @return
     */
    List<ProductsCostType> getDesignPlanProductList2B(PanoramaVo panoramaVo);
}
