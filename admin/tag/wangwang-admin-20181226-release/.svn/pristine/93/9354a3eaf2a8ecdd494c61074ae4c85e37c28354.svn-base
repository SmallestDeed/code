package com.sandu.service.company.impl;

import com.sandu.api.company.model.CompanyCategoryRel;
import com.sandu.api.company.service.CompanyCategoryRelService;
import com.sandu.service.company.dao.CompanyCategoryRelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author Sandu
 */
@Service
public class CompanyCategoryRelServiceImpl implements CompanyCategoryRelService {
    @Autowired
    private CompanyCategoryRelDao companyCategoryRelDao;
    @Override
    public int saveCompanyCategory(CompanyCategoryRel companyCategoryRel) {
        return companyCategoryRelDao.save(companyCategoryRel);
    }

    @Override
    public void updataCompanyCategoryRel(CompanyCategoryRel companyCategoryRel) {
        companyCategoryRelDao.update(companyCategoryRel);
    }

    @Override
    public void saveCompanyCategoryList(List<CompanyCategoryRel> lists) {
        if(lists.isEmpty()){
            return ;
        }
        int count = companyCategoryRelDao.saveCompanyCategoryList(lists);
    }
}
