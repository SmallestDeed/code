package com.sandu.pay.base.service;


import com.sandu.pay.base.model.BaseCompany;

public interface BaseCompanyService {

    BaseCompany get(Long id);

    BaseCompany getByIdAndAppId(Long companyId, String appId);
}