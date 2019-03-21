package com.sandu.service.companyshop.dao;

import com.sandu.api.companyshop.input.CompanyshopofflineQuery;
import com.sandu.api.companyshop.model.Companyshopoffline;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/11/29 14:15
 * @since 1.8
 */

@Repository
public interface CompanyshopofflineMapper {
    int insert(Companyshopoffline companyshopoffline);

    Companyshopoffline query(Long id);

    List<Companyshopoffline> listShopOffline(CompanyshopofflineQuery query);

    int updateByPrimaryKey(Companyshopoffline companyshopoffline);

    int deleteByPrimaryKey(@Param("companyshopofflineIds") Set<Integer> companyshopofflineIds);

    Companyshopoffline selectByPrimaryKey(@Param("companyshopofflineId") int companyshopofflineId);

    List<Companyshopoffline> findAll(CompanyshopofflineQuery query);

    Companyshopoffline checkShopName(@Param("shopName") String shopName, @Param("companyId") Integer companyId);

    int toRelease(@Param("id") String id, @Param("isRelease") String isRelease);
}
