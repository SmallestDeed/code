package com.sandu.service.mallUserAddress.impl.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.sandu.api.mallUserAddress.input.MallUserAddressAdd;
import com.sandu.api.mallUserAddress.input.MallUserAddressQuery;
import com.sandu.api.mallUserAddress.input.MallUserAddressUpdate;
import com.sandu.api.mallUserAddress.model.MallUserAddress;
import com.sandu.api.mallUserAddress.service.MallUserAddressService;
import com.sandu.api.mallUserAddress.service.biz.MallUserAddressBizService;
import com.sandu.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
@Service("mallUserAddressBizService")
public class MallUserAddressBizServiceImpl implements MallUserAddressBizService {

    @Autowired
    private MallUserAddressService mallUserAddressService;

    @Override
    public int create(MallUserAddressAdd input) {
        MallUserAddress.MallUserAddressBuilder builder = MallUserAddress.builder();

        MallUserAddress mallUserAddress = builder.build();
        BeanUtils.copyProperties(input, mallUserAddress);

        return mallUserAddressService.insert(mallUserAddress);
    }

    @Override
    public int update(MallUserAddressUpdate input) {
        MallUserAddress.MallUserAddressBuilder builder = MallUserAddress.builder();
        MallUserAddress mallUserAddress = builder.build();

        BeanUtils.copyProperties(input, mallUserAddress);
        //转换原字段ID
        mallUserAddress.setId(input.getMallUserAddressId());
        return mallUserAddressService.update(mallUserAddress);
    }


    @Override
    public MallUserAddress getById(int mallUserAddressId) {
        return mallUserAddressService.getById(mallUserAddressId);
    }


}
