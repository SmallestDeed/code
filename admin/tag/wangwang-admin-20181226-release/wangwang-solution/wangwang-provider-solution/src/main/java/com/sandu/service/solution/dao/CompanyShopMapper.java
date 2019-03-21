package com.sandu.service.solution.dao;

import com.sandu.api.solution.model.CompanyShop;

public interface CompanyShopMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CompanyShop record);

    int insertSelective(CompanyShop record);

    CompanyShop selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompanyShop record);

    int updateByPrimaryKey(CompanyShop record);
}