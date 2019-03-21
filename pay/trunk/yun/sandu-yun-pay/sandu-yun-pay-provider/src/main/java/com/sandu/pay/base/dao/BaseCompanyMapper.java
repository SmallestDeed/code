package com.sandu.pay.base.dao;


import com.sandu.pay.base.model.BaseCompany;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseCompanyMapper {

    BaseCompany selectByPrimaryKey(Long id);

    BaseCompany selectByIdAndAppId(@Param("companyId") Long id, @Param("appId")String appId);
}