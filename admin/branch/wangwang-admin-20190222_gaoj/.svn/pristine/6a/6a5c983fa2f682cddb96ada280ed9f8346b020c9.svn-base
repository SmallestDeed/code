package com.sandu.service.goods.dao;

import com.sandu.api.goods.input.GoodsDetailQuery;
import com.sandu.api.goods.model.BaseGoodsSKU;
import com.sandu.api.goods.model.bo.BaseGoodsSKUBO;
import com.sandu.api.goods.model.bo.BaseProductBO;
import com.sandu.api.goods.model.bo.ProductSKUBO;
import com.sandu.api.goods.model.bo.PutAwayBO;
import com.sandu.api.product.model.Product;
import org.apache.ibatis.annotations.Param;
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

    List<ProductSKUBO> getProductSKUsBySPUId(GoodsDetailQuery goodsDetailQuery);

    List<BaseGoodsSKUBO> selectBySPUId(Integer spuId);

    Integer updateByProductId(BaseGoodsSKU baseGoodsSKU);

    List<BaseProductBO> getBaseProductBySpuId(GoodsDetailQuery goodsDetailQuery);

    String getDashBoardConfigByAppId(String appId);

    Integer updateDashBoardConfigByAppId(@Param("appId") String appid,
                                         @Param("dashboardConfig") String dashboardConfig);
}