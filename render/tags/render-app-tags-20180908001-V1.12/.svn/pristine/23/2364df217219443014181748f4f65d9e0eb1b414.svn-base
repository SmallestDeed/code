package com.nork.design.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.metadata.PageParameter;
import com.nork.common.metadata.QueryParameter;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletProduct;
import com.nork.design.model.search.DesignPlanSearch;
import com.nork.design.model.search.DesignTempletProductSearch;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletProductService;
import com.nork.design.service.DesignTempletService;

/***
 * 设计方案缓存层
 * 
 * @author qiu.jun
 * @date 2016-05-10
 */
public class DesignCacher {
	private static Logger logger = Logger.getLogger(DesignCacher.class);
	private static DesignPlanService designPlanService = SpringContextHolder.getBean(DesignPlanService.class);
	private static DesignTempletService designTempletService = SpringContextHolder.getBean(DesignTempletService.class);
	private static DesignTempletProductService templetProductService = SpringContextHolder
			.getBean(DesignTempletProductService.class);
	private static DesignPlanProductService designPlanProductService = SpringContextHolder
			.getBean(DesignPlanProductService.class);

	private static PageParameter getPageParameter(DesignPlan plan) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(plan.getStart());
		parameter.setPageSize(plan.getLimit());
		
		if (plan.getCreator() != null) {
			qp = new QueryParameter();
			qp.setName("creator");
			qp.setValue(plan.getCreator());
			lstParameter.add(qp);
		}
		if (plan.getDesignId() != null) {
			qp = new QueryParameter();
			qp.setName("designId");
			qp.setValue(String.valueOf(plan.getDesignId()));
			lstParameter.add(qp);
		}
		if (plan.getUserId()!=null && plan.getUserId() != -1) {
			qp = new QueryParameter();
			qp.setName("userId");
			qp.setValue(String.valueOf(plan.getUserId()));
			lstParameter.add(qp);
		}
		if (plan.getDesignSourceType() != null) {
			qp = new QueryParameter();
			qp.setName("designSourceType");
			qp.setValue(String.valueOf(plan.getDesignSourceType()));
			lstParameter.add(qp);
		}
		if (plan.getMediaType() != null) {
			qp = new QueryParameter();
			qp.setName("mediaType");
			qp.setValue(String.valueOf(plan.getMediaType()));
			lstParameter.add(qp);
		}
		if (plan.getSpaceFunctionId() != null) {
			qp = new QueryParameter();
			qp.setName("spaceFunctionId");
			qp.setValue(String.valueOf(plan.getSpaceFunctionId()));
			lstParameter.add(qp);
		}
		if (StringUtils.isNotBlank(plan.getOrder())) {
			qp = new QueryParameter();
			qp.setName("order");
			qp.setValue(plan.getOrder().replace(" ", "_"));
			lstParameter.add(qp);
		}
		if (StringUtils.isNotBlank(plan.getOrders())) {
			qp = new QueryParameter();
			qp.setName("orders");
			qp.setValue(plan.getOrders().replace(" ", "_"));
			lstParameter.add(qp);
		}
		
