package com.sandu.api.product.service;

import java.util.List;
import java.util.Map;

import com.sandu.api.house.bo.DrawBaseProductBO;
import com.sandu.api.house.bo.UserLoginBO;
import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
import com.sandu.api.house.model.DrawBaseProduct;
import com.sandu.api.product.model.BaseProduct;
import com.sandu.exception.DrawBusinessException;

/**
 * Description: 产品接口类
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/29
 */
public interface DrawBaseProductService {

    /**
     * 根据空间id获取产品部分信息及产品对应的文件信息
     *
     * @param spaceIds            空间id集合
     * @param createProductStatus draw_design_templet_product中的create_product_status字段
     *                            户型绘制时,该产品是不是软装白膜(系统中已经存在该产品,无需再新建该产品,0:需要新建,1:不需要新建)
     * @return
     */
    List<DrawBaseProductBO> listBaseProductBo(List<Long> spaceIds, Integer createProductStatus);

    /**
     * 空间下的所有产品详情
     *
     * @param ids 空间id集合
     * @return
     */
    List<DrawBaseProduct> listDrawBaseProductByIds(List<Long> ids);

    /**
     * 保存产品
     *
     * @param drawBaseProduct 产品对象
     */
    Long saveBaseProduct(DrawBaseProduct drawBaseProduct);

    /**
     * 烘焙回调更新模型与产品关系
     *
     * @param resModelId
     * @param productId
     * @param fileType   文件类型
     */
    void updateProductModel(Long resModelId, Long productId, String fileType);

    /**
     * 保存户型绘制产品
     *
     * @param product 产品
     */
    Long saveDrawBaseProduct(DrawBaseProduct product);

    /**
     * 批量删除产品
     *
     * @param productIds 产品id集合
     */
    void batchDeleteDrawBaseProduct(List<Long> productIds);

    /**
     * 绘制户型新增产品
     *
     * @param dtoNew
     * @param loginBO
     * @return
     * @throws DrawBusinessException
     * @author huangsongbo
     */
    DrawBaseHouseSubmitDTO saveDrawBaseProductBySubmit(DrawBaseHouseSubmitDTO dtoNew,
                                                       UserLoginBO loginBO) throws DrawBusinessException;

    DrawBaseProduct get(Long id);

    void update(DrawBaseProduct drawBaseProduct);

    List<DrawBaseProduct> findAllByDrawDesignTempletId(Long drawDesignTempletId);

    Map<Long, Long> transformToBaseProduct(List<DrawBaseProduct> drawBaseProductList) throws DrawBusinessException;

    Long add(DrawBaseProduct drawBaseProduct);

    List<Long> getDeleteDrawBaseProductId(List<Long> emptySpaces);

    Integer deleteDrawBaseProduct(Integer status, List<Long> baseProducts);

    List<Long> getDeleteBaseProductId(Long drawSpaceId);

    Integer deleteBaseProduct(Integer status, List<Long> baseProducts);

    String getProductCode(String typeValueKey) throws DrawBusinessException;

    List<Long> getEmptyBaseProduct(List<Long> emptySpaces);

    Integer emptyBaseProduct(Integer status, List<Long> baseProducts);

    Integer batchUpdateDrawBaseProduct(List<DrawBaseProduct> drawBaseProducts);

    List<BaseProduct> listProductByProductCode(List<String> productCodes);
}
