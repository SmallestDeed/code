package com.sandu.api.product.service;

import com.sandu.api.product.model.ProductProp;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Sandu
 * @date 2017/12/18
 */
public interface ProductPropService {
    /**
     * 保存属性
     * @param productProp
     * @return
     */
    int saveProductProp(ProductProp productProp);

    /**
     * 删除属性
     * @param id
     * @return
     */
    int deleteProductPropById(long id);

    /**
     * 更新属性
     * @param productProp
     */
    void updateProductProp(ProductProp productProp);

    /**
     * 获取属性详情
     * @param id
     * @return
     */
    ProductProp getProductPropInfoById(long id);

    /**
     * 获取子节点
     * @param pid
     * @return
     */
    List<ProductProp> getProductPropByPid(int pid);

    /**
     * 获取产品下的所有属性
     * @param productId
     * @return
     */

    List<ProductProp> getProductPropByProductId(int productId);

    /**
     * 根据longCode 加载属性
     * @param longCode
     * @return
     */
    List<ProductProp> getProductPropByLongCode(String longCode);

    /**
     * 根据子节点查询父节点及自身节点信息
     * @param ids
     * @return
     */
    List<ProductProp> getParentAndItselfByIds(List<Integer> ids);

    Map<String, List<ProductProp>> getAllParentByCodes(List<String> code);



}
