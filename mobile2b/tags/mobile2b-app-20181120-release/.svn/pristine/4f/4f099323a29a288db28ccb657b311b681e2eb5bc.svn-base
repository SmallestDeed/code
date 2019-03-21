package com.nork.design.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.search.DesignPlanProductSearch;
import com.nork.design.service.DesignPlanProductService;
import com.nork.design.service.DesignPlanService;
import com.nork.design.service.DesignTempletService;
import com.nork.design.service.OptimizePlanService;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.model.BaseProduct;
import com.nork.product.service.BaseProductService;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.SysDictionaryService;
import com.nork.task.service.SysTaskService;

/***
 * 设计模块-设计方案 缓存层
 * 
 * @author zhao.bing.lin
 * @date 2016-05-011
 */
public class DesignPlanCacher {
	@SuppressWarnings("unused")
	private static SysDictionaryService sysDictionaryService = SpringContextHolder.getBean(SysDictionaryService.class);
	private static DesignPlanService designPlanService = SpringContextHolder.getBean(DesignPlanService.class);
	private static DesignTempletService designTempletService = SpringContextHolder.getBean(DesignTempletService.class);
	private static SpaceCommonService spaceCommonService = SpringContextHolder.getBean(SpaceCommonService.class);
	private static DesignPlanProductService designPlanProductService = SpringContextHolder
			.getBean(DesignPlanProductService.class);
	private static BaseProductService baseProductService = SpringContextHolder.getBean(BaseProductService.class);
	private static SysTaskService sysTaskService  = SpringContextHolder.getBean(SysTaskService.class);
	private static OptimizePlanService optimizePlanService  = SpringContextHolder.getBean(OptimizePlanService.class);



	/***
	 * 设计方案
	 * 
	 * @param planId
	 * @author Administrator
	 * @return DesignPlan
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DesignPlan getDesignPlan(Integer planId) {
		DesignPlan designPlan = new DesignPlan();
		Map map = new HashMap();
		map.put("planId", planId);
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignPlan, map);
		if (CacheManager.getInstance().getCacher() != null) {
			designPlan = (DesignPlan) CacheManager.getInstance().getCacher().getObject(key);
			if (designPlan == null) {
				designPlan = designPlanService.get(planId);
				if (designPlan != null) {
					CacheManager.getInstance().getCacher().setObject(key, designPlan, 0);
				}
			}
		}
		//////System.out.println("********WebDesignPlanController-delRenderPic and designPlanClosed-getDesignPlan-key" + key+ "********");
		return designPlan;
	}
	/***
	 * 设计方案
	 * 
	 * @param planId
	 * @author Administrator
	 * @return DesignPlan
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DesignPlan getDesignPlan2(Integer planId) {
		DesignPlan designPlan = new DesignPlan();
		Map map = new HashMap();
		map.put("planId", planId);
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignPlan, map);
		if (CacheManager.getInstance().getCacher() != null) {
			designPlan = (DesignPlan) CacheManager.getInstance().getCacher().getObject(key);
			if (designPlan == null) {
				designPlan = optimizePlanService.getPlan(planId);
				if (designPlan != null) {
					CacheManager.getInstance().getCacher().setObject(key, designPlan, 0);
				}
			}
		}
		//////System.out.println("********WebDesignPlanController-delRenderPic and designPlanClosed-getDesignPlan-key" + key+ "********");
		return designPlan;
	}

	/***
	 * 在 resPicService.delete(fileId); 执行后 ，清理掉 以 fileId 未key 值 的缓存
	 * 
	 * @name removeResPic
	 * @param fileId
	 * @author Administrator
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void removeResPic(Integer id) {
		Map map = new HashMap<>();
		map.put("id", id);
		String key = KeyGenerator.getKeyWithMap(ModuleType.ResPic, map);
		CacheManager.getInstance().getCacher().remove(ModuleType.ResPic,key);
	}

	/***
	 * 在 designPlanService.update(designPlan); 执行后 ，清理掉 以
	 * 
	 * @name removeObjectResPic
	 * @param renderPicObj
	 * @author Administrator
	 * @return
	 */
	public static void removeObjectResPic(DesignPlan designPlan) {
		String key ="";
		CacheManager.getInstance().getCacher().removeObject(ModuleType.DesignPlan, key);
	}

