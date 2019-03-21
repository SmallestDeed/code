package com.nork.product.cache;

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
import com.nork.product.model.UserProductCollect;
import com.nork.product.model.UserProductsConllect;
import com.nork.product.model.search.UserProductCollectSearch;
import com.nork.product.service.UserProductCollectService;

/***
 * 用户收藏产品缓存层
 * 
 * @author qiu.jun
 * @date 2016-05-13
 */
public class UserProductCollectCacher {
	private static UserProductCollectService userProductCollectService = SpringContextHolder
			.getBean(UserProductCollectService.class);

	private static PageParameter getPageParameter(UserProductsConllect userProductsCollect) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(userProductsCollect.getStart());
		parameter.setPageSize(userProductsCollect.getLimit());

		if (userProductsCollect.getUserId() != null && userProductsCollect.getUserId() != -1) {
			qp = new QueryParameter();
			qp.setName("userId");
			qp.setValue(String.valueOf(userProductsCollect.getUserId()));
			lstParameter.add(qp);
		}
		if (userProductsCollect.getProductId() != null) {
			qp = new QueryParameter();
			qp.setName("productId");
			qp.setValue(String.valueOf(userProductsCollect.getProductId()));
			lstParameter.add(qp);
		}

		parameter.setLstParameter(lstParameter);
		return parameter;
	}

	private static PageParameter getParameter(UserProductCollect productCollect) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(productCollect.getStart());
		parameter.setPageSize(productCollect.getLimit());

		if (productCollect.getProductId() != null) {
			qp = new QueryParameter();
			qp.setName("productId");
			qp.setValue(String.valueOf(productCollect.getProductId()));
			lstParameter.add(qp);
		}

		parameter.setLstParameter(lstParameter);
		return parameter;
	}

	/***
	 * 获取产品收藏总记录数
	 * 
	 * @param spaceCommon
	 * @return
	 */
	/**public static int getTotalWithParameter(UserProductsConllect userProductsCollect) {
		int total = 0;
		PageParameter parameter = getPageParameter(userProductsCollect);
		String key = KeyGenerator.getTotalWithParameter(ModuleType.UserProductsCollect, parameter);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = userProductCollectService.getUserProductsConllectCount(userProductsCollect);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
			//////System.out.println("DB中");
		} else {
			total = Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:" + key);
			//////System.out.println("缓存中");
		}
		return total; 
	}*/ 

	
	
	/***
	 * 获取产品收藏总记录数
	 * 
	 * @param spaceCommon
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int getTotalWithParameter(UserProductsConllect userProductsCollect) {
		int total = 0;
		Map map = new HashMap();
		map.put("start", userProductsCollect.getStart());
		map.put("limit", userProductsCollect.getLimit());
		map.put("msgId", userProductsCollect.getMsgId());
		map.put("productTypeValue", userProductsCollect.getProductTypeValue());
		map.put("catalogName", userProductsCollect.getCatalogName());
		map.put("Count", "Count");
		
		String key = KeyGenerator.getKeyWithMap(ModuleType.UserProductsCollect, map);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = userProductCollectService.getUserProductsConllectCount(userProductsCollect);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:" + key);
		}
		return total; 
	}
	
	
	/***
	 * 获取带查询参数的产品收藏的分页记录
	 * 
	 * @param userProductsCollect
	 * @return
	 */
	/**public static List<UserProductsConllect> getPageWithParameter(UserProductsConllect userProductsCollect) {
		List<UserProductsConllect> lstProductsCollect = Lists.newArrayList();
		PageParameter parameter = getPageParameter(userProductsCollect);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.UserProductsCollect, parameter);
		lstProductsCollect = CacheManager.getInstance().getCacher().getList(UserProductsConllect.class, key);
		//////System.out.println("List HC中");
		if (CustomerListUtils.isEmpty(lstProductsCollect)) {
			lstProductsCollect = userProductCollectService.getAllList(userProductsCollect);
			CacheManager.getInstance().getCacher().setObject(key, lstProductsCollect, 0);
			//////System.out.println("List DB中");
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstProductsCollect;
	}*/

	
	
	/***
	 * 获取带查询参数的产品收藏的分页记录
	 * 
	 * @param userProductsCollect
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<UserProductsConllect> getPageWithParameter(UserProductsConllect userProductsCollect) {
		List<UserProductsConllect> lstProductsCollect = Lists.newArrayList();
		
		Map map = new HashMap();
		map.put("start", userProductsCollect.getStart());
		map.put("limit", userProductsCollect.getLimit());
		map.put("msgId", userProductsCollect.getMsgId());
		map.put("productTypeValue", userProductsCollect.getProductTypeValue());
		map.put("catalogName", userProductsCollect.getCatalogName());
		
		String key = KeyGenerator.getKeyWithMap(ModuleType.UserProductsCollect, map);
		
		lstProductsCollect = CacheManager.getInstance().getCacher().getList(UserProductsConllect.class, key);
		if (CustomerListUtils.isEmpty(lstProductsCollect)) {
			lstProductsCollect = userProductCollectService.getAllList(userProductsCollect);
			CacheManager.getInstance().getCacher().setObject(key, lstProductsCollect, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstProductsCollect;
	}
	
	
	/***
	 * 获取带查询参数的产品收藏的所有记录
	 * 
	 * @param userProductsCollect
	 * @return
	 */
	public static List<UserProductCollect> getList(UserProductCollect productCollect) {
		List<UserProductCollect> lstProductsCollect = Lists.newArrayList();
		PageParameter parameter = getParameter(productCollect);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.UserProductsCollect, parameter);
		lstProductsCollect = CacheManager.getInstance().getCacher().getList(UserProductCollect.class, key);
		if (CustomerListUtils.isEmpty(lstProductsCollect)) {
			lstProductsCollect = userProductCollectService.getList(productCollect);
			CacheManager.getInstance().getCacher().setObject(key, lstProductsCollect, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstProductsCollect;
	}
	
	
	public static Integer getCount(UserProductCollectSearch userProductCollectSearch) {
		Integer count = null;
		String tmp=null;
		PageParameter parameter = getParameter(userProductCollectSearch);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.UserProductsCollect, parameter);
		tmp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(tmp)) {
			count = userProductCollectService.getCount(userProductCollectSearch);
			CacheManager.getInstance().getCacher().setObject(key, count+"", 0);
		} else {
			count=Integer.parseInt(tmp);
		}
		return count;
	}

	/***
	 * 移除单个产品收藏的缓存
	 * 
	 * @param id
	 */
	public static void remove(int id) {
		String key = KeyGenerator.getIdKey(ModuleType.UserProductsCollect, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.UserProductsCollect, key);
	}
}
