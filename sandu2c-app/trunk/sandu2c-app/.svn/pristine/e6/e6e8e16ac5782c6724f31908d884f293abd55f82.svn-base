package com.sandu.goods.service;


import com.sandu.goods.input.GoodsSkuQuery;
import com.sandu.goods.model.BaseGoodsSKU;
import com.sandu.goods.output.GoodsSkuVO;

import java.util.List;
import java.util.Map;

public interface BaseGoodsSKUService
{
    GoodsSkuVO getSkuInfoByAttrs(GoodsSkuQuery goodsSkuQuery);

    List<Integer> getProductIdsBySpuId(Integer spuId);

    Integer changeInventory(Integer id,Integer num);

    List<BaseGoodsSKU> getListByProductIdList(List<Integer> productIdList);

    Map<Integer, String> mapGoodsSKU(List<Long> listSku);
}
