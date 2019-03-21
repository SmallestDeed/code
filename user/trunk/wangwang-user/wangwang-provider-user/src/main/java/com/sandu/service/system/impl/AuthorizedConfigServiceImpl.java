package com.sandu.service.system.impl;

import com.sandu.api.system.input.AuthorizedConfigSearch;
import com.sandu.api.system.model.AuthorizedConfig;
import com.sandu.api.system.service.AuthorizedConfigService;
import com.sandu.service.system.dao.AuthorizedConfigDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authorizedConfigService")
public class AuthorizedConfigServiceImpl implements AuthorizedConfigService {

    @Autowired
    private AuthorizedConfigDao authorizedConfigDao;


    @Override
    public int getCount(AuthorizedConfigSearch authorizedConfigSearch) {
        if (authorizedConfigSearch.getValidState() == null) {
            authorizedConfigSearch.setValidState(1);
        }
        // 等于3则查询所有
        if (authorizedConfigSearch.getValidState() != null && authorizedConfigSearch.getValidState() == 3) {
            authorizedConfigSearch.setValidState(null);
        }
        return authorizedConfigDao.selectCount(authorizedConfigSearch);
    }


	@Override
	public AuthorizedConfig queryAuthorizedConfigByUserId(Long userId) {
		// TODO Auto-generated method stub
		return authorizedConfigDao.selectByUserId(userId);
	}
	
	@Override
	public List<AuthorizedConfig> getList(AuthorizedConfig authorizedConfig) {
		if(authorizedConfig.getValidState()==null){
			authorizedConfig.setValidState(1);
		}
		if(authorizedConfig.getValidState()!=null&&authorizedConfig.getValidState()==3){/**等于3则查询所有*/
			authorizedConfig.setValidState(null);
		}
		return authorizedConfigDao.selectList(authorizedConfig);
	}
}
