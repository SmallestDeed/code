package com.sandu.api.product.service;

import com.sandu.api.product.model.ProductControl;

import java.util.List;

/**
 * @author Sandu
 */
public interface ProductControlService {
    /**
     * 批量更新产品的公开状态
     * @param productIds
     * @param secrecy   0:不公开,1:公开
     * @return
     */
    boolean batchChangeProductSecrecy(List<Integer> productIds, Integer secrecy);

    int saveProductControl(ProductControl productControl);

    int saveProductControlList(List<ProductControl> list);

    /**
     * 批量更新
     * @param list
     * @return
     */
    int batchUpdate(List<ProductControl> list);

    /**
     * 获取在此表中的数据(用于区分更新/插入)
     * @param productIds
     * @return
     */
    List<Integer> getExistProductByProductId(List<Integer> productIds);

    int insertList(List<ProductControl> list);

    ProductControl getByProductId(Long id);

    /**
     * 更新(如果不存在此数据则insert)
     * @param productControl
     * @return
     */
    int update(ProductControl productControl);

    void updateByProductId(ProductControl pc);
}
