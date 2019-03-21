package com.sandu.service.companyshop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.companyshop.input.ShopidentificationQuery;
import com.sandu.api.companyshop.model.Shopidentification;
import com.sandu.api.companyshop.service.ShopidentificationService;
import com.sandu.service.companyshop.dao.ShopidentificationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * shop_identification
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-22 11:50
 */
@Slf4j
@Service("shopidentificationService")
public class ShopidentificationServiceImpl implements ShopidentificationService {

    @Autowired
    private ShopidentificationMapper shopidentificationMapper;

    @Override
    public int insert(Shopidentification shopidentification) {
        int result = shopidentificationMapper.insert(shopidentification);
        if (result > 0) {
            return shopidentification.getId();
        }
        return 0;
    }

    @Override
    public int update(Shopidentification shopidentification) {
        return shopidentificationMapper.updateByPrimaryKey(shopidentification);
    }

    @Override
    public int delete(Set<Integer> shopidentificationIds) {
        return shopidentificationMapper.deleteByPrimaryKey(shopidentificationIds);
    }

    @Override
    public Shopidentification getById(int shopidentificationId) {
        return shopidentificationMapper.selectByPrimaryKey(shopidentificationId);
    }

    @Override
    public PageInfo<Shopidentification> findAll(ShopidentificationQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        return new PageInfo<>(shopidentificationMapper.findAll(query));
    }

    @Override
    public Shopidentification getByShopId(int shopId) {
        return shopidentificationMapper.getByShopId(shopId);
    }
}
