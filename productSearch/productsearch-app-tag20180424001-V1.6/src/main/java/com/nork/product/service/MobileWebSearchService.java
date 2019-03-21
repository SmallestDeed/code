package com.nork.product.service;

import com.nork.mobile.model.search.MobileSearchProductModel;

/**
 * Created by yangz on 2017/12/12.
 */
public interface MobileWebSearchService {

    /**
     * 移动端搜索单品的接口
     * @param model
     * @return
     */
    Object searchProduct(MobileSearchProductModel model, String mediaType,String style);

    /**
     * 分类查询接口供移动端调用
     * @return
     */
    Object searchProCategory(String type, Integer cid, String msgId);

    /**
     * 查询产品组合列表
     *
     * @author yangzhun
     * @param model
     * @return
     */
    Object searchProductGroup(MobileSearchProductModel model, String mediaType);

    /**
     * 查询同类产品的材质
     * @author yangzhun
     * @param productId
     * @param msgId
     * @param planProductId
     * @param userId
     * @return
     */
    Object findTextureOfSameTypeProduct(Integer productId, String msgId, Integer planProductId, Integer userId);

    /**
     * 根据材质id查询材质
     * @param id
     * @return
     */
    Object selectProductById(Integer id,boolean onlyDefault);

    /**
     * 根据材质id查询材质
     * @param id
     * @return
     */
    Object selectTextureById(Integer id);

}
