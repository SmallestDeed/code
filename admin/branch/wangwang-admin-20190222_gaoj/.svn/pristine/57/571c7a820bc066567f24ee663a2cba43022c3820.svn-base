package com.sandu.service.product.impl;

import com.sandu.api.product.model.BaseGoodsSpu;
import com.sandu.api.product.service.BaseGoodsSpuService;
import com.sandu.service.product.dao.BaseGoodsSpuDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("baseGoodsSpuService")
public class BaseGoodsSpuServiceImpl implements BaseGoodsSpuService {
    @Resource
    private BaseGoodsSpuDao baseGoodsSpuDao;

    @Override
    public void insertGoodSpu(BaseGoodsSpu spu) {
        baseGoodsSpuDao.insertBaseGoodsSpu(spu);
    }


    @Override
    public BaseGoodsSpu getById(Integer id) {
        return baseGoodsSpuDao.getById(id);
    }

    @Override
    public String getMaxSpuId() {
        return baseGoodsSpuDao.getMaxId();
    }
}
