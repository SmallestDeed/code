package com.sandu.service.mallUserAddress.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.mallUserAddress.input.MallUserAddressQuery;
import com.sandu.api.mallUserAddress.model.MallUserAddress;
import com.sandu.api.mallUserAddress.service.MallUserAddressService;
import com.sandu.service.mallUserAddress.dao.MallUserAddressMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Slf4j
@Service("mallUserAddressService")
public class MallUserAddressServiceImpl implements MallUserAddressService {

    @Autowired
    private MallUserAddressMapper mallUserAddressMapper;

    @Override
    public int insert(MallUserAddress mallUserAddress) {
        int result = mallUserAddressMapper.insert(mallUserAddress);
        if (result > 0) {
            return mallUserAddress.getId();
        }
        return 0;
    }

    @Override
    public int update(MallUserAddress mallUserAddress) {
        return mallUserAddressMapper.updateByPrimaryKey(mallUserAddress);
    }

    @Override
    public int delete(Set<Integer> mallUserAddressIds) {
        return mallUserAddressMapper.deleteByPrimaryKey(mallUserAddressIds);
    }

    @Override
    public MallUserAddress getById(int mallUserAddressId) {
        return mallUserAddressMapper.selectByPrimaryKey(mallUserAddressId);
    }


    @Override
    public List<MallUserAddress> getAddressByUserId(Integer userId) {
        return mallUserAddressMapper.getAddressByUserId(userId);
    }
}