		parameter.setLstParameter(lstParameter);
		return parameter;
	}

	/***
	 * 获取单个设计方案详情
	 * 
	 * @param id
	 * @return
	 */
	public static DesignPlan get(int id) {
		DesignPlan plan = null;
		String key = KeyGenerator.getIdKey(ModuleType.DesignPlan, id);
		plan = (DesignPlan) CacheManager.getInstance().getCacher().getObject(key);
		if (plan == null) {
			plan = designPlanService.get(id);
			if (plan != null) {
				CacheManager.getInstance().getCacher().setObject(key, plan, 0);
			}
		}
		return plan;
	}

	/***
	 * 根据SysCode获取所有设计方案
	 * 
	 * @return
	 */
	public static List<DesignPlan> getAllPlantListWithSysCode(String sysCode) {
		List<DesignPlan> lstTemplet = Lists.newArrayList();
    	Map<String, String> map=new HashMap<String, String>();
    	map.put("sysCode", sysCode);
    	String key=KeyGenerator.getAllListKeyWithMap(ModuleType.DesignPlan,map);
		if (CacheManager.getInstance().getCacher() != null) {
			lstTemplet = CacheManager.getInstance().getCacher().getList(DesignPlan.class, key);
			if (lstTemplet == null) {
				DesignPlan desPlan = new DesignPlan();
				desPlan.setSysCode(sysCode);
				lstTemplet = designPlanService.getList(desPlan);
				CacheManager.getInstance().getCacher().setObject(key, lstTemplet, 0);
			}
		}
		return lstTemplet;
	}

	/***
	 * 获取单个设计方案样板房详情
	 * 
	 * @param id
	 * @return
	 */
	public static DesignTemplet getTemplet(int id) {
		DesignTemplet templet = null;
		String key = KeyGenerator.getIdKey(ModuleType.DesignTemplet, id);
		templet = (DesignTemplet) CacheManager.getInstance().getCacher().getObject(key);
		if (templet == null) {
			templet = designTempletService.get(id);
			if (templet != null) {
				CacheManager.getInstance().getCacher().setObject(key, templet, 0);
			}
		}
		return templet;
	}

	/***
	 * 获取所有设计模板产品
	 * 
	 * @return
	 */
	public static List<DesignTempletProduct> getAllTempletProductList() {
		List<DesignTempletProduct> lstTemplet = Lists.newArrayList();
		String key = KeyGenerator.getAllListKey(ModuleType.DesignProduct);
		if (CacheManager.getInstance().getCacher() != null) {
			lstTemplet = CacheManager.getInstance().getCacher().getList(DesignTempletProduct.class, key);
			if (lstTemplet == null) {
				DesignTempletProductSearch search = new DesignTempletProductSearch();
				lstTemplet = templetProductService.getList(search);
				if (CustomerListUtils.isNotEmpty(lstTemplet)) {
					CacheManager.getInstance().getCacher().setObject(key, lstTemplet, 0);
				}
			}
		}
		return lstTemplet;
	}
	
	/***
	 * 根据条件获取所有设计模板产品
	 * 
	 * @return
	 */
	public static List<DesignTempletProduct> getTempletProductList(DesignTempletProduct templetProduct) {
		List<DesignTempletProduct> lstTemplet = Lists.newArrayList();
		Map<String, String> map=new HashMap<String, String>();
		map.put("func", "templetproduct");
		map.put("designTempletId", String.valueOf(templetProduct.getDesignTempletId()));
		String key = KeyGenerator.getAllCountKeyWithMap(ModuleType.DesignProduct,map);
		if (CacheManager.getInstance().getCacher() != null) {
			lstTemplet = CacheManager.getInstance().getCacher().getList(DesignTempletProduct.class, key);
			if (lstTemplet == null) {
				
				lstTemplet = templetProductService.getList(templetProduct);
				if (CustomerListUtils.isNotEmpty(lstTemplet)) {
					CacheManager.getInstance().getCacher().setObject(key, lstTemplet, 0);
				}
			}
		}
		return lstTemplet;
	}

	/***
	 * 根据设计方案ID获取所有设计方案产品
	 * 
	 * @return
	 */
	public static List<DesignPlanProduct> getAllPlanProductList(int planId) {
		List<DesignPlanProduct> lstPlanProduct = Lists.newArrayList();
    	Map<String, String> map=new HashMap<String, String>();
    	map.put("planid", String.valueOf(planId));
    	String key=KeyGenerator.getAllListKeyWithMap(ModuleType.DesignTemplet,map);
		if (CacheManager.getInstance().getCacher() != null) {
			lstPlanProduct = CacheManager.getInstance().getCacher().getList(DesignPlanProduct.class, key);
			if (lstPlanProduct == null) {
				DesignPlanProduct designPlanProduct = new DesignPlanProduct();
				designPlanProduct.setIsDeleted(0);
				designPlanProduct.setPlanId(planId);
				lstPlanProduct = designPlanProductService.getList(designPlanProduct);
				CacheManager.getInstance().getCacher().setObject(key, lstPlanProduct, 0);
			}
		}
		return lstPlanProduct;
	}

	/***
	 * 获取设计方案总记录数
	 * 
	 * @param spaceCommon
	 * @return
	 */
	public static int getPlanCount(DesignPlan plan) {
		int total = 0;
		PageParameter parameter = getPageParameter(plan);
		String key = KeyGenerator.getTotalWithParameter(ModuleType.DesignPlan, parameter);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = designPlanService.getPlanCount(plan);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:" + key);
		}
		return total;
	}

	/***
	 * 获取设计方案列表
	 * 
	 * @param plan
	 * @return
	 */
	public static List<DesignPlan> getPlanList(DesignPlan plan) {
		List<DesignPlan> lstPlan = Lists.newArrayList();
		PageParameter parameter = getPageParameter(plan);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.DesignPlan, parameter);
		lstPlan = CacheManager.getInstance().getCacher().getList(DesignPlan.class, key);
		//////System.out.println("缓存中取...");
		if (CustomerListUtils.isEmpty(lstPlan)) {
			lstPlan = designPlanService.getPlanList(plan);
			CacheManager.getInstance().getCacher().setObject(key, lstPlan, 0);
			//////System.out.println("DB中取...");
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlan;
	}
	
	/***
	 * 获取设计方案列表
	 * 
	 * @param plan
	 * @return
	 */
	public static List<DesignPlan> getPaginatedList(DesignPlanSearch planSearch) {
		List<DesignPlan> lstPlan = Lists.newArrayList();
		PageParameter parameter = getPageParameter(planSearch);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.DesignPlan, parameter);
		key+="func_list";
		lstPlan = CacheManager.getInstance().getCacher().getList(DesignPlan.class, key);
		//////System.out.println("缓存中取...");
		if (CustomerListUtils.isEmpty(lstPlan)) {
			lstPlan = designPlanService.getPaginatedList(planSearch);
			if(lstPlan != null && lstPlan.size()>0){
				logger.info("designPlanId----------"+lstPlan.get(0).getId());
			}else{
				logger.info("designPlanId is null,tempalteId ="+planSearch.getDesignId());
			}
			CacheManager.getInstance().getCacher().setObject(key, lstPlan, 0);
			//////System.out.println("DB中取...");
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlan;
	}

	/***
	 * 移除单个设计方案缓存
	 * 
	 * @param id
	 */
	public static void removePlan(int id) {
		String key = KeyGenerator.getIdKey(ModuleType.DesignPlan, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.DesignPlan,key);
	}

	/***
	 * 移除单个设计方案模板缓存
	 * 
	 * @param id
	 */
	public static void removeTemplet(int id) {
		String key = KeyGenerator.getIdKey(ModuleType.DesignTemplet, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.DesignTemplet,key);
	}
	
	public static void updatePlan(DesignPlan plan){
		String key = KeyGenerator.getIdKey(ModuleType.DesignPlan, plan.getId());
		CacheManager.getInstance().getCacher().removeObject(key);
		if (plan != null) {
			CacheManager.getInstance().getCacher().setObject(key, plan, 0);
		}
	}
	
//	public static void deletedPlan(){
//		String key = KeyGenerator.getAllCountKey(ModuleType.DesignPlan);
//		CacheManager.getInstance().getCacher().removeObject(key);
//	}
	

}
