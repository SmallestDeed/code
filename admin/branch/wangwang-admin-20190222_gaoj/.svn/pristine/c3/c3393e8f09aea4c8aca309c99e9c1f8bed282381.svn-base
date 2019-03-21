package com.sandu.api.companyshop.service.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.companyshop.input.CompanyshopAdd;
import com.sandu.api.companyshop.input.CompanyshopQuery;
import com.sandu.api.companyshop.input.CompanyshopUpdate;
import com.sandu.api.companyshop.model.Companyshop;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * store_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-22 16:51
 */
public interface CompanyshopBizService {

    /**
     * 创建
     *
     * @param input
     * @return
     */
    int create(CompanyshopAdd input);

    /**
     * 更新
     *
     * @param input
     * @return
     */
    int update(CompanyshopUpdate input);

    /**
     * 删除
     */
    int delete(String companyshopId);

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
     * @param query
     * @return
     */
    PageInfo<Companyshop> query(CompanyshopQuery query);

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
}
