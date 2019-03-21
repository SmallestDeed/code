package com.sandu.service.user.impl;

import com.sandu.api.user.model.ServicesAccountRef;
import com.sandu.api.user.service.ServicesAccountRefService;
import com.sandu.service.user.dao.ServicesAccountRefDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/6/2  16:26
 */
@Service("servicesAccountRefService")
public class ServicesAccountRefServiceImpl implements ServicesAccountRefService{

    @Autowired
    private ServicesAccountRefDao servicesAccountRefDao;


    @Override
    public int addServiceAccountService(ServicesAccountRef servicesAccountRef) {
        int result = servicesAccountRefDao.add(servicesAccountRef);
        if (result>0){
            Long id = servicesAccountRef.getId();
            return id.intValue();
        }
        return 0;
    }

    @Override
    public List<ServicesAccountRef> getUserServiceByAccount(String account) {

        return null;
    }

}
