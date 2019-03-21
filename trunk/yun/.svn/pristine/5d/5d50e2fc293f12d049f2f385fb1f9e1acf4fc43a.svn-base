package com.sandu.pay.goods.dao;

import com.sandu.pay.goods.model.Goods;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/9/21.
 */
@Repository
public interface GoodsMapper {

    /**
     * 从数据字典中获取商品信息（暂时使用，后期商品信息独立成goods表）
     * @param dicType   数据字典type
     * @param value     数据字典VALUE
     * @return  商品信息
     */
    Goods getGoodsInfo(String dicType, Integer value);

    /**
     * 从数据字典中获取商品信息（正版，后期开放）
     * @param goodsId   商品ID
     * @return  商品信息
     */
    Goods getGoodsInfo(Integer goodsId);

}
