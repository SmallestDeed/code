package com.sandu.service.company.dao;

import com.sandu.api.company.model.BaseCompany;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseCompanyMapper {
    List<BaseCompany> listCompany();
}
