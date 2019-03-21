package com.nork.onekeydesign.cache;

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
import com.nork.onekeydesign.model.DesignPlanProduct;
import com.nork.onekeydesign.model.DesignPlanProductResult;
import com.nork.onekeydesign.model.DesignTempletProduct;
import com.nork.onekeydesign.model.ProductCostDetail;
import com.nork.onekeydesign.model.ProductsCost;
import com.nork.onekeydesign.model.search.DesignPlanProductSearch;
import com.nork.onekeydesign.service.DesignPlanProductService;
import com.nork.onekeydesign.service.DesignTempletProductService;
import com.nork.onekeydesign.service.OptimizePlanService;
import com.nork.designconfig.service.DesignRulesService;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.UserProductConfig;
import com.nork.product.service.BaseBrandService;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.UserProductConfigService;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.SysDictionaryService;

/***
 * 设计方案成品库缓存层
 * 
 * @author zhao.bing.lin
 * @date 2016-05-12
 */
public class DesignPlanProductCacher {
	private static UserProductConfigService userProductConfigService = SpringContextHolder
			.getBean(UserProductConfigService.class);
	@SuppressWarnings("unused")
	private static SysDictionaryService sysDictionaryService = SpringContextHolder.getBean(SysDictionaryService.class);
	private static DesignPlanProductService designPlanProductService = SpringContextHolder
			.getBean(DesignPlanProductService.class);
	private static BaseProductService baseProductService = SpringContextHolder.getBean(BaseProductService.class);
	private static ResFileService resFileService = SpringContextHolder.getBean(ResFileService.class);
	private static ResModelService resModelService = SpringContextHolder.getBean(ResModelService.class);
	private static BaseBrandService baseBrandService = SpringContextHolder.getBean(BaseBrandService.class);
	private static DesignTempletProductService designTempletProductService = SpringContextHolder
			.getBean(DesignTempletProductService.class);
	private static DesignRulesService designRulesService = SpringContextHolder.getBean(DesignRulesService.class);
	private static OptimizePlanService optimizePlanService = SpringContextHolder.getBean(OptimizePlanService.class);

	private static PageParameter getPageParameter(DesignPlanProduct designPlanProduct) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(designPlanProduct.getStart());
		parameter.setPageSize(designPlanProduct.getLimit());

		if (designPlanProduct.getUserId() != null && designPlanProduct.getUserId() != -1) {
			qp = new QueryParameter();
			qp.setName("userId");
			qp.setValue(String.valueOf(designPlanProduct.getUserId()));
			lstParameter.add(qp);
		}
		if (designPlanProduct.getIsDeleted() != null) {
			qp = new QueryParameter();
			qp.setName("isDeleted");
			qp.setValue(String.valueOf(designPlanProduct.getIsDeleted()));
			lstParameter.add(qp);
		}
		if (designPlanProduct.getPlanId() != null) {
			qp = new QueryParameter();
			qp.setName("planId");
			qp.setValue(String.valueOf(designPlanProduct.getPlanId()));
			lstParameter.add(qp);
		}
		if (designPlanProduct.getProductGroupId() != null) {
			qp = new QueryParameter();
			qp.setName("productGroupId");
			qp.setValue(String.valueOf(designPlanProduct.getProductGroupId()));
			lstParameter.add(qp);
		}
		if (designPlanProduct.getPlanGroupId() != null) {
			qp = new QueryParameter();
			qp.setName("planGroupId");
			qp.setValue(String.valueOf(designPlanProduct.getPlanGroupId()));
			lstParameter.add(qp);
		}
		if (designPlanProduct.getIsMainProduct() != null) {
			qp = new QueryParameter();
			qp.setName("isMainProduct");
			qp.setValue(String.valueOf(designPlanProduct.getIsMainProduct()));
			lstParameter.add(qp);
		}
		if (designPlanProduct.getInitProductId() != null) {
			qp = new QueryParameter();
			qp.setName("initProductId");
			qp.setValue(String.valueOf(designPlanProduct.getInitProductId()));
			lstParameter.add(qp);
		}

