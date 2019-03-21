package com.sandu.service.companyshop.dao;

import com.sandu.api.companyshop.input.CompanyshopQuery;
import com.sandu.api.companyshop.model.Companyshop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * store_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-22 16:51
 */
@Repository
public interface CompanyshopMapper {
    int insert(Companyshop companyshop);

    int updateByPrimaryKey(Companyshop companyshop);

    int deleteByPrimaryKey(@Param("companyshopIds") Set<Integer> companyshopIds);

    Companyshop selectByPrimaryKey(@Param("companyshopId") int companyshopId);

    List<Companyshop> findAll(CompanyshopQuery query);

    int companyshopToTop(@Param("shopId") String shopId, @Param("topId") String topId);

    int companyshopToRefresh(@Param("shopId") String shopId);

    Integer checkHasOfflineShop(Long shopOfflineId);
    Integer checkHasOfflineShop2(Long claimUserId);
}
