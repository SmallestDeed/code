package com.sandu.api.mallUserAddress.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.mallUserAddress.input.MallUserAddressQuery;
import com.sandu.api.mallUserAddress.model.MallUserAddress;

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
public interface MallUserAddressService {

    /**
     * 插入
     *
     * @param mallUserAddress
     * @return
     */
    int insert(MallUserAddress mallUserAddress);

    /**
     * 更新
     *
     * @param mallUserAddress
     * @return
     */
    int update(MallUserAddress mallUserAddress);

    /**
     * 删除
     *
     * @param mallUserAddressIds
     * @return
     */
    int delete(Set<Integer> mallUserAddressIds);

    /**
     * 通过ID获取详情
     *
     * @param mallUserAddressId
     * @return
     */
     MallUserAddress getById(int mallUserAddressId);




    List<MallUserAddress> getAddressByUserId(Integer userId);
}
