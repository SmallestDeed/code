package com.sandu.api.goods.service;

import com.sandu.api.goods.input.GoodsDetailQuery;
import com.sandu.api.goods.input.GoodsSKUListAdd;
import com.sandu.api.goods.model.BaseGoodsSKU;
import com.sandu.api.goods.model.bo.PutAwayBO;
import com.sandu.api.goods.output.GoodsSKUVO;

import java.util.List;

public interface BaseGoodsSKUService
{
    /**
     * @Author: zhangchengda
     * @Description: 获取sku信息
     * @Date: 16:15 2018/5/23
     */
    List<GoodsSKUVO> getGoodsSKUs(GoodsDetailQuery goodsDetailQuery);

    /**
     * @Author: zhangchengda
     * @Description: 更新sku信息
     * @Date: 16:15 2018/5/23
     */
    Integer updateGoodsSKU(GoodsSKUListAdd goodsSKUListAdd);
}
