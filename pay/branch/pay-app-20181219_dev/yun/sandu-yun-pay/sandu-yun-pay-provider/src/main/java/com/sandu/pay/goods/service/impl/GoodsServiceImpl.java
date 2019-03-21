package com.sandu.pay.goods.service.impl;

import com.sandu.pay.goods.dao.GoodsMapper;
import com.sandu.pay.goods.model.Goods;
import com.sandu.pay.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017/9/21.
 */
@Service("goodsService")
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 从数据字典中获取商品信息（暂时从数据字典获取，后期商品信息独立成goods表）
     * @param dicType   数据字典type
     * @param value     数据字典VALUE
     * @return  商品信息
     */
    @Override
    public Goods getGoodsInfo(String dicType, Integer value) {
        return goodsMapper.getGoodsInfo(dicType, value);
    }

    /**
     * 从数据字典中获取商品信息（正版，后期开放）
     * @param goodsId   商品ID
     * @return  商品信息
     */
    @Override
    public Goods getGoodsInfo(Integer goodsId) {
        return goodsMapper.getGoodsInfo(goodsId);
    }
}
