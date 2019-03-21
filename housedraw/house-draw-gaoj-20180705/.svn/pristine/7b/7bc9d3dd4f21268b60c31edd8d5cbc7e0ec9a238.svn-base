package com.sandu.service.product.dao;

import java.util.List;

import com.sandu.api.product.model.BaseProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.house.bo.DrawBaseProductBO;
import com.sandu.api.house.model.DrawBaseProduct;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/29
 */
@Repository
public interface DrawBaseProductMapper {

    List<DrawBaseProductBO> listBaseProductBo(@Param("spaceIds") List<Long> spaceIds, @Param("createProductStatus") Integer createProductStatus);

    List<DrawBaseProduct> listDrawBaseProductByIds(@Param("ids") List<Long> ids);

    Long saveBaseProduct(DrawBaseProduct baseProduct);

    void updateBaseProduct(@Param("resModel") Long resModelId, @Param("productId") Long productId,
                           @Param("type")String fileType);

    Long saveDrawBaseProduct(DrawBaseProduct product);

    void batchDelete(@Param("productIds") List<Long> productIds);

	DrawBaseProduct selectByPrimaryKey(Long id);
	
	Integer updateByPrimaryKeySelective(DrawBaseProduct record);

	List<DrawBaseProduct> findAllByDrawDesignTempletId(@Param("drawDesignTempletId") Long drawDesignTempletId);

	void transformToBaseProduct(DrawBaseProduct drawBaseProduct);

	void insertSelective(DrawBaseProduct drawBaseProduct);

	Integer findBaseProductByProductCode(String productCode);

	List<Long> getDeleteDrawBaseProductId(@Param("emptySpaces")List<Long> emptySpaces);

	Integer deleteDrawBaseProduct(@Param("status")Integer status, @Param("baseProducts")List<Long> baseProducts);

	Integer deleteBaseProduct(@Param("status")Integer status, @Param("baseProducts")List<Long> baseProducts);

	List<Long> getDeleteBaseProductId(Long drawSpaceId);

	List<Long> getEmptyBaseProduct(@Param("emptySpaces")List<Long> emptySpaces);

	Integer emptyBaseProduct(@Param("status")Integer status, @Param("baseProducts")List<Long> baseProducts);

    Integer batchSaveDrawBaseProduct(List<DrawBaseProduct> drawBaseProducts);

    Integer batchUpdateDrawBaseProduct(@Param("products") List<DrawBaseProduct> drawBaseProducts);

    List<BaseProduct> listProductByProductCode(List<String> productCodes);

	Integer updateBatchDrawBaseProduct(List<DrawBaseProduct> drawProducts);
}
