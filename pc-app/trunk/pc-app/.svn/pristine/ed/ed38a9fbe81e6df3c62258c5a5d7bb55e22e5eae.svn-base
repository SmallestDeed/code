package com.nork.home.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.cache.utils.ListTranscoder;
import com.nork.common.metadata.ModuleType;
import com.nork.common.metadata.PageParameter;
import com.nork.common.metadata.QueryParameter;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.home.model.BaseHouse;
import com.nork.home.model.BaseHouseResult;
import com.nork.home.model.search.BaseHouseSearch;
import com.nork.home.service.BaseHouseService;

/***
 * 户型缓存层
 * 
 * @author qiu.jun
 * @date 2016-05-18
 */
public class BaseHouseCacher {
	private static BaseHouseService baseHouseService = SpringContextHolder.getBean(BaseHouseService.class);

	private static PageParameter getPageParameter(BaseHouseResult result) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(result.getStart());
		parameter.setPageSize(result.getLimit());

		if (StringUtils.isNoneBlank(result.getProvinceCode())) {
			qp = new QueryParameter();
			qp.setName("provinceCode");
			qp.setValue(result.getProvinceCode());
			lstParameter.add(qp);
		}
		if (StringUtils.isNoneBlank(result.getCityCode())) {
			qp = new QueryParameter();
			qp.setName("cityCode");
			qp.setValue(result.getCityCode());
			lstParameter.add(qp);
		}
		if (StringUtils.isNoneBlank(result.getLivingName())) {
			qp = new QueryParameter();
			qp.setName("livingName");
			qp.setValue(result.getLivingName());
			lstParameter.add(qp);
		}
		if (StringUtils.isNoneBlank(result.getAreaLongCode())) {
			qp = new QueryParameter();
			qp.setName("areaLongCode");
			qp.setValue(result.getAreaLongCode());
			lstParameter.add(qp);
		}
		if (result.getUserType()==null) {
			qp = new QueryParameter();
			qp.setName("userType");
			qp.setValue(result.getUserType()==null?"":result.getUserType().toString());
			lstParameter.add(qp);
		}
		parameter.setLstParameter(lstParameter);
		return parameter;
	}

	/***
	 * 根据条件获取户型总记录数
	 * 
	 * @param spaceCommon
	 * @return
	 */
	public static int getCount(BaseHouseResult result) {
		int total = 0;
		PageParameter parameter = getPageParameter(result);
		String key = KeyGenerator.getTotalWithParameter(ModuleType.BaseHouse, parameter);
		String temp = null;
		if (Utils.enableRedisCache())
		temp = CacheManager.getInstance().getCacher().get(key);
		
		if (StringUtils.isBlank(temp)) {
			total = baseHouseService.getHouseCount(result);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 1800);
		} else {
			total = Integer.parseInt(temp);
		}
		return total;
	}

	/***
	 * 分页获取户型列表
	 * 
	 * @param plan
	 * @return
	 */
	public static List<BaseHouseResult> getPageList(BaseHouseResult result,int userId) {
		List<BaseHouseResult> lstPlan = Lists.newArrayList();
		PageParameter parameter = getPageParameter(result);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.BaseHouse, parameter);
		if(result.getUserType()!= null){
			key = key + "_userType="+result.getUserType();
		}
		lstPlan = CacheManager.getInstance().getCacher().getList(BaseHouseResult.class, key);
		if (CustomerListUtils.isEmpty(lstPlan)) {
			lstPlan = baseHouseService.getHouseList(result);
			if(lstPlan==null||lstPlan.size()==0){
				
			}else{
				CacheManager.getInstance().getCacher().setObject(key, lstPlan, 10);
			}
			
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlan;
	}


	/***
	 * 获得房子 基础表 总条数
	 * 
	 * @param baseHouseSearch
	 * @return total
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int getBaseHouseCount(BaseHouseSearch baseHouseSearch) {
		int total = 0;
		Map map = new HashMap();

		Integer[] stateArray = baseHouseSearch.getSpaceCommonStatusList();
		if (null != stateArray && 0 < stateArray.length) {
			StringBuilder builder = new StringBuilder("");
			for (Integer state : stateArray) {
				if (StringUtils.isEmpty(builder.toString())) {
					builder.append(state);
				} else {
					builder.append("," + state);
				}
			}
			map.put("state", builder.toString());
		}
		Integer livingId = baseHouseSearch.getLivingId();
		map.put("livingId", livingId);

		String key = KeyGenerator.getKeyWithMap(ModuleType.BaseHouse, map);
		CacheManager.getInstance().getCacher().remove(ModuleType.BaseHouse, key);

		if (CacheManager.getInstance().getCacher() != null) {
			String totalString = CacheManager.getInstance().getCacher().get(key);
			if (!StringUtils.isEmpty(totalString)) {
				total = Integer.parseInt(totalString);
			} else {
				total = baseHouseService.getHaveSpaceCount(baseHouseSearch);
				if (total > 0) {
					CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 10);
				}
			}
		}
		return total;
	}

	/**
	 * 获取小区户型
	 * @param baseHouseSearch
	 * @return
	 */
	public static List<BaseHouse> getBaseHouseList(BaseHouseSearch baseHouseSearch) {

		List<BaseHouse> list = new ArrayList<BaseHouse>();
		Map map = new HashMap();
		Integer[] stateArray = baseHouseSearch.getSpaceCommonStatusList();
		if (null != stateArray && 0 < stateArray.length) {
			StringBuilder builder = new StringBuilder("");
			for (Integer state : stateArray) {
				if (StringUtils.isEmpty(builder.toString())) {
					builder.append(state);
				} else {
					builder.append("," + state);
				}
			}
			map.put("state", builder.toString());
		}
		Integer livingId = baseHouseSearch.getLivingId();
		Integer start = baseHouseSearch.getStart();
		Integer limit = baseHouseSearch.getLimit();
		map.put("livingId", livingId);
		map.put("start", start);
		map.put("limit", limit);

		String key = KeyGenerator.getKeyWithMap(ModuleType.BaseHouse, map);
		if (CacheManager.getInstance().getCacher() != null) {
			ListTranscoder<BaseHouse> listTranscoder = new ListTranscoder<BaseHouse>();
			list = listTranscoder.deserialize(CacheManager.getInstance().getCacher().getBytes(key));
			if (CustomerListUtils.isEmpty(list)) {
				list = baseHouseService.getHaveSpaceList(baseHouseSearch);
				if (!CustomerListUtils.isEmpty(list)) {
					CacheManager.getInstance().getCacher().setByte(key, listTranscoder.serialize(list), 10);
				}
			}
		}
		return list;
	}

	/***
	 * 获得房子 基础表 列表
	 * 
	 * @param baseHouseSearch
	 * @return List
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<BaseHouse> getPaginatedList(BaseHouseSearch baseHouseSearch) {
		List<BaseHouse> list = new ArrayList<BaseHouse>();
		Map map = new HashMap();
		Integer start = baseHouseSearch.getStart();
		Integer limit = baseHouseSearch.getLimit();
		Integer livingId = baseHouseSearch.getLivingId();
		map.put("start", start);
		map.put("limit", limit);
		map.put("livingId", livingId);
		String key = KeyGenerator.getKeyWithMap(ModuleType.BaseHouse, map);
		if (CacheManager.getInstance().getCacher() != null) {
			ListTranscoder<BaseHouse> listTranscoder = new ListTranscoder<BaseHouse>();
			  //list = (List<BaseHouse>)CacheManager.getInstance().getCacher().getObject(key);
			list = listTranscoder.deserialize(CacheManager.getInstance().getCacher().getBytes(key));
			if (CustomerListUtils.isEmpty(list)) {
				list = baseHouseService.getPaginatedListFilter(baseHouseSearch);
				if (!CustomerListUtils.isEmpty(list)) {
					//CacheManager.getInstance().getCacher().setObject(key, listTranscoder.serialize(list), 10);
					CacheManager.getInstance().getCacher().setByte(key, listTranscoder.serialize(list), 10);
				}
			}

		}
		//////System.out.println("********WebDesignPlanController-saveDesignModeWeb-getPaginatedList-key" + key + "********");
		return list;
	}

}
