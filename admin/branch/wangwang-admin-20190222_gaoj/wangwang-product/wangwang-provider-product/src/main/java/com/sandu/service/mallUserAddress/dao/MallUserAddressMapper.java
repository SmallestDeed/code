package com.sandu.service.mallUserAddress.dao;

import com.sandu.api.mallUserAddress.input.MallUserAddressQuery;
import com.sandu.api.mallUserAddress.model.MallUserAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * mallUserAddress
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Jul-31 15:56
 */
@Repository
public interface MallUserAddressMapper {
    int insert(MallUserAddress mallUserAddress);

    int updateByPrimaryKey(MallUserAddress mallUserAddress);

    int deleteByPrimaryKey(@Param("mallUserAddressIds") Set<Integer> mallUserAddressIds);

    MallUserAddress selectByPrimaryKey(@Param("mallUserAddressId") int mallUserAddressId);

    List<MallUserAddress> getAddressByUserId(Integer userId);
}
