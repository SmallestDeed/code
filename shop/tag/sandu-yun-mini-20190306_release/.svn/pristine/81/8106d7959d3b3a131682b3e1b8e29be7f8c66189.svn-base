package com.sandu.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.reflect.TypeToken;
import com.sandu.cache.service.RedisService;
import com.sandu.common.constant.GlobalConstant;
import com.sandu.common.utils.JsonUtils;
import com.sandu.common.utils.SecurityUtils;
import com.sandu.matadata.CacheKeys;
import com.sandu.matadata.SecurityConfig;
import com.sandu.sys.dao.SysUserDao;
import com.sandu.sys.model.query.SysUserQuery;
import com.sandu.sys.model.vo.SysUserVo;
import com.sandu.sys.service.SysUserService;

@Service("sysUserService")
@Transactional(readOnly = true)
public class SysUserServiceImpl implements SysUserService{
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private RedisService redisService;
	@Override
	public SysUserVo get(String openId) {
		SysUserVo user=null;
		String key=CacheKeys.getUserOpenId(openId);
		String jsonDicList=redisService.get(key);
		if(StringUtils.isBlank(jsonDicList)) {
			SysUserQuery query=new SysUserQuery();
			query.setOpenId(openId);
			user=sysUserDao.getByOpenId(query);
			if(user!=null) {
				int userId=(int)user.getId();
				String token=SecurityUtils.generateToken(userId, SecurityConfig.SECRET);
				String tokenKey=CacheKeys.getUserToken(token);
				user.setToken(token);
				jsonDicList=JsonUtils.toJson(user);
				redisService.del(key);
				redisService.del(tokenKey);
				redisService.addString(key,GlobalConstant.CACHE_SECONDS,jsonDicList);
				redisService.addString(tokenKey,GlobalConstant.CACHE_SECONDS,openId);
			}
		}
		else {
			user=JsonUtils.fromJson(jsonDicList, new TypeToken<SysUserVo>() {}.getType());
		}
		return user;
	}
	
	public boolean setToken(long userId,String token) {
		boolean isUpdated=false;
		String tokenKey=CacheKeys.getUserToken(token);
		redisService.del(tokenKey);
		isUpdated=redisService.addString(tokenKey,GlobalConstant.TOKEN_CACHE_SECONDS,String.valueOf(userId));
		return isUpdated;
	}
	
	public boolean validateToken(String token) {
		boolean isValidated=false;
		String tokenKey=CacheKeys.getUserToken(token);
		if(redisService.exists(tokenKey)) {
			isValidated=true;
		}
		return isValidated;
	}

	@Override
	public String selectUUIDByPrimaryKey(int userId) {
		return sysUserDao.selectUUIDByPrimaryKey(userId);
	}
	@Override
	public SysUserVo getById(Long userId) {
		return sysUserDao.getUserById(userId);
	}

}
