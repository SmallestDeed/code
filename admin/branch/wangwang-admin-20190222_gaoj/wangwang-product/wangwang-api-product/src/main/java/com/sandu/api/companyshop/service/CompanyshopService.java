package com.sandu.api.companyshop.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.companyshop.input.CompanyshopQuery;
import com.sandu.api.companyshop.model.Companyshop;

import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * store_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-22 16:51
 */
public interface CompanyshopService {

    /**
     * 插入
     *
     * @param companyshop
     * @return
     */
    int insert(Companyshop companyshop);

    /**
     * 更新
     *
     * @param companyshop
     * @return
     */
    int update(Companyshop companyshop);

    /**
     * 删除
     *
     * @param companyshopIds
     * @return
     */
    int delete(Set<Integer> companyshopIds);

    /**
     * 通过ID获取详情
     *
     * @param companyshopId
     * @return
     */
     Companyshop getById(int companyshopId);

    /**
     * 查询列表
     *
     * @return
     */
    PageInfo<Companyshop> findAll(CompanyshopQuery query);

    /**
     * 置顶
     * @param shopId
     * @param topId
     * @return
     */
    int companyshopToTop(String shopId, String topId);

    /**
     * 刷新
     * @param companyshopId
     * @return
     */
    int companyshopToRefresh(String companyshopId, String topId);

    Integer checkHasOfflineShop(Long shopOfflineId);

    Integer checkHasOfflineShop2(Long claimUserId);
}
