package com.sandu.api.companyshop.service.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.companyshop.input.ShopidentificationAdd;
import com.sandu.api.companyshop.input.ShopidentificationQuery;
import com.sandu.api.companyshop.input.ShopidentificationUpdate;
import com.sandu.api.companyshop.model.Shopidentification;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * shop_identification
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-22 11:50
 */
public interface ShopidentificationBizService {

    /**
     * 创建
     *
     * @param input
     * @return
     */
    int create(ShopidentificationAdd input);

    /**
     * 更新
     *
     * @param input
     * @return
     */
    int update(ShopidentificationUpdate input);

    /**
     * 删除
     */
    int delete(String shopidentificationId);

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
     * @param query
     * @return
     */
    PageInfo<Shopidentification> query(ShopidentificationQuery query);

    /**
     * 通过店铺ID获取详情
     * @param shopId
     * @return
     */
    Shopidentification getByShopId(int shopId);
}
