package com.sandu.service.companyshop.dao;


import com.sandu.api.companyshop.model.BaseCompany;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseCompanyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseCompany record);

    int insertSelective(BaseCompany record);

    BaseCompany selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseCompany record);

    int updateByPrimaryKeyWithBLOBs(BaseCompany record);

    int updateByPrimaryKey(BaseCompany record);

    List<BaseCompany> queryCompany();
}