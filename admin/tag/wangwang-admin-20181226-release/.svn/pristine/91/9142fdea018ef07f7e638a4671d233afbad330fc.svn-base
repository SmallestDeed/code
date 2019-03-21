package com.sandu.service.companyshop.dao;

import com.sandu.api.companyshop.input.ShopidentificationQuery;
import com.sandu.api.companyshop.model.Shopidentification;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * shop_identification
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-22 11:50
 */
@Repository
public interface ShopidentificationMapper {
    int insert(Shopidentification shopidentification);

    int updateByPrimaryKey(Shopidentification shopidentification);

    int deleteByPrimaryKey(@Param("shopidentificationIds") Set<Integer> shopidentificationIds);

    Shopidentification selectByPrimaryKey(@Param("shopidentificationId") int shopidentificationId);

    List<Shopidentification> findAll(ShopidentificationQuery query);

    Shopidentification getByShopId(@Param("shopId") int shopId);
}
