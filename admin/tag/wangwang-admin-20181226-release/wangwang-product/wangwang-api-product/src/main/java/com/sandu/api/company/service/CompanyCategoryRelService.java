package com.sandu.api.company.service;

import com.sandu.api.company.model.CompanyCategoryRel;

import java.util.List;

/**
 * @author Sandu
 */
public interface CompanyCategoryRelService {
    int saveCompanyCategory(CompanyCategoryRel companyCategoryRel);

    void updataCompanyCategoryRel(CompanyCategoryRel companyCategoryRel);

    void saveCompanyCategoryList(List<CompanyCategoryRel> lists);


}
