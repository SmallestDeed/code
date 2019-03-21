package com.sandu.service.companyshop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.companyshop.input.CompanyShopOfflineClaim;
import com.sandu.api.companyshop.input.CompanyshopofflineQuery;
import com.sandu.api.companyshop.model.Companyshopoffline;
import com.sandu.api.companyshop.service.CompanyshopofflineService;
import com.sandu.service.companyshop.dao.CompanyshopofflineMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/11/29 14:04
 * @since 1.8
 */

@Slf4j
@Service("companyshopofflineService")
public class CompanyshopofflineServiceImpl implements CompanyshopofflineService {

    @Autowired
    private CompanyshopofflineMapper companyshopofflineMapper;

    @Override
    public Companyshopoffline query(Long id) {
        if (id == null || id <= 0) {
            log.warn("参数异常：id 不能为空！");
            return null;
        }

        return companyshopofflineMapper.query(id);
    }

    @Override
    public PageInfo<Companyshopoffline> listShopOffline(CompanyshopofflineQuery query) {
        if (query == null) {
            log.warn("参数异常：query 不能为空！");
            return null;
        }

        PageHelper.startPage(query.getPage(), query.getLimit());
        List<Companyshopoffline> listShopOffline = companyshopofflineMapper.listShopOffline(query);

        return new PageInfo<>(listShopOffline);
    }

    @Override
    @Transactional
    public Integer claimShop(CompanyShopOfflineClaim claim) {
        return null;
    }

    @Override
    public int insert(Companyshopoffline companyshopoffline) {
        int result = companyshopofflineMapper.insert(companyshopoffline);
        if (result > 0) {
            return companyshopoffline.getId();
        }
        return 0;
    }

    @Override
    public int update(Companyshopoffline companyshopoffline) {
        return companyshopofflineMapper.updateByPrimaryKey(companyshopoffline);
    }

    @Override
    public int delete(Set<Integer> companyshopofflineIds) {
        return companyshopofflineMapper.deleteByPrimaryKey(companyshopofflineIds);
    }

    @Override
    public Companyshopoffline getById(int companyshopofflineId) {
        return companyshopofflineMapper.selectByPrimaryKey(companyshopofflineId);
    }

    @Override
    public PageInfo<Companyshopoffline> findAll(CompanyshopofflineQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        return new PageInfo<>(companyshopofflineMapper.findAll(query));
    }

    @Override
    public Companyshopoffline checkShopName(String shopName, Integer companyId) {
        return companyshopofflineMapper.checkShopName(shopName, companyId);
    }

    @Override
    public int toRelease(String id, String isRelease) {
        return companyshopofflineMapper.toRelease(id, isRelease);
    }
}
