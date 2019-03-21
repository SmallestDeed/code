package com.sandu.base.service.impl;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sandu.cache.service.RedisService;
import com.sandu.matadata.CacheKeys;
import com.google.gson.reflect.TypeToken;
import com.sandu.base.dao.BaseAreaDao;
import com.sandu.base.model.query.BaseAreaQuery;
import com.sandu.base.model.vo.BaseAreaVo;
import com.sandu.base.service.BaseAreaService;
import com.sandu.common.constant.GlobalConstant;
import com.sandu.common.utils.JsonUtils;

@Service("baseAreaService")
@Transactional(readOnly = true)
public class BaseAreaServiceImpl implements BaseAreaService{
	@Autowired
	private BaseAreaDao baseAreaDao;
	@Autowired
	private RedisService redisService;
	
	@Override
	public List<BaseAreaVo> getCityList() {
		List<BaseAreaVo> lstCity=null;
		String key=CacheKeys.getArea();
		String jsonCityList=redisService.get(key);
		if(StringUtils.isBlank(jsonCityList)) {
			BaseAreaQuery queryEntity=new BaseAreaQuery();
			queryEntity.setLevelId(2);
			lstCity= baseAreaDao.findFontAllList(queryEntity);
			jsonCityList=JsonUtils.toJson(lstCity);
			redisService.del(key);
			redisService.addString(key, GlobalConstant.LONG_CACHE_SECONDS,jsonCityList);
		}
		else {
			lstCity=JsonUtils.fromJson(jsonCityList, new TypeToken<List<BaseAreaVo>>() {}.getType());
		}
		return lstCity;
	}
	
	public List<BaseAreaVo> getOpenServiceCityList()
	{
		List<BaseAreaVo> lstCity=null;
		String key=CacheKeys.getOpenServiceArea();
		String jsonCityList=redisService.get(key);
		if(StringUtils.isBlank(jsonCityList)) {
			BaseAreaQuery queryEntity=new BaseAreaQuery();
			queryEntity.setLevelId(2);
			lstCity= baseAreaDao.getOpenServiceCityList();
			jsonCityList=JsonUtils.toJson(lstCity);
			redisService.del(key);
			redisService.addString(key,GlobalConstant.LONG_CACHE_SECONDS,jsonCityList);
		}
		else {
			lstCity=JsonUtils.fromJson(jsonCityList, new TypeToken<List<BaseAreaVo>>() {}.getType());
		}
		return lstCity;
	}

}