		parameter.setLstParameter(lstParameter);
		return parameter;
	}

	/***
	 * 获取单个设计方案产品库详情
	 * 
	 * @param id
	 * @return
	 */
	public static DesignPlanProduct get(int id) {
		DesignPlanProduct product = null;
		String key = KeyGenerator.getIdKey(ModuleType.DesignPlanProduct, id);
		product = (DesignPlanProduct) CacheManager.getInstance().getCacher().getObject(key);
		if (product == null) {
			product = designPlanProductService.get(id);
			if (product != null) {
				CacheManager.getInstance().getCacher().setObject(key, product, 0);
			}
		}
		return product;
	}

	/***
	 * 用户产品配置
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<UserProductConfig> getAllList(UserProductConfig userProductConfig) {
		Map map = new HashMap();
		String userId = userProductConfig.getUserId();
		map.put("userId", userId);
		List<UserProductConfig> listUserPC = Lists.newArrayList();
		String key = KeyGenerator.getKeyWithMap(ModuleType.UserProductConfig, map);
		if (CacheManager.getInstance().getCacher() != null) {
			listUserPC = CacheManager.getInstance().getCacher().getList(UserProductConfig.class, key);
			if (CustomerListUtils.isEmpty(listUserPC)) {
				listUserPC = userProductConfigService.getUserConfigList(userProductConfig);
				if (!CustomerListUtils.isEmpty(listUserPC)) {
					CacheManager.getInstance().getCacher().setObject(key, listUserPC, 0);
				}
			}
		}
		//////System.out.println("********WebDesignPlanProductController-costListWeb-getAllList-key" + key + "********");
		return listUserPC;
	}

	/***
	 * 获得价格、花费 总条数
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int costListCount(DesignPlanProduct designPlanProduct) {

		Map map = new HashMap();

		/**Integer userId = designPlanProduct.getUserId();
		String brands = designPlanProduct.getBrands();
		String bigType = designPlanProduct.getBigType();
		String smallType = designPlanProduct.getSmallType();

		map.put("userId", userId);
		map.put("brands", brands);
		map.put("bigType", bigType);
		map.put("smallType", smallType);*/

		Integer planId=designPlanProduct.getPlanId();
		String brands=designPlanProduct.getBrands();
		String bigType=designPlanProduct.getBigType();
		String smallType=designPlanProduct.getSmallType();
		
		map.put("planId", planId);
		map.put("brands", brands);
		map.put("bigType", bigType);
		map.put("smallType", smallType);
		map.put("Count", "Count");
		
		int Count = 0;
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignPlanProductCost, map);
		if (CacheManager.getInstance().getCacher() != null) {
			String CountString = CacheManager.getInstance().getCacher().get(key);
			if ((!"".equals(CountString)) && (null != CountString)) {
				Count = Integer.parseInt(CountString);
			} else {
				Count = designPlanProductService.costListCount(designPlanProduct);
				if (Count > 0) {
					CacheManager.getInstance().getCacher().set(key, String.valueOf(Count), 0);
				}
			}
		}
		//////System.out.println("********WebDesignPlanProductController-costListWeb-costListCount-key" + key + "********");
		return Count;
	}

	/***
	 * 得到结算汇总清单
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<ProductsCost> costList(DesignPlanProduct designPlanProduct) {

		Map map = new HashMap();

		/**Integer userId = designPlanProduct.getUserId();
		String brands = designPlanProduct.getBrands();
		String bigType = designPlanProduct.getBigType();
		String smallType = designPlanProduct.getSmallType();*/

		/**map.put("userId", userId);
		map.put("brands", brands);
		map.put("bigType", bigType);
		map.put("smallType", smallType);*/

		
		Integer planId=designPlanProduct.getPlanId();
		String brands=designPlanProduct.getBrands();
		String bigType=designPlanProduct.getBigType();
		String smallType=designPlanProduct.getSmallType();
		
		map.put("planId", planId);
		map.put("brands", brands);
		map.put("bigType", bigType);
		map.put("smallType", smallType);
		
		
		List<ProductsCost> list = new ArrayList<ProductsCost>();
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignPlanProduct, map);
		if (CacheManager.getInstance().getCacher() != null) {
			list = CacheManager.getInstance().getCacher().getList(ProductsCost.class, key);
			if (CustomerListUtils.isEmpty(list)) {
//				list = designPlanProductService.costList(designPlanProduct);
				if (!CustomerListUtils.isEmpty(list)) {
					CacheManager.getInstance().getCacher().setObject(key, list, 0);
				}
			}
		}
		//////System.out.println("********WebDesignPlanProductController-costListWeb-costList-key" + key + "********");
		return list;
	}

	/***
	 * 得到结算汇总清单详情
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })

	public static List<ProductCostDetail> costDetail(ProductsCost productsCost) {
		Map map = new HashMap();

		Integer planId = productsCost.getPlanId();
		Integer userId = productsCost.getUserId();
		String brands = productsCost.getBrands();
		String bigType = productsCost.getBigType();
		String smallType = productsCost.getSmallType();

		map.put("planId", planId);
		map.put("userId", userId);
		map.put("brands", brands);
		map.put("bigType", bigType);
		map.put("smallType", smallType);

		List<ProductCostDetail> list = new ArrayList<ProductCostDetail>();
		String key = KeyGenerator.getKeyWithMap(ModuleType.ProductsCost, map);
		if (CacheManager.getInstance().getCacher() != null) {
			list = CacheManager.getInstance().getCacher().getList(ProductCostDetail.class, key);
			if (CustomerListUtils.isEmpty(list)) {
				list = designPlanProductService.costDetail(productsCost);
				if (!CustomerListUtils.isEmpty(list)) {
					CacheManager.getInstance().getCacher().setObject(key, list, 0);
				}
			}
		}
		return list;
	}

	/***
	 * 获取设计方案成品库总记录数
	 * 
	 * @param spaceCommon
	 * @return
	 */
	public static int getPlanProductCount(DesignPlanProduct designPlanProduct) {
		int total = 0;
		PageParameter parameter = getPageParameter(designPlanProduct);
		String key = KeyGenerator.getTotalWithParameter(ModuleType.DesignPlanProduct, parameter);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isBlank(temp)) {
			total = designPlanProductService.planProductCount(designPlanProduct);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else {
			total = Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:" + key);
		}
		return total;
	}

	/***
	 * 获取设计方案成品库列表
	 * 
	 * @param plan
	 * @return
	 */
	public static List<DesignPlanProductResult> getPlanProductList(DesignPlanProduct designPlanProduct) {
		List<DesignPlanProductResult> lstPlanProduct = Lists.newArrayList();
		PageParameter parameter = getPageParameter(designPlanProduct);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.DesignPlanProduct, parameter);
		lstPlanProduct = CacheManager.getInstance().getCacher().getList(DesignPlanProductResult.class, key);
		if (CustomerListUtils.isEmpty(lstPlanProduct)) {
			lstPlanProduct = designPlanProductService.planProductList(designPlanProduct);
			CacheManager.getInstance().getCacher().setObject(key, lstPlanProduct, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlanProduct;
	}

	/***
	 * 获取设计方案成品库列表
	 * 
	 * @param plan
	 * @return
	 */
	public static List<DesignPlanProduct> getAllList(DesignPlanProduct designPlanProduct) {
		List<DesignPlanProduct> lstPlanProduct = Lists.newArrayList();
		PageParameter parameter = getPageParameter(designPlanProduct);
		String key = KeyGenerator.getAllListKeyWithParameter(ModuleType.DesignPlanProduct, parameter);
		lstPlanProduct = CacheManager.getInstance().getCacher().getList(DesignPlanProduct.class, key);
		if (CustomerListUtils.isEmpty(lstPlanProduct)) {
			lstPlanProduct = designPlanProductService.getList(designPlanProduct);
			if (CustomerListUtils.isNotEmpty(lstPlanProduct)){
				CacheManager.getInstance().getCacher().setObject(key, lstPlanProduct, 0);
				//////System.out.println("count:"+String.valueOf(lstPlanProduct.size()));
				//////System.out.println("get from db,key:" + key);
			}
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlanProduct;
	}
	/***
	 * 获取设计方案成品库列表
	 * 
	 * @param plan
	 * @return
	 */
	public static List<DesignPlanProduct> getAllList2(DesignPlanProduct designPlanProduct) {
		List<DesignPlanProduct> lstPlanProduct = Lists.newArrayList();
		PageParameter parameter = getPageParameter(designPlanProduct);
		String key = KeyGenerator.getAllListKeyWithParameter(ModuleType.DesignPlanProduct, parameter);
		lstPlanProduct = CacheManager.getInstance().getCacher().getList(DesignPlanProduct.class, key);
		if (CustomerListUtils.isEmpty(lstPlanProduct)) {
			lstPlanProduct = optimizePlanService.getList(designPlanProduct);
			if (CustomerListUtils.isNotEmpty(lstPlanProduct)){
				CacheManager.getInstance().getCacher().setObject(key, lstPlanProduct, 0);
				//////System.out.println("count:"+String.valueOf(lstPlanProduct.size()));
				//////System.out.println("get from db,key:" + key);
			}
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlanProduct;
	}

	/***
	 * 移除单个设计方案使用产品的缓存
	 * 
	 * @param id
	 */
	public static void remove(int id) {
		String key = KeyGenerator.getIdKey(ModuleType.DesignPlanProduct, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.DesignPlanProduct,key);
		
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
		// CacheManager.getInstance().getCacher().remove(key);
		if (CacheManager.getInstance().getCacher() != null) {
			String totalString = CacheManager.getInstance().getCacher().get(key);
			if (totalString != null && !"".equals(totalString)) {
				total = Integer.parseInt(totalString);
			} else {
				total = designPlanProductService.getCount(designPlanProductSearch);
				if (total > 0) {
					CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
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
		// CacheManager.getInstance().getCacher().remove(key);
		if (CacheManager.getInstance().getCacher() != null) {
			list = CacheManager.getInstance().getCacher().getList(DesignPlanProduct.class, key);
			if (CustomerListUtils.isEmpty(list)) {
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

		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignPlanProduct, map);
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

	/***
	 * 材质配置文件路径
	 * 
	 * @param materialConfigId
	 * @author Administrator
	 * @return ResFile
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResFile getResFile(Integer materialConfigId) {
		ResFile resFile = new ResFile();
		Map map = new HashMap();
		map.put("materialConfigId", materialConfigId);

		String key = KeyGenerator.getKeyWithMap(ModuleType.BaseProduct, map);
		if (CacheManager.getInstance().getCacher() != null) {
			resFile = (ResFile) CacheManager.getInstance().getCacher().getObject(key);
			if (resFile == null) {
				resFile = resFileService.get(materialConfigId);
				if (resFile != null) {
					CacheManager.getInstance().getCacher().setObject(key, resFile, 0);
				}
			}
		}
		//////System.out.println("********WebDesignPlanProductController-deletedList-getResFile-key：" + key + "********");
		return resFile;
	}

	/***
	 * 
	 * @param u3dModelId
	 * @author Administrator
	 * @return u3dModelId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResModel getResModel(Integer u3dModelId) {

		ResModel resModel = new ResModel();
		Map map = new HashMap();
		map.put("u3dModelId", u3dModelId);

		String key = KeyGenerator.getKeyWithMap(ModuleType.ResModel, map);
		if (CacheManager.getInstance().getCacher() != null) {
			resModel = (ResModel) CacheManager.getInstance().getCacher().getObject(key);
			if (resModel == null) {
				resModel = resModelService.get(u3dModelId);
				if (resModel != null) {
					CacheManager.getInstance().getCacher().setObject(key, resModel, 0);
				}
			}
		}
		//////System.out.println("********WebDesignPlanProductController-deletedList-getResModel-key：" + key + "********");
		return resModel;
	}

	/***
	 * 根据id 获得 品牌信息
	 * 
	 * @param brandId
	 * @author Administrator
	 * @return u3dModelId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BaseBrand getBaseBrand(Integer brandId) {
		BaseBrand baseBrand = new BaseBrand();

		Map map = new HashMap();
		map.put("brandId", brandId);

		String key = KeyGenerator.getKeyWithMap(ModuleType.BaseProduct, map);
		if (CacheManager.getInstance().getCacher() != null) {
			baseBrand = (BaseBrand) CacheManager.getInstance().getCacher().getObject(key);
			if (baseBrand == null) {
				baseBrand = baseBrandService.get(brandId);
				if (baseBrand != null) {
					CacheManager.getInstance().getCacher().setObject(key, baseBrand, 0);
				}
			}
		}
		//////System.out.println("********WebDesignPlanProductController-deletedList-getBaseBrand-key：" + key + "********");
		return baseBrand;
	}

	/***
	 * 根据id 获得 产品设计模板
	 * 
	 * @param planProductId
	 * @author Administrator
	 * @return DesignTempletProduct
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DesignTempletProduct getDesignTempletProductService(Integer planProductId) {
		DesignTempletProduct designTempletProduct = new DesignTempletProduct();

		Map map = new HashMap();
		map.put("planProductId", planProductId);

		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignPlanProduct, map);
		if (CacheManager.getInstance().getCacher() != null) {
			designTempletProduct = (DesignTempletProduct) CacheManager.getInstance().getCacher().getObject(key);
			if (designTempletProduct == null) {
				designTempletProduct = designTempletProductService.get(planProductId);
				if (designTempletProduct != null) {
					CacheManager.getInstance().getCacher().setObject(key, designTempletProduct, 0);
				}
			}
		}
		//////System.out.println("********WebDesignPlanProductController-deletedList-getDesignTempletProductService-key：" + key + "********");
		return designTempletProduct;
	}

	/***
	 * 获取设计方案成品库列表
	 * 
	 * @param plan
	 * @return
	 */
	public static List<DesignPlanProductResult> getPlanProductListV2(DesignPlanProduct designPlanProduct) {
		List<DesignPlanProductResult> lstPlanProduct = Lists.newArrayList();
		PageParameter parameter = getPageParameter(designPlanProduct);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.DesignPlanProduct, parameter);
		lstPlanProduct = CacheManager.getInstance().getCacher().getList(DesignPlanProductResult.class, key);
		if (CustomerListUtils.isEmpty(lstPlanProduct)) {
			lstPlanProduct = designPlanProductService.planProductListV2(designPlanProduct);
			CacheManager.getInstance().getCacher().setObject(key, lstPlanProduct, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlanProduct;
	}

	 
}