	/***
	 * 设计模板
	 * 
	 * @param designTempletId
	 * @author Administrator
	 * @return DesignTemplet
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DesignTemplet getDesignTemplet(Integer designTempletId) {
		DesignTemplet dPlan = new DesignTemplet();
		Map map = new HashMap();
		map.put("designTempletId", designTempletId);
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignTemplet, map);
		if (CacheManager.getInstance().getCacher() != null) {
			dPlan = (DesignTemplet) CacheManager.getInstance().getCacher().getObject(key);
			if (dPlan == null) {
				dPlan = designTempletService.get(designTempletId);
				if (dPlan != null) {
					CacheManager.getInstance().getCacher().setObject(key, dPlan, 0);
				}
			}
		}

		//////System.out.println("********WebDesignPlanController-recommendPlanListWeb-getDesignTemplet-key" + key + "********");
		return dPlan;
	}

	/***
	 * 通用（公共）空间
	 * 
	 * @author Administrator
	 * @return DesignTemplet
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SpaceCommon getSpaceCommonOne(Integer spaceCommonId) {
		SpaceCommon sCommon = new SpaceCommon();
		Map map = new HashMap();
		map.put("spaceCommonId", spaceCommonId);
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignTemplet, map);
		if (CacheManager.getInstance().getCacher() != null) {
			sCommon = (SpaceCommon) CacheManager.getInstance().getCacher().getObject(key);
			if (sCommon == null) {
				sCommon = spaceCommonService.get(spaceCommonId);
				if (sCommon != null) {
					CacheManager.getInstance().getCacher().setObject(key, sCommon, 0);
				}
			}
		}

		//////System.out.println("********WebDesignPlanController-recommendPlanListWeb-getSpaceCommonOne-key" + key + "********");
		return sCommon;
	}

	/***
	 * 通用（公共）空间
	 * 
	 * @author Administrator
	 * @return DesignTemplet
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SpaceCommon getSpaceCommonTwo(Integer spaceCommonId) {
		SpaceCommon sCommon = new SpaceCommon();
		Map map = new HashMap();
		map.put("spaceCommonId", spaceCommonId);
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignPlan, map);
		if (CacheManager.getInstance().getCacher() != null) {
			sCommon = (SpaceCommon) CacheManager.getInstance().getCacher().getObject(key);
			if (sCommon == null) {
				sCommon = spaceCommonService.get(spaceCommonId);
				if (sCommon != null) {
					CacheManager.getInstance().getCacher().setObject(key, sCommon, 0);
				}
			}
		}

		//////System.out.println("********WebDesignPlanController-recommendPlanListWeb-getSpaceCommonTwo-key" + key + "********");
		return sCommon;
	}

	/***
	 * 产品设计方案总条数
	 * 
	 * @param designPlanProductSearch
	 * @author Administrator
	 * @return total
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int getDesignPlanProductCount(DesignPlanProductSearch designPlanProductSearch) {

		Map map = new HashMap();

		Integer start = designPlanProductSearch.getStart();
		Integer limit = designPlanProductSearch.getLimit();
		String msgId = designPlanProductSearch.getMsgId();
		Integer planId = designPlanProductSearch.getPlanId();
		Integer isDeleted = designPlanProductSearch.getIsDeleted();

		map.put("start", start);
		map.put("limit", limit);
		map.put("Count", "Count");
		map.put("msgId", msgId);
		map.put("planId", planId);
		map.put("isDeleted", isDeleted);

		int total = 0;
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignPlanProduct, map);
		if (CacheManager.getInstance().getCacher() != null) {
			String totalString = CacheManager.getInstance().getCacher().get(key);
			if (totalString != null && !"".equals(totalString)) {
				total = Integer.parseInt(totalString);
			} else {
				total = designPlanProductService.getCount(designPlanProductSearch);
				if (total > 0) {
					CacheManager.getInstance().getCacher().setObject(key, String.valueOf(total), 0);
				}
			}
		}
		//////System.out.println("********WebDesignPlanProductController-deletedList-getDesignPlanProductCount-key：" + key + "********");
		return total;
	}

	/***
	 * 产品设计方案
	 * 
	 * @param designPlanProductSearch
	 * @author Administrator
	 * @return total
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<DesignPlanProduct> getPaginatedList(DesignPlanProductSearch designPlanProductSearch) {
		List<DesignPlanProduct> list = new ArrayList<DesignPlanProduct>();
		Map map = new HashMap();

		Integer start = designPlanProductSearch.getStart();
		Integer limit = designPlanProductSearch.getLimit();
		String msgId = designPlanProductSearch.getMsgId();
		Integer planId = designPlanProductSearch.getPlanId();
		Integer isDeleted = designPlanProductSearch.getIsDeleted();

		map.put("start", start);
		map.put("limit", limit);
		map.put("msgId", msgId);
		map.put("planId", planId);
		map.put("isDeleted", isDeleted);

		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignPlanProduct, map);
		if (CacheManager.getInstance().getCacher() != null) {
			list = CacheManager.getInstance().getCacher().getList(DesignPlanProduct.class, key);
			if (CustomerListUtils.isNotEmpty(list)) {
				list = designPlanProductService.getPaginatedList(designPlanProductSearch);
				if (!CustomerListUtils.isNotEmpty(list)) {
					CacheManager.getInstance().getCacher().setObject(key, list, 0);
				}
			}
		}
		System.out
				.println("********WebDesignPlanProductController-deletedList-getPaginatedList-key：" + key + "********");
		return list;
	}

	/***
	 * 根据productId获取 产品基础
	 * 
	 * @param productId
	 * @author Administrator
	 * @return BaseProduct
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BaseProduct getBaseProduct(Integer productId) {
		BaseProduct baseProduct = new BaseProduct();
		Map map = new HashMap();
		map.put("productId", productId);

		String key = KeyGenerator.getKeyWithMap(ModuleType.BaseProduct, map);
		if (CacheManager.getInstance().getCacher() != null) {
			baseProduct = (BaseProduct) CacheManager.getInstance().getCacher().getObject(key);
			if (baseProduct == null) {
				baseProduct = baseProductService.get(productId);
				if (baseProduct != null) {
					CacheManager.getInstance().getCacher().setObject(key, baseProduct, 0);
				}
			}
		}
		//////System.out.println("********WebDesignPlanProductController-deletedList-getBaseProduct-key：" + key + "********");
		return baseProduct;

	}


	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void removeDesignPlan(DesignPlan designPlan) {
		Map map = new HashMap();
		String msgId = designPlan.getMsgId();
		Integer id = designPlan.getId();
		map.put("msgId", msgId);
		map.put("id", id);
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignPlan, map);
		CacheManager.getInstance().getCacher().remove(ModuleType.DesignPlan,key);
	}

	/***
	 * 获得产品设计方案所有数据
	 * 
	 * @param designPlanProduct
	 * @author Administrator
	 * @return List<DesignPlanProduct>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<DesignPlanProduct> getDesignPlanProductList(DesignPlanProduct designPlanProduct) {
		List<DesignPlanProduct> list = new ArrayList<DesignPlanProduct>();

		Map map = new HashMap();
		Integer isDeleted = designPlanProduct.getIsDeleted();
		Integer planId = designPlanProduct.getPlanId();
		if(isDeleted!=null){
		   map.put("isDeleted", isDeleted);
		}
		if(planId!=null){
		   map.put("planId", planId);
		}
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignPlanProduct, map);
		if (CacheManager.getInstance().getCacher() != null) {
			list = CacheManager.getInstance().getCacher().getList(DesignPlanProduct.class, key);
			if (CustomerListUtils.isEmpty(list)) {
				list = designPlanProductService.getList(designPlanProduct);
				if (!CustomerListUtils.isEmpty(list)) {
					CacheManager.getInstance().getCacher().setObject(key, list, 0);
				}
			}
		}
		//////System.out.println("********WebDesignPlanController-saveDesignModeWeb-getDesignPlanProductList-key" + key + "********");
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SysDictionary getSysDictionaryByValue(String type, Integer value, HttpServletRequest request) {
		SysDictionary dictionary = new SysDictionary();

		Map map = new HashMap();
		map.put("type", type);
		map.put("value", value);
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignPlanProduct, map);
		if (CacheManager.getInstance().getCacher() != null) {
			dictionary = (SysDictionary) CacheManager.getInstance().getCacher().getObject(key);
			if (dictionary == null) {
				dictionary = sysDictionaryService.getSysDictionaryByValue(type, value);
				if (dictionary != null) {
					CacheManager.getInstance().getCacher().setObject(key, dictionary, 0);
				}
			}

		}
		//////System.out.println("********WebDesignPlanController-saveDesignModeWeb-getSysDictionaryByValue-key" + key + "********");
		return dictionary;
	}

	public static void removeDesignPlanProduct(DesignPlanProduct designPlanProduct) {
		String key = "";
		CacheManager.getInstance().getCacher().removeObject(ModuleType.DesignPlanProduct, key);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void removeDesignPlanProduct(Integer id) {
		Map map = new HashMap();
		map.put("id", id);
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignPlanProduct, map);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.DesignPlanProduct, key);
	}


	public static void removeObjectDesignPlan(DesignPlan designPlan) {
		String key = "";
		CacheManager.getInstance().getCacher().removeObject(ModuleType.DesignPlan, key);
	}

	public static void removeObjectDesignPlanNew() {
		String key = "";
		CacheManager.getInstance().getCacher().removeObject(ModuleType.DesignPlan, key);
	}
	

}
