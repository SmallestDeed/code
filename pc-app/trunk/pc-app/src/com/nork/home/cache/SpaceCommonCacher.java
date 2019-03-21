package com.nork.home.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.metadata.PageParameter;
import com.nork.common.metadata.QueryParameter;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.home.model.HouseSpaceResult;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;

/***
 * 通用空间缓存层
 * 
 * @author qiu.jun
 * @date 2016-05-12
 */
public class SpaceCommonCacher {
	private static SpaceCommonService spaceCommonService = SpringContextHolder.getBean(SpaceCommonService.class);

	private static PageParameter getPageParameter(SpaceCommon spaceCommon) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(spaceCommon.getStart());
		parameter.setPageSize(spaceCommon.getLimit());

		qp = new QueryParameter();
		qp.setName("pid");
		qp.setValue(String.valueOf(spaceCommon.getPid()));
		lstParameter.add(qp);

		qp = new QueryParameter();
		qp.setName("isStandardSpace");
		qp.setValue(String.valueOf(spaceCommon.getIsStandardSpace()));
		lstParameter.add(qp);

		qp = new QueryParameter();
		qp.setName("spaceFunctionId");
		qp.setValue(String.valueOf(spaceCommon.getSpaceFunctionId()));
		lstParameter.add(qp);

		qp = new QueryParameter();
		qp.setName("spaceShape");
		qp.setValue(spaceCommon.getSpaceShape());
		lstParameter.add(qp);

		if (StringUtils.isNotBlank(spaceCommon.getSpaceAreas())) {
			qp = new QueryParameter();
			qp.setName("spaceAreas");
			qp.setValue(spaceCommon.getSpaceAreas());
			lstParameter.add(qp);
		}
		
		if (spaceCommon.getPutawayStates() != null && spaceCommon.getPutawayStates().size()>0) {
			qp = new QueryParameter();
			qp.setName("putawayStates");
			String states = "";
			for(Integer ps : spaceCommon.getPutawayStates()){
				states = states + ps + ",";
			}
			qp.setValue(states.substring(0, states.length()-1));
			lstParameter.add(qp);
		}
		
		if (spaceCommon.getBlackList()!=null&&spaceCommon.getBlackList().size()>0) {
			qp = new QueryParameter();
			qp.setName("blackList");
			qp.setValue(spaceCommon.getBlackList().toString());
			lstParameter.add(qp);
		}

		if( StringUtils.isNotBlank(spaceCommon.getSch_spaceCode()) ){
			qp = new QueryParameter();
			qp.setName("sch_spaceCode");
			qp.setValue(spaceCommon.getSch_spaceCode());
			lstParameter.add(qp);
		}

		qp = new QueryParameter();
		qp.setName("Start");
		qp.setValue(spaceCommon.getStart()+"");
		lstParameter.add(qp);

		qp = new QueryParameter();
		qp.setName("limit");
		qp.setValue(spaceCommon.getLimit()+"");
		lstParameter.add(qp);

