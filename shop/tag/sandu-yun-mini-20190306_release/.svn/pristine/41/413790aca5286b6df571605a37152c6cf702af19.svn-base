package com.sandu.search.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.reflect.TypeToken;
import com.sandu.cache.service.RedisService;
import com.sandu.common.constant.GlobalConstant;
import com.sandu.common.utils.JsonUtils;
import com.sandu.common.utils.StringUtils;
import com.sandu.company.model.vo.CompanyShopVo;
import com.sandu.matadata.CacheKeys;
import com.sandu.search.dao.SearchDao;
import com.sandu.search.model.SearchHot;
import com.sandu.search.model.SearchUser;
import com.sandu.search.model.query.SearchHotQuery;
import com.sandu.search.model.query.SearchUserQuery;
import com.sandu.search.model.vo.SearchHotVo;
import com.sandu.search.model.vo.SearchUserVo;
import com.sandu.search.service.SearchService;

/***
 * 搜索服务
 * 
 * @author Administrator
 *
 */
@Service("searchService")
@Transactional(readOnly = true)
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchDao searchDao;
	@Autowired
	private RedisService redisService;

	private void deleteCache(long userId) {
		String key = CacheKeys.getUserHistorySearch(userId);
		redisService.del(key);
	}
	
	@Override
	public boolean deleteSearchUser(SearchUser search) {
		boolean isDeleted=false;
		int num=0;
		if(search.getId()>0) {
		   num = searchDao.deleteSearchUser(search.getId());
		}
		if(search.getUserId()>0) {
			   num = searchDao.deleteSearchUserWithUserId(search.getUserId());
		}
		if (num > 0) {
			isDeleted= true;
			if(search.getUserId()>0)
			   deleteCache(search.getUserId());
		}
		return isDeleted;
	}

	@Override
	public List<SearchHotVo> getHotList() {
		List<SearchHotVo> lstVo = null;
		String key = CacheKeys.getHotSearch();
		String jsonShop = redisService.get(key);
		if (StringUtils.isBlank(jsonShop)) {
			lstVo = searchDao.getHotList();
			if (lstVo != null && lstVo.size() > 0) {
				jsonShop = JsonUtils.toJson(lstVo);
				redisService.del(key);
				redisService.addString(key, GlobalConstant.LONG_LONG_CACHE_SECONDS, jsonShop);
			}
		} else {
			lstVo = JsonUtils.fromJson(jsonShop, new TypeToken<List<SearchHotVo>>() {
			}.getType());
		}
		return lstVo;
	}

	@Override
	public List<SearchUserVo> getHistoryList(long userId) {
		List<SearchUserVo> lstVo = null;
		String key = CacheKeys.getUserHistorySearch(userId);
		String jsonShop = redisService.get(key);
		if (StringUtils.isBlank(jsonShop)) {
			SearchUserQuery query = new SearchUserQuery();
			int topN = 5;
			query.setUserId(userId);
			query.setTopN(topN);
			lstVo = searchDao.getHistoryList(query);
			if (lstVo != null && lstVo.size() > 0) {
				jsonShop = JsonUtils.toJson(lstVo);
				redisService.del(key);
				redisService.addString(key, GlobalConstant.LONG_LONG_CACHE_SECONDS, jsonShop);
			}
		} else {
			lstVo = JsonUtils.fromJson(jsonShop, new TypeToken<List<SearchUserVo>>() {
			}.getType());
		}
		return lstVo;
	}

	@Override
	public boolean saveSearchUser(SearchUser search) {
		boolean isSaved = false;
		if (search.getUserId() == 0 || StringUtils.isBlank(search.getSearchKey())) {
			return isSaved;
		}
		int result = 0;
		SearchUserQuery historyQuery = new SearchUserQuery();
		historyQuery.setUserId(search.getUserId());
		historyQuery.setSearchKey(search.getSearchKey());
		SearchUserVo userVo = searchDao.getSearchHistoryKey(historyQuery);
		if (userVo == null) {
			search.preInsert();
			result = searchDao.saveSearchUser(search);
		}
		else {
			search.setId(userVo.getId());
			search.preUpdate();
			search.setSearchCount(userVo.getSearchCount()+1);
			result = searchDao.updateHistorySearchCount(search);
		}
		SearchHotQuery query = new SearchHotQuery();
		query.setSearchKey(search.getSearchKey());
		SearchHotVo hotVo = searchDao.getSearchKey(query);
		if (hotVo != null && hotVo.getSearchKey() != null && !"".equals(hotVo.getSearchKey())) {
			SearchHot hot = new SearchHot();
			hot.setId(hotVo.getId());
			hot.setSearchKey(hotVo.getSearchKey());
			hot.setSearchCount(hotVo.getSearchCount() + 1);
			hot.preUpdate();
			searchDao.updateSearchCount(hot);
		} else {// 没有相同的。则进行插入
			search.preInsert();
			searchDao.saveSearchHot(search);
		}
		if (result > 0) {
			isSaved =true;
			deleteCache(search.getUserId());
		} 
		return isSaved;
		
	}
}
