package com.sandu.service.user.impl;

import com.sandu.api.user.model.AuthorizedConfig;
import com.sandu.api.user.service.AuthorizedConfigService;
import com.sandu.service.user.dao.AuthorizedConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sandu
 */
@Service("authorizedConfigService")
public class AuthorizedConfigServiceImpl implements AuthorizedConfigService {
    @Autowired
    private AuthorizedConfigDao authorizedConfigDao;

    @Override
    public AuthorizedConfig getAhthorizedByUserId(int userId) {
        return authorizedConfigDao.getByUserId(userId);
    }

    @Override
    public List<Integer> listCompanyUserIds(Integer companyId) {
        return authorizedConfigDao.listCompanyUserIds(companyId);
    }
}