		parameter.setLstParameter(lstParameter);
		return parameter;
	}

	/***
	 * 获取单个通用空间
	 * 
	 * @param id
	 * @return
	 */
	public static SpaceCommon get(int id) {
		SpaceCommon common = null;
		String key = KeyGenerator.getIdKey(ModuleType.SpaceCommon, id);
		common = (SpaceCommon) CacheManager.getInstance().getCacher().getObject(key);
		if (common == null) {
			common = spaceCommonService.get(id);
			if (common != null) {
				CacheManager.getInstance().getCacher().setObject(key, common, 0);
			}
		}
		return common;
	}

	/***
	 * 获取带查询参数的通用空间总记录数
	 * 
	 * @param spaceCommon
	 * @return
	 */
	public static int getTotalWithParameter(SpaceCommon spaceCommon,int userId) {
		int total = 0;
		PageParameter parameter = getPageParameter(spaceCommon);
		String key = KeyGenerator.getTotalWithParameter(ModuleType.SpaceCommon, parameter);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = spaceCommonService.getSpaceSearchCount(spaceCommon,userId);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
		}
		return total;
	}

	/***
	 * 获取带查询参数的通用空间的分页记录
	 * 
	 * @param spaceCommon
	 * @return
	 */
	public static List<SpaceCommon> getPageWithParameter(SpaceCommon spaceCommon,int userId) {
		List<SpaceCommon> lstCommon = Lists.newArrayList();
		PageParameter parameter = getPageParameter(spaceCommon);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.SpaceCommon, parameter);
		lstCommon = CacheManager.getInstance().getCacher().getList(SpaceCommon.class, key);
		/*去除缓存测试*/
		//lstCommon=null;
		/*去除缓存测试->end*/
		if (CustomerListUtils.isEmpty(lstCommon)) {
			lstCommon = spaceCommonService.getSpaceSearchList(spaceCommon,userId);
			CacheManager.getInstance().getCacher().setObject(key, lstCommon, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstCommon;
	}

	/***
	 * 房子空间列表的总条数
	 * 
	 * @param houseId
	 * @return total
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//public static int getHouseSpaceListCount(Integer houseId) {
	public static int getHouseSpaceListCount(SpaceCommon spaceCommon,int userId) {
		Integer houseId=spaceCommon.getHouseId();
		int total = 0;
		Map map = new HashMap();
		map.put("houseId", houseId);
		map.put("Count", "Count");
		map.put("blackList", spaceCommon.getBlackList());
		String key = KeyGenerator.getKeyWithMap(ModuleType.HouseSpaceResult, map);
		if (CacheManager.getInstance().getCacher() != null) {
			String totalString = CacheManager.getInstance().getCacher().get(key);
			if (totalString != null && !"".equals(totalString)) {
				total = Integer.parseInt(totalString);
			} else {
				total = spaceCommonService.getHouseSpaceListCount(spaceCommon,userId);
				if (total > 0) {
					CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
				}
			}
		}

		return total;
	}

	/***
	 * 房子空间列表的总条数
	 * 
	 * @param houseId
	 * @return total
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//public static int getHouseSpaceListCount2(Integer houseId,int userType) {
	public static int getHouseSpaceListCount2(SpaceCommon spaceCommon,int userId) {
		Integer houseId=spaceCommon.getHouseId();
		Integer userType=spaceCommon.getUserType();
		int total = 0;
		Map map = new HashMap();
		map.put("houseId", houseId);
		map.put("Count", "Count");
		map.put("userType", userType);
		map.put("blackList", spaceCommon.getBlackList());
		String key = KeyGenerator.getKeyWithMap(ModuleType.HouseSpaceResult, map);
		if (CacheManager.getInstance().getCacher() != null) {
			String totalString = CacheManager.getInstance().getCacher().get(key);
			if (totalString != null && !"".equals(totalString)) {
				total = Integer.parseInt(totalString);
			} else {
				total = spaceCommonService.getHouseSpaceListCount2(spaceCommon,userId);
				if (total > 0) {
					CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
				}
			}
		}

		return total;
	}
	
	/***
	 * 房子空间列表
	 * 
	 * @param houseId
	 * @return total
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//public static List<HouseSpaceResult> getPaginatedHouseSpaceList(int houseId, int start, int limit) {
	public static List<HouseSpaceResult> getPaginatedHouseSpaceList(SpaceCommon spaceCommon,int userId) {
		Integer houseId=spaceCommon.getHouseId();
		Integer start=spaceCommon.getStart();
		Integer limit=spaceCommon.getLimit();
		List<HouseSpaceResult> list = new ArrayList<HouseSpaceResult>();
		Map map = new HashMap();
		map.put("houseId", houseId);
		map.put("start", start);
		map.put("limit", limit);
		map.put("blackList", spaceCommon.getBlackList());
		String key = KeyGenerator.getKeyWithMap(ModuleType.HouseSpaceResult, map);

		if (CacheManager.getInstance().getCacher() != null) {
			list = CacheManager.getInstance().getCacher().getList(HouseSpaceResult.class, key);
			//////System.out.println("HC");
			if (CustomerListUtils.isEmpty(list)) {
				//list = spaceCommonService.getPaginatedHouseSpaceList(houseId, start, limit);
				list = spaceCommonService.getPaginatedHouseSpaceList(spaceCommon,userId);
				//////System.out.println("db1");
				if (CustomerListUtils.isEmpty(list)) {
					CacheManager.getInstance().getCacher().setObject(key, list, 0);
					//////System.out.println("db2");
				}

			}
		}
		return list;
	}
	
	/***
	 * 房子空间列表
	 * 
	 * @param houseId
	 * @return total
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//public static List<HouseSpaceResult> getPaginatedHouseSpaceList2(int houseId, int start, int limit,int userType) {
	public static List<HouseSpaceResult> getPaginatedHouseSpaceList2(SpaceCommon spaceCommon,int userId) {
		Integer houseId=spaceCommon.getHouseId();
		Integer start=spaceCommon.getStart();
		Integer limit=spaceCommon.getLimit();
		Integer userType=spaceCommon.getUserType();
		List<HouseSpaceResult> list = new ArrayList<HouseSpaceResult>();
		Map map = new HashMap();
		map.put("houseId", houseId);
		map.put("start", start);
		map.put("limit", limit);
		map.put("userType", userType);
		map.put("blackList", spaceCommon.getBlackList());
		String key = KeyGenerator.getKeyWithMap(ModuleType.HouseSpaceResult, map);

		if (CacheManager.getInstance().getCacher() != null) {
			list = CacheManager.getInstance().getCacher().getList(HouseSpaceResult.class, key);
			//////System.out.println("HC");
			if (CustomerListUtils.isEmpty(list)) {
				list = spaceCommonService.getPaginatedHouseSpaceList2(spaceCommon,userId);
				//////System.out.println("db1");
				if (CustomerListUtils.isEmpty(list)) {
					CacheManager.getInstance().getCacher().setObject(key, list, 0);
					//////System.out.println("db2");
				}

			}
		}
		return list;
	}
	
	/***
	 * 获取带查询参数的通用空间的分页记录
	 * 
	 * @param spaceCommon
	 * @return
	 */
	public static List<SpaceCommon> getCapacityParameter(SpaceCommon spaceCommon) {
		List<SpaceCommon> lstCommon = Lists.newArrayList();
		PageParameter parameter = getPageParameter(spaceCommon);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.SpaceCommon, parameter);
		lstCommon = CacheManager.getInstance().getCacher().getList(SpaceCommon.class, key);
		/*去除缓存测试*/
		if (CustomerListUtils.isEmpty(lstCommon)) {
			lstCommon = spaceCommonService.spaceCapacityList(spaceCommon);
			CacheManager.getInstance().getCacher().setObject(key, lstCommon, 0);
		}
		return lstCommon;
	}
	
	/***
	 * 获取带查询参数的通用空间总记录数
	 * 
	 * @param spaceCommon
	 * @return
	 */
	public static int getTotalParameter(SpaceCommon spaceCommon) {
		int total = 0;
		PageParameter parameter = getPageParameter(spaceCommon);
		String key = KeyGenerator.getTotalWithParameter(ModuleType.SpaceCommon, parameter);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = spaceCommonService.spaceCapacityCount(spaceCommon);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
		}
		return total;
	}
	
}
