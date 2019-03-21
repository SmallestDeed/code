package com.sandu.api.user.service;

import com.sandu.api.user.model.UserJurisdiction;

import java.util.List;
import java.util.Set;

public interface UserJurisdictionService {

	/**
     * 根据用户id及平台id查找用户权限配置
     *
     */
	UserJurisdiction queryByUserIdAndPlatformId(Long userId,Long platformId);
	
	List<UserJurisdiction> queryByUserId(Long userId);

	int countByUserIdAndJurisdictionStatus(Long userId, int jurisdictionStatus);

    int save(UserJurisdiction userJurisdiction);

    void delByPrimaryKey(Long id);

    void delByIds(Integer userId);

    void batchUserJurisdictions(List<UserJurisdiction> ujs);

    List<UserJurisdiction> queryByUserIdANDplatformIds(Long userId, List<Integer> adminPlatforms);

    void batchDelUserJurisdiction(int intValue, Set<Long> oldPlatformIds);
}
