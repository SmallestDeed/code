package com.sandu.api.companyshop.service.biz;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sandu.api.companyshop.input.CompanyShopOfflineClaim;
import com.sandu.api.companyshop.input.CompanyshopofflineAdd;
import com.sandu.api.companyshop.input.CompanyshopofflineQuery;
import com.sandu.api.companyshop.input.CompanyshopofflineUpdate;
import com.sandu.api.companyshop.model.Companyshopoffline;
import com.sandu.common.ReturnData;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/11/29 14:02
 * @since 1.8
 */
public interface CompanyshopofflineBizService {

    Companyshopoffline query(Long id);

    PageInfo<Companyshopoffline> listShopOffline(CompanyshopofflineQuery query);

    ReturnData claimShop(CompanyShopOfflineClaim claim);

    /**
     * 创建
     *
     * @param input
     * @return
     */
    int create(CompanyshopofflineAdd input);

    /**
     * 更新
     *
     * @param input
     * @return
     */
    int update(CompanyshopofflineUpdate input);

    /**
     * 删除
     */
    int delete(String companyshopofflineId);

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
     * @param query
     * @return
     */
    PageInfo<Companyshopoffline> query(CompanyshopofflineQuery query);

    /**
     * 校验店铺名称是否唯一
     *
     * @param shopName
     * @param companyId
     * @return
     */
    Companyshopoffline checkShopName(String shopName, Integer companyId);

    /**
     * 店铺发布
     *
     * @param id
     * @param isRelease
     * @return
     */
    int toRelease(String id, String isRelease);

	void importCompanyShopFromExcel(List<CompanyshopofflineAdd> adds);
}
