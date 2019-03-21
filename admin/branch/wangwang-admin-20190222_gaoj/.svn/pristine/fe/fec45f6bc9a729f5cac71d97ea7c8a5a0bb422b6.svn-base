package com.sandu.api.companyshop.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.companyshop.input.ShopidentificationQuery;
import com.sandu.api.companyshop.model.Shopidentification;

import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * shop_identification
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-22 11:50
 */
public interface ShopidentificationService {

    /**
     * 插入
     *
     * @param shopidentification
     * @return
     */
    int insert(Shopidentification shopidentification);

    /**
     * 更新
     *
     * @param shopidentification
     * @return
     */
    int update(Shopidentification shopidentification);

    /**
     * 删除
     *
     * @param shopidentificationIds
     * @return
     */
    int delete(Set<Integer> shopidentificationIds);

    /**
     * 通过ID获取详情
     *
     * @param shopidentificationId
     * @return
     */
    Shopidentification getById(int shopidentificationId);

    /**
     * 查询列表
     *
     * @return
     */
    PageInfo<Shopidentification> findAll(ShopidentificationQuery query);

    /**
     * 通过shopID获取详情
     * @param shopId
     * @return
     */
    Shopidentification getByShopId(int shopId);
}
