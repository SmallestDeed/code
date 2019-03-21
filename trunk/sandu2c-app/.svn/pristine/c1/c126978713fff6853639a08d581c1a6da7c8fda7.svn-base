package com.sandu.shop.service.impl;

import com.sandu.shop.dao.CompanyShopMapper;
import com.sandu.shop.service.CompanyShopService;
import com.sandu.shop.vo.CompanyShopVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/11/17  11:33
 */
@Service("companyShopService")
public class CompanyShopServiceImpl implements CompanyShopService {

    @Autowired
    private CompanyShopMapper companyShopMapper;

    @Override
    public List<CompanyShopVO> getShopByCompanyId(Long companyId) {
        return companyShopMapper.selectByCompanyId(companyId);
    }
}
