package com.nork.design.cache;

import java.util.List;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.metadata.PageParameter;
import com.nork.common.metadata.QueryParameter;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.model.UsedProducts;
import com.nork.design.model.UserProductPlan;
import com.nork.design.model.search.UsedProductsSearch;
import com.nork.design.service.UsedProductsService;

/***
 * 设计方案已使用产品缓存层
 * 
 * @author qiu.jun
 * @date 2016-05-11
 */
public class UsedProductsCacher {
	private static UsedProductsService usedProductsService = SpringContextHolder.getBean(UsedProductsService.class);

	private static PageParameter getPageParameter(UsedProductsSearch search){
		PageParameter parameter=new PageParameter();
		List<QueryParameter> lstParameter=Lists.newArrayList();
		QueryParameter qp=null;
		parameter.setPageIndex(search.getStart());
		parameter.setPageSize(search.getLimit());
		
		qp=new QueryParameter();
		qp.setName("designId");
		qp.setValue(String.valueOf(search.getDesignId()));
		lstParameter.add(qp);
		
		parameter.setLstParameter(lstParameter);
		return parameter;
	}
    
	
    /***
	 * 获取单个设计方案已使用产品详情
	 * 
	 * @param id
	 * @return
	 */
	public static UsedProducts get(int id) {
		UsedProducts usedProducts = null;
		String key=KeyGenerator.getIdKey(ModuleType.DesignUsedProducts, id);
		usedProducts = (UsedProducts) CacheManager.getInstance().getCacher().getObject(key);
		if (usedProducts == null) {
			usedProducts = usedProductsService.get(id);
			if (usedProducts != null) {
				CacheManager.getInstance().getCacher().setObject(key, usedProducts, 0);
			}
		}
		return usedProducts;
	}
	
	/***
	 * 根据条件获取已使用产品的总记录数
	 * @param spaceCommon
	 * @return
	 */
	public static int getCount(UsedProductsSearch search){
		int total=0;
		PageParameter parameter= getPageParameter(search);
		String key=KeyGenerator.getTotalWithParameter(ModuleType.DesignUsedProducts, parameter);
		String temp=CacheManager.getInstance().getCacher().get(key);
		if(StringUtils.isBlank(temp)){
			total=usedProductsService.getUsedProductPlanCount(search);
			if(total>0){
			   CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
			}
		}
		else{
			total=Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:"+key);
		}
		return total;
	}
    
	/***
	 * 根据条件分页获取已使用产品
	 * @param spaceCommon
	 * @return
	 */
	public static List<UserProductPlan> getList(UsedProductsSearch search){
		List<UserProductPlan> lstCommon=Lists.newArrayList();
		PageParameter parameter= getPageParameter(search);
		String key=KeyGenerator.getPageQueryKeyParameter(ModuleType.DesignUsedProducts, parameter);
		lstCommon=CacheManager.getInstance().getCacher().getList(UserProductPlan.class, key);
		if(CustomerListUtils.isEmpty(lstCommon)){
			lstCommon= usedProductsService.getUsedProductPlanList(search);
			CacheManager.getInstance().getCacher().setObject(key, lstCommon, 0);
		}
		else{
			//////System.out.println("get from cacher,key:"+key);
		}
		return lstCommon;
	}
	
	
	/***
	 * 删除单个已使用产品的缓存
	 * @param id
	 */
	public static void remove(int id) {
		String key = KeyGenerator.getIdKey(ModuleType.DesignUsedProducts, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.DesignUsedProducts,key);
		//////System.out.println("remove used products cache");
	}
}
