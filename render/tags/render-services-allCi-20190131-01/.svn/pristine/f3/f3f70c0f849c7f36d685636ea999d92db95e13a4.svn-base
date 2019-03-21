package com.nork.product.service;

import com.nork.common.exception.BizException;

public interface CommonAppSearchService {
    /**
     * 根据全铺长度,获取产品组里最匹配的产品信息(长度最接近)
     *
     * @param productId
     * @param designPlanProductId
     * @return
     * @throws BizException
     */
    Integer getPracticableProductId(Integer productId, Integer designPlanProductId) throws BizException;

    Integer getPracticableProductIdByProductIdAndFullPaveLength(Integer productId, Integer fullPaveLength)
            throws BizException;

}
