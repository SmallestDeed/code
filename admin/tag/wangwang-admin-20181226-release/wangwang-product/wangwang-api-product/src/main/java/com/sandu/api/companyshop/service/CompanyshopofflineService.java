package com.sandu.api.companyshop.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.companyshop.input.CompanyShopOfflineClaim;
import com.sandu.api.companyshop.input.CompanyshopofflineQuery;
import com.sandu.api.companyshop.model.Companyshopoffline;

import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/11/29 14:01
 * @since 1.8
 */
public interface CompanyshopofflineService {

    /**
     * 插入
     *
     * @param companyshopoffline
     * @return
     */
    int insert(Companyshopoffline companyshopoffline);

    /**
     * 更新
     *
     * @param companyshopoffline
     * @return
     */
    int update(Companyshopoffline companyshopoffline);

    /**
     * 删除
     *
     * @param companyshopofflineIds
     * @return
     */
    int delete(Set<Integer> companyshopofflineIds);

    /**
     * 通过ID获取详情
     *
     * @param companyshopofflineId
     * @return
     */
    Companyshopoffline getById(int companyshopofflineId);

    /**
     * 查询列表
     *
     * @return
     */
    PageInfo<Companyshopoffline> findAll(CompanyshopofflineQuery query);

    /**
     * 校验店铺名称是否唯一
     *
     * @param shopName
     * @param companyId
     * @return
     */
    Companyshopoffline checkShopName(String shopName, Integer companyId);

    /**
     * 门店发布
     *
     * @param id
     * @param isRelease
     * @return
     */
    int toRelease(String id, String isRelease);

    Companyshopoffline query(Long id);

    PageInfo<Companyshopoffline> listShopOffline(CompanyshopofflineQuery query);

    Integer claimShop(CompanyShopOfflineClaim claim);
}
