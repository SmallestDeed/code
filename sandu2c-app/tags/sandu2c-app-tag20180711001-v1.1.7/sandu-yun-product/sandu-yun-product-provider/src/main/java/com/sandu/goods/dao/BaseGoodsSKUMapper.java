package com.sandu.goods.dao;

import com.sandu.goods.model.BO.GoodsSkuBO;
import com.sandu.goods.model.BO.ProductAttrBO;
import com.sandu.goods.model.BO.SkuPriceAndAttrBO;
import com.sandu.goods.model.BaseGoodsSKU;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseGoodsSKUMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BaseGoodsSKU record);

    int insertSelective(BaseGoodsSKU record);

    BaseGoodsSKU selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseGoodsSKU record);

    int updateByPrimaryKey(BaseGoodsSKU record);

    List<GoodsSkuBO> getSkusBySpuId(Integer spuId);

    List<Integer> getProductIdsBySpuId(Integer spuId);

    List<ProductAttrBO> getProductAttrBySpuId(Integer spuId);

    List<SkuPriceAndAttrBO> getSkuPriceAndAttr(Integer id);
}