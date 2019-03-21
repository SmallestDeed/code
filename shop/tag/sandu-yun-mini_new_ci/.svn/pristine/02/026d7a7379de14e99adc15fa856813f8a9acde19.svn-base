package com.sandu.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.sandu.common.utils.Utils;
import com.sandu.sys.model.SysDictionary;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.reflect.TypeToken;
import com.sandu.base.model.query.BaseAreaQuery;
import com.sandu.base.model.vo.BaseAreaVo;
import com.sandu.cache.service.RedisService;
import com.sandu.common.constant.GlobalConstant;
import com.sandu.common.utils.JsonUtils;
import com.sandu.matadata.CacheKeys;
import com.sandu.sys.dao.SysDictionaryDao;
import com.sandu.sys.model.query.SysDictionaryQuery;
import com.sandu.sys.model.vo.SysDictionaryVo;
import com.sandu.sys.service.SysDictionaryService;

@Service("sysDictionaryService")
@Transactional(readOnly = true)
public class SysDictionaryServiceImpl implements SysDictionaryService{
	@Autowired
	private SysDictionaryDao sysDictionaryDao;
	@Autowired
	private RedisService redisService;
	
	@Override
	public List<SysDictionaryVo> getListWithType(String type) {
		List<SysDictionaryVo> lstDic=null;
		String key=CacheKeys.getDictionaryType(type);
		String jsonDicList=redisService.get(key);
		if(StringUtils.isBlank(jsonDicList)) {
			SysDictionaryQuery query=new SysDictionaryQuery();
			query.setType(type);
			lstDic= sysDictionaryDao.findListByType(query);
			jsonDicList=JsonUtils.toJson(lstDic);
			redisService.addList(key,GlobalConstant.LONG_CACHE_SECONDS, jsonDicList);
		}
		else {
			lstDic=JsonUtils.fromJson(jsonDicList, new TypeToken<List<SysDictionaryVo>>() {}.getType());
		}
		return lstDic;
	}

	@Override
	public List<SysDictionaryVo> getDictionoryByTypeOrValues(String type, String values) {
		if (StringUtils.isEmpty(values)) {
			return null;
		}
		List<SysDictionaryVo> lstDic = null;
		String key = CacheKeys.getDictionaryTypeValues(type,values);
		String jsonDicList = redisService.get(key);
		if (StringUtils.isBlank(jsonDicList)) {
			List<Integer> valueList = Utils.getIntegerListFromString(values);
			SysDictionaryQuery query = new SysDictionaryQuery();
			query.setType(type);
			query.setValueList(valueList);
			lstDic = sysDictionaryDao.findListByTypeOrValues(query);
			if(lstDic==null) {
				lstDic=new ArrayList<SysDictionaryVo>();
			}
			jsonDicList = JsonUtils.toJson(lstDic);
			redisService.set(key, jsonDicList, GlobalConstant.LONG_CACHE_SECONDS);
		} else {
			lstDic=JsonUtils.fromJson(jsonDicList, new TypeToken<List<SysDictionaryVo>>() {}.getType());
		}
		return lstDic;
	}

	@Override
	public SysDictionaryVo getDictionoryByTypeOrValue(String type, Integer value) {
		SysDictionaryQuery query = new SysDictionaryQuery();
		query.setType(type);
		query.setValue(value);
		return sysDictionaryDao.findDictionory(query);
	}

	@Override
	public List<SysDictionary> getListByType(String type, Integer start, Integer pageSize) {
		return sysDictionaryDao.getListByType(type,start,pageSize);
	}

}
