package com.sandu.product.service.impl;

import com.sandu.product.dao.BaseCompanyMapper;
import com.sandu.product.model.input.BaseCompanySearch;
import com.sandu.product.model.output.BaseCompanyVo;
import com.sandu.product.service.BaseCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenm on 2018/8/2.
 */
@Service("baseCompanyService")
@Transactional
public class BaseCompanyServiceImpl implements BaseCompanyService{
    @Autowired
    private BaseCompanyMapper baseCompanyMapper;

    @Override
    public List<BaseCompanyVo> selectBaseCompanyVoList(BaseCompanySearch baseCompanySearch) {
        return baseCompanyMapper.selectBaseCompanyVoList(baseCompanySearch);
    }
}
