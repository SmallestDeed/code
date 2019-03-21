package com.sandu.service.user.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.user.model.UserJurisdiction;
import com.sandu.api.user.service.UserJurisdictionService;
import com.sandu.service.user.dao.UserJurisdictionDao;

import java.util.List;
import java.util.Set;

@Service("userJurisdictionService")
public class UserJurisdictionServiceImpl implements UserJurisdictionService{

	@Autowired
	private UserJurisdictionDao userJurisdictionDao;
	
	@Override
	public UserJurisdiction queryByUserIdAndPlatformId(Long userId,Long platformId) {
		// TODO Auto-generated method stub
		return userJurisdictionDao.selectByUserIdAndPlatformId(userId,platformId);
	}
	
	@Override
	public List<UserJurisdiction> queryByUserId(Long userId) {
		// TODO Auto-generated method stub
		return userJurisdictionDao.selectByUserId(userId);
	}
	 

	@Override
	public int countByUserIdAndJurisdictionStatus(Long userId, int jurisdictionStatus) {
		// TODO Auto-generated method stub
		return userJurisdictionDao.countByUserIdAndJurisdictionStatus(userId,jurisdictionStatus);
	}

	@Override
	public int save(UserJurisdiction userJurisdiction) {
		return userJurisdictionDao.insert(userJurisdiction);
	}

	@Override
	public void delByPrimaryKey(Long id) {
		 userJurisdictionDao.delByPrimaryKey(id);
	}

	@Override
	public void delByIds(Integer userId) {
		userJurisdictionDao.delByIds(userId);
	}

	@Override
	public void batchUserJurisdictions(List<UserJurisdiction> ujs) {
		userJurisdictionDao.batchUserJurisdictions(ujs);
	}

	@Override
	public List<UserJurisdiction> queryByUserIdANDplatformIds(Long userId, List<Integer> adminPlatforms) {
		return userJurisdictionDao.queryByUserIdANDplatformIds(userId,adminPlatforms);
	}

	@Override
	public void batchDelUserJurisdiction(int userId, Set<Long> oldPlatformIds) {
		userJurisdictionDao.batchDelUserJurisdiction(userId,oldPlatformIds);
	}
}
