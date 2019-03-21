package com.nork.product.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.StringUtils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.model.DesignPlan;
import com.nork.product.model.BrandIndustryModel;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProductCategoryRel;
import com.nork.product.model.ProductUsageCount;
import com.nork.product.service.ProductCategoryRelService;
import com.nork.product.service.ProductUsageCountService;

/***
 * 产品与目录关联关系缓存层
 * @author qiu.jun
 * @date 2016-05-19
 * 
 */
public class ProductCategoryRelCacher {
     private static ProductCategoryRelService productCategoryRelService=SpringContextHolder.getBean(ProductCategoryRelService.class);
     private static ProductUsageCountService productUsageCountService = SpringContextHolder.getBean(ProductUsageCountService.class);
     private static Map<String, String> getMap(final ProductCategoryRel rel){
    	Map<String,String> map=new HashMap<String,String>();
  		map.put("categoryCode", rel.getCategoryCode());
  		map.put("productCode", rel.getProductCode());
  		map.put("productModelNumber", rel.getProductModelNumber());
  		map.put("houseTypeValues", rel.getHouseTypeValues());
  		if(rel.getHouseTypeList()!=null&&rel.getHouseTypeList().size()>0){
  			map.put("houseTypeList", rel.getHouseTypeList().toString());
  		}
  		if(rel.getUserId()!=null && rel.getUserId()!=-1){
  			map.put("userId", String.valueOf(rel.getUserId()));
  		}
  		if(rel.getSpaceCommonId()!=null){
  			map.put("spaceCommonId", String.valueOf(rel.getSpaceCommonId()));
  		}
  		if(CustomerListUtils.isNotEmpty(rel.getTemplateProductId())){
  			map.put("templateProductId", CustomerListUtils.getString(rel.getTemplateProductId()));
  		}
  		if(rel.getDesignTempletId()!=null){
  			map.put("designTempletId", String.valueOf(rel.getDesignTempletId()));
  		}
  		if(rel.getDesignProductId()!=null){
  			map.put("designProductId", String.valueOf(rel.getDesignProductId()));
  		}
  		if(rel.getProductId()!=null){
  			map.put("productId", String.valueOf(rel.getProductId()));
  		}
  		//产品使用量缓存
  		if(rel.getProductId()!=null && rel.getUserId()!=null && rel.getUserId()!=-1){
  			ProductUsageCount  productUsageCount = new ProductUsageCount();
  			productUsageCount.setUserId(rel.getUserId());
  			productUsageCount.setProductId(rel.getProductId());
  			productUsageCount.setIsDeleted(0);
  			List<ProductUsageCount> list = productUsageCountService.getList(productUsageCount);
  			if( list != null && list.size()>0 ){
  				map.put("productCount", String.valueOf(list.get(0).getCount()));
  			}
  		}
  		if(CustomerListUtils.isNotEmpty(rel.getBrandIds())){
  			map.put("brandIds", CustomerListUtils.getString(rel.getBrandIds()));
  		}
  		if( rel.getCategoryIdList() != null && rel.getCategoryIdList().size() > 0 ){
  			String str = "";
  			for(String category : rel.getCategoryIdList()){
  				str += category+",";
  			}
  			map.put("categoryIdList", str);
  		}
  		if( rel.getAttributeConditionList() != null && rel.getAttributeConditionList().size() > 0 ){
  			String str = "";
  			for(String condition : rel.getAttributeConditionList()){
  				str += condition+",";
  			}
  			map.put("attributeConditionList", str);
  		}
  		map.put("productTypeValue", rel.getProductTypeValue());
  		if(rel.getProductSmallTypeValue()!=null){
  		   map.put("productSmallTypeValue", String.valueOf(rel.getProductSmallTypeValue()));
  		}
  		map.put("specialProductType", rel.getSpecialProductType());
  		map.put("onlyShowRecommend", String.valueOf(rel.getOnlyShowRecommend()));
  		map.put("showBgWall", String.valueOf(rel.getShowBgWall()));
  		map.put("exceptRecommend", String.valueOf(rel.getExceptRecommend()));
  		map.put("startLength", String.valueOf(rel.getStartLength()));
  		map.put("endLength", String.valueOf(rel.getEndLength()));
  		map.put("bgWallHeight", String.valueOf(rel.getBgWallHeight()));
  		map.put("productLength", String.valueOf(rel.getProductLength()));
  		map.put("productHeight", String.valueOf(rel.getProductHeight()));
  		map.put("start", String.valueOf(rel.getStart()));
  		map.put("limit", String.valueOf(rel.getLimit()));
  		return map;
     }
     
     private static Map<String, String> getRecommendMap(final ProductCategoryRel rel){
    	Map<String,String> map = new HashMap<String,String>();
  		map.put("categoryCode", rel.getCategoryCode());
  		map.put("productModelNumber", rel.getProductModelNumber());
  		map.put("houseTypeValues", rel.getHouseTypeValues());
  		if(rel.getUserId()!=null && rel.getUserId()!=-1){
  			map.put("userId", String.valueOf(rel.getUserId()));
  		}
  		if(rel.getSpaceCommonId()!=null){
  			map.put("spaceCommonId", String.valueOf(rel.getSpaceCommonId()));
  		}
//  		if(CustomerListUtils.isNotEmpty(rel.getTemplateProductId())){
//  			map.put("templateProductId", CustomerListUtils.getString(rel.getTemplateProductId()));
//  		}
  		if(rel.getDesignTempletId()!=null){
  			map.put("designTempletId", String.valueOf(rel.getDesignTempletId()));
  		}
  		if(rel.getDesignProductId()!=null){
  			map.put("designProductId", String.valueOf(rel.getDesignProductId()));
  		}
  		if(rel.getProductId()!=null){
  			map.put("productId", String.valueOf(rel.getProductId()));
  		}
  		//产品使用量缓存
  		if(rel.getProductId()!=null && rel.getUserId()!=null && rel.getUserId()!=-1){
  			ProductUsageCount  productUsageCount = new ProductUsageCount();
  			productUsageCount.setUserId(rel.getUserId());
  			productUsageCount.setProductId(rel.getProductId());
  			productUsageCount.setIsDeleted(0);
  			List<ProductUsageCount> list = productUsageCountService.getList(productUsageCount);
  			if( list != null && list.size()>0 ){
  				map.put("productCount", String.valueOf(list.get(0).getCount()));
  			}
  		}
  		if(CustomerListUtils.isNotEmpty(rel.getBrandIds())){
  			map.put("brandIds", CustomerListUtils.getString(rel.getBrandIds()));
  		}
  		if( rel.getAttributeConditionList() != null && rel.getAttributeConditionList().size() > 0 ){
  			String str = "";
  			for(String condition : rel.getAttributeConditionList()){
  				str += condition+",";
  			}
  			map.put("attributeConditionList", str);
  		}
  		map.put("productTypeValue", rel.getProductTypeValue());
  		if(rel.getProductSmallTypeValue()!=null){
  		   map.put("productSmallTypeValue", String.valueOf(rel.getProductSmallTypeValue()));
  		}
  		map.put("specialProductType", rel.getSpecialProductType());
  		map.put("onlyShowRecommend", String.valueOf(rel.getOnlyShowRecommend()));
  		map.put("exceptRecommend", String.valueOf(rel.getExceptRecommend()));
  		map.put("productLength", String.valueOf(rel.getProductLength()));
  		map.put("productHeight", String.valueOf(rel.getProductHeight()));
  		map.put("start", String.valueOf(rel.getStart()));
  		map.put("limit", String.valueOf(rel.getLimit()));
  		return map;
     }
     
     private static Map<String, String> getBuildingHomeMap(final ProductCategoryRel rel){
    	Map<String,String> map=new HashMap<String,String>();
  		map.put("categoryCode", rel.getCategoryCode());
  		map.put("productCode", rel.getProductCode());
  		if(rel.getUserId()!=null && rel.getUserId()!=-1){
  			map.put("userId", String.valueOf(rel.getUserId()));
  		}
  		if(CustomerListUtils.isNotEmpty(rel.getBrandIds())){
  			map.put("brandIds", CustomerListUtils.getString(rel.getBrandIds()));
  		}
  		if( rel.getCategoryIdList() != null && rel.getCategoryIdList().size() > 0 ){
  			String str = "";
  			for(String category : rel.getCategoryIdList()){
  				str += category+",";
  			}
  			map.put("categoryIdList", str);
  		}
  		if (rel.getBrandIndustryModelList() != null && rel.getBrandIndustryModelList().size() > 0) {
			 StringBuffer sb = new StringBuffer("");
			 for (BrandIndustryModel brandIndustryModel : rel.getBrandIndustryModelList()) {
				 sb.append(brandIndustryModel.getBrandId()+"_");
				 sb.append(brandIndustryModel.getIndustryValue()+"_");
				 sb.append(brandIndustryModel.getSmallTypeValue()+"_");
				 sb.append(brandIndustryModel.getStatusShowWu()+"_");
			 }
			 map.put("brandIndustryModel", sb.toString());
		 }
  		map.put("start", String.valueOf(rel.getStart()));
  		map.put("limit", String.valueOf(rel.getLimit()));
  		return map;
     }
     private static Map<String, String> getCustomizedMap(final ProductCategoryRel rel){
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("categoryCode", rel.getCategoryCode());
   		map.put("productModelNumber", rel.getProductModelNumber());
   		map.put("houseTypeValues", rel.getHouseTypeValues());
   		if(rel.getUserId()!=null && rel.getUserId()!=-1){
   			map.put("userId", String.valueOf(rel.getUserId()));
   		}
   		if(rel.getSpaceCommonId()!=null){
   			map.put("spaceCommonId", String.valueOf(rel.getSpaceCommonId()));
   		}
   		if(CustomerListUtils.isNotEmpty(rel.getTemplateProductId())){
   			map.put("templateProductId", CustomerListUtils.getString(rel.getTemplateProductId()));
   		}
   		if(rel.getDesignTempletId()!=null){
   			map.put("designTempletId", String.valueOf(rel.getDesignTempletId()));
   		}
   		if(rel.getDesignProductId()!=null){
   			map.put("designProductId", String.valueOf(rel.getDesignProductId()));
   		}
   		if(rel.getProductId()!=null){
   			map.put("productId", String.valueOf(rel.getProductId()));
   		}
   		//产品使用量缓存
   		if(rel.getProductId()!=null && rel.getUserId()!=null && rel.getUserId()!=-1){
   			ProductUsageCount  productUsageCount = new ProductUsageCount();
   			productUsageCount.setUserId(rel.getUserId());
   			productUsageCount.setProductId(rel.getProductId());
   			productUsageCount.setIsDeleted(0);
   			List<ProductUsageCount> list = productUsageCountService.getList(productUsageCount);
   			if( list != null && list.size()>0 ){
   				map.put("productCount", String.valueOf(list.get(0).getCount()));
   			}
   		}
   		if(CustomerListUtils.isNotEmpty(rel.getBrandIds())){
   			map.put("brandIds", CustomerListUtils.getString(rel.getBrandIds()));
   		}
   		if( rel.getAttributeConditionList() != null && rel.getAttributeConditionList().size() > 0 ){
   			String str = "";
   			for(String condition : rel.getAttributeConditionList()){
   				str += condition+",";
   			}
   			map.put("attributeConditionList", str);
   		}
   		map.put("productTypeValue", rel.getProductTypeValue());
   		if(rel.getProductSmallTypeValue()!=null){
   		   map.put("productSmallTypeValue", String.valueOf(rel.getProductSmallTypeValue()));
   		}
   		map.put("specialProductType", rel.getSpecialProductType());
   		map.put("onlyShowRecommend", String.valueOf(rel.getOnlyShowRecommend()));
   		map.put("showBgWall", String.valueOf(rel.getShowBgWall()));
   		map.put("exceptRecommend", String.valueOf(rel.getExceptRecommend()));
   		map.put("startLength", String.valueOf(rel.getStartLength()));
   		map.put("endLength", String.valueOf(rel.getEndLength()));
   		map.put("bgWallHeight", String.valueOf(rel.getBgWallHeight()));
   		map.put("productLength", String.valueOf(rel.getProductLength()));
   		map.put("productHeight", String.valueOf(rel.getProductHeight()));
   		map.put("start", String.valueOf(rel.getStart()));
   		map.put("limit", String.valueOf(rel.getLimit()));
  		return map;
     }
 	/***
 	 * 根据分类code和商品名称获取总记录数
 	 * 
 	 * @param spaceCommon
 	 * @return
 	 */
 	public static int getCountByCategoryCode(ProductCategoryRel rel) {
 		int total = 0;
 		Map<String, String> map=getMap(rel);
 		map.put("func", "bycategorycode");
 		String key = KeyGenerator.getAllCountKeyWithMap(ModuleType.ProductCategoryRel, map);
 		String temp = CacheManager.getInstance().getCacher().get(key);
 		if (StringUtils.isBlank(temp)) {
 			total = productCategoryRelService.findProductByCategoryCodeCount(rel);
 			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
 		} else {
 			total = Integer.parseInt(temp);
 			//////System.out.println("get from cacher,key:" + key);
 		}
 		//////System.out.println("total:"+total+" key:"+key+"**********************************");
 		return total;
 	}
 	/***
 	 * 建材家居搜索产品记录总数
 	 * @param ProductCategoryRel rel
 	 * @return
 	 */
 	public static int findBuildingHomeProductCount(ProductCategoryRel rel) {
 		int total = 0;
 		Map<String, String> map = getBuildingHomeMap(rel);
 		map.put("func", "bycategorycode");
 		if(rel.getBaseProduct()!=null){
 			map.put("baseProduct", rel.getBaseProduct().toString());
 		}
 		String key = KeyGenerator.getAllCountKeyWithMap(ModuleType.ProductCategoryRel, map);
 		String temp = CacheManager.getInstance().getCacher().get(key);
 		if (StringUtils.isBlank(temp)) {
 			total = productCategoryRelService.findBuildingHomeProductCount(rel);
 			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
 			//////System.out.println("total:"+total+" key:"+key+"-------------------------");
 		} else {
 			total = Integer.parseInt(temp);
 			//////System.out.println("get from cacher,key:" + key);
 		}
 		//////System.out.println("total:"+total+" key:"+key+"**********************************");
 		return total;
 	}
 	
 	/***
	 *建材家居搜索产品列表
	 * @param rel
	 * @return
	 */
	public static List<CategoryProductResult> findBuildingHomeProductResult(ProductCategoryRel rel) {
		List<CategoryProductResult> lstPlanProduct = Lists.newArrayList();
		Map<String, String> map = getBuildingHomeMap(rel);
		map.put("func", "bycategorycode");
 		if(rel.getBaseProduct()!=null){
 			map.put("baseProduct", rel.getBaseProduct().toString());
 		}
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.ProductCategoryRel, map);
		lstPlanProduct = CacheManager.getInstance().getCacher().getList(CategoryProductResult.class, key);
		if (CustomerListUtils.isEmpty(lstPlanProduct)) {
			lstPlanProduct = productCategoryRelService.findBuildingHomeProductResult(rel);
			CacheManager.getInstance().getCacher().setObject(key, lstPlanProduct, 0);
			//////System.out.println("size:"+lstPlanProduct.size()+" key:"+key+"---getListRecommendCategoryCode---");
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlanProduct;
	}
	
	/***
 	 * 分类搜索推荐分类产品记录总数
 	 * @param ProductCategoryRel rel
 	 * @return
 	 */
 	public static int getRecommendCategoryCount(ProductCategoryRel rel) {
 		int total = 0;
 		Map<String, String> map = getRecommendMap(rel);
 		map.put("func", "bycategorycode");
 		if(rel.getBaseProduct()!=null){
 			map.put("baseProduct", rel.getBaseProduct().toString());
 		}
 		String key = KeyGenerator.getAllCountKeyWithMap(ModuleType.ProductCategoryRel, map);
 		String temp = CacheManager.getInstance().getCacher().get(key);
 		if (StringUtils.isBlank(temp)) {
 			total = productCategoryRelService.findRecommendCategoryProductResultCount(rel);
 			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
 			//////System.out.println("total:"+total+" key:"+key+"-------------------------");
 		} else {
 			total = Integer.parseInt(temp);
 			//////System.out.println("get from cacher,key:" + key);
 		}
 		//////System.out.println("total:"+total+" key:"+key+"**********************************");
 		return total;
 	}
 	
 	/***
	 *分类搜索推荐分类产品列表
	 * @param rel
	 * @return
	 */
	public static List<CategoryProductResult> getListRecommendCategoryCode(ProductCategoryRel rel) {
		List<CategoryProductResult> lstPlanProduct = Lists.newArrayList();
		Map<String, String> map = getRecommendMap(rel);
		map.put("func", "bycategorycode");
 		if(rel.getBaseProduct()!=null){
 			map.put("baseProduct", rel.getBaseProduct().toString());
 		}
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.ProductCategoryRel, map);
		lstPlanProduct = CacheManager.getInstance().getCacher().getList(CategoryProductResult.class, key);
		if (CustomerListUtils.isEmpty(lstPlanProduct)) {
			lstPlanProduct = productCategoryRelService.findRecommendCategoryProductResultV2(rel);
			CacheManager.getInstance().getCacher().setObject(key, lstPlanProduct, 0);
			//////System.out.println("size:"+lstPlanProduct.size()+" key:"+key+"---getListRecommendCategoryCode---");
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlanProduct;
	}
	
	
	/***
 	 * 分类搜索推荐分类产品记录总数
 	 * @param ProductCategoryRel rel
 	 * @return
 	 */
 	public static int getRecommendCount(ProductCategoryRel rel) {
 		int total = 0;
 		Map<String, String> map = getRecommendMap(rel);
 		map.put("func", "bycategorycode");
 		if(rel.getBaseProduct()!=null){
 			map.put("baseProduct", rel.getBaseProduct().toString());
 		}
 		String key = KeyGenerator.getAllCountKeyWithMap(ModuleType.ProductCategoryRel, map);
 		String temp = CacheManager.getInstance().getCacher().get(key);
 		if (StringUtils.isBlank(temp)) {
 			total = productCategoryRelService.getRecommendResultCount(rel);
 			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
 			//////System.out.println("total:"+total+" key:"+key+"-------------------------");
 		} else {
 			total = Integer.parseInt(temp);
 			//////System.out.println("get from cacher,key:" + key);
 		}
 		//////System.out.println("total:"+total+" key:"+key+"**********************************");
 		return total;
 	}
 	
 	/***
	 *分类搜索推荐产品列表
	 * @param rel
	 * @return
	 */
	public static List<CategoryProductResult> getRecommendList(ProductCategoryRel rel) {
		List<CategoryProductResult> lstPlanProduct = Lists.newArrayList();
		Map<String, String> map = getRecommendMap(rel);
		map.put("func", "bycategorycode");
 		if(rel.getBaseProduct()!=null){
 			map.put("baseProduct", rel.getBaseProduct().toString());
 		}
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.ProductCategoryRel, map);
		lstPlanProduct = CacheManager.getInstance().getCacher().getList(CategoryProductResult.class, key);
		if (CustomerListUtils.isEmpty(lstPlanProduct)) {
			lstPlanProduct = productCategoryRelService.getRecommendResult(rel);
			CacheManager.getInstance().getCacher().setObject(key, lstPlanProduct, 0);
			//////System.out.println("size:"+lstPlanProduct.size()+" key:"+key+"---getListRecommendCategoryCode---");
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlanProduct;
	}
	
 	/***
 	 * 根据分类code和商品名称获取定制总记录数
 	 * 
 	 * @param ProductCategoryRel rel
 	 * @return
 	 */
 	public static int getCustomizedCategoryCount(ProductCategoryRel rel) {
 		int total = 0;
 		Map<String, String> map = getCustomizedMap(rel);
 		map.put("func", "bycategorycode");
 		if(rel.getBaseProduct()!=null){
 			map.put("baseProduct", rel.getBaseProduct().toString());
 		}
 		String key = KeyGenerator.getAllCountKeyWithMap(ModuleType.ProductCategoryRel, map);
 		String temp = CacheManager.getInstance().getCacher().get(key);
 		if (StringUtils.isBlank(temp)) {
 			total = productCategoryRelService.findCustomizedCategoryProductResultCount(rel);
 			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
 		} else {
 			total = Integer.parseInt(temp);
 			//////System.out.println("get from cacher,key:" + key);
 		}
 		//////System.out.println("total:"+total+" key:"+key+"**********************************");
 		return total;
 	}
 	
 	/***
	 *根据分类code和商品名称获取所有记录
	 * @param rel
	 * @return
	 */
	public static List<CategoryProductResult> getListCustomizedCategoryCode(ProductCategoryRel rel) {
		List<CategoryProductResult> lstPlanProduct = Lists.newArrayList();
		Map<String, String> map=getCustomizedMap(rel);
		map.put("func", "bycategorycode");
 		if(rel.getBaseProduct()!=null){
 			map.put("baseProduct", rel.getBaseProduct().toString());
 		}
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.ProductCategoryRel, map);
		lstPlanProduct = CacheManager.getInstance().getCacher().getList(CategoryProductResult.class, key);
		if (CustomerListUtils.isEmpty(lstPlanProduct)) {
			lstPlanProduct = productCategoryRelService.findCustomizedCategoryProductResult(rel);
			CacheManager.getInstance().getCacher().setObject(key, lstPlanProduct, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlanProduct;
	}
	
 	/***
 	 * 根据long code和商品名称获取总记录数
 	 * @param spaceCommon
 	 * @return
 	 */
 	public static int getCountByLongCode(ProductCategoryRel rel) {
 		int total = 0;
 		Map<String, String> map=getMap(rel);
 		map.put("func", "bylongcode");
 		String key = KeyGenerator.getAllCountKeyWithMap(ModuleType.ProductCategoryRel, map);
 		String temp = CacheManager.getInstance().getCacher().get(key);
 		if (StringUtils.isBlank(temp)) {
 			total = productCategoryRelService.findCategoryProductResultByLongCodeCount(rel);
 			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
 		} else {
 			total = Integer.parseInt(temp);
 			//////System.out.println("get from cacher,key:" + key);
 		}
 		return total;
 	}
 	
	/***
	 *根据分类code和商品名称获取所有记录
	 * 
	 * @param rel
	 * @return
	 */
	public static List<CategoryProductResult> getListByCategoryCode(ProductCategoryRel rel) {
		List<CategoryProductResult> lstPlanProduct = Lists.newArrayList();
		Map<String, String> map=getMap(rel);
		map.put("func", "bycategorycode");
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.ProductCategoryRel, map);
		lstPlanProduct = CacheManager.getInstance().getCacher().getList(CategoryProductResult.class, key);
		if (CustomerListUtils.isEmpty(lstPlanProduct)) {
			lstPlanProduct = productCategoryRelService.findProductByCategoryCode(rel);
			CacheManager.getInstance().getCacher().setObject(key, lstPlanProduct, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlanProduct;
	}
	
	/***
	 *根据long code和商品名称获取所有记录
	 * 
	 * @param rel
	 * @return
	 */
	public static List<CategoryProductResult> getListByLongCode(ProductCategoryRel rel) {
		List<CategoryProductResult> lstPlanProduct = Lists.newArrayList();
		Map<String, String> map=getMap(rel);
		map.put("func", "bylongcode");
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.ProductCategoryRel, map);
		lstPlanProduct = CacheManager.getInstance().getCacher().getList(CategoryProductResult.class, key);
		if (CustomerListUtils.isEmpty(lstPlanProduct)) {
			lstPlanProduct = productCategoryRelService.findCategoryProductResultByLongCode(rel);
			CacheManager.getInstance().getCacher().setObject(key, lstPlanProduct, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlanProduct;
	}
	
 	/***
 	 * 根据分类code和商品名称获取推荐及分类总记录数
 	 * 
 	 * @param ProductCategoryRel rel
 	 * @return
 	 */
 	public static int getCategoryProductCount(ProductCategoryRel rel) {
 		Long startTime=System.currentTimeMillis();
 		int total = 0;
 		Map<String, String> map = getMap(rel);
 		map.put("func", "bycategorycode");
 		String key = KeyGenerator.getAllCountKeyWithMap(ModuleType.ProductCategoryRel, map);
 		String temp = CacheManager.getInstance().getCacher().get(key);
 		//////System.out.println("getCategoryProductCount组装KEY消耗时间:"+(System.currentTimeMillis()-startTime));
 		if (StringUtils.isBlank(temp)) {
 			total = productCategoryRelService.getCategoryProductCount(rel);
 			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
 			//////System.out.println("sql消耗时间:"+(System.currentTimeMillis()-startTime));
 		} else {
 			total = Integer.parseInt(temp);
 			//////System.out.println("get from cacher,key:" + key);
 		}
 		
 		//////System.out.println("total:"+total+" key:"+key+"**********************************");
 		return total;
 	}
 	
 	/***
	 *根据分类code和商品名称获取所有记录
	 * 
	 * @param rel
	 * @return
	 */
	public static List<CategoryProductResult> getCategoryProductResult(ProductCategoryRel rel) {
		Long startTime=System.currentTimeMillis();
		List<CategoryProductResult> lstPlanProduct = Lists.newArrayList();
		Map<String, String> map=getMap(rel);
		map.put("func", "bycategorycode");
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.ProductCategoryRel, map);
		lstPlanProduct = CacheManager.getInstance().getCacher().getList(CategoryProductResult.class, key);
		//////System.out.println("getCategoryProductResult组装KEY消耗时间:"+(System.currentTimeMillis()-startTime));
		if (CustomerListUtils.isEmpty(lstPlanProduct)) {
			lstPlanProduct = productCategoryRelService.getCategoryProductResult(rel);
			CacheManager.getInstance().getCacher().setObject(key, lstPlanProduct, 0);
			//////System.out.println("sql消耗时间:"+(System.currentTimeMillis()-startTime));
			//////System.out.println("size:"+lstPlanProduct.size()+" key:"+key+"---getCategoryProductResult---");
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstPlanProduct;
	}
	
	public static ResponseEnvelope<CategoryProductResult> searchProduct(ProductCategoryRel productCategoryRel
			, HttpServletRequest request,String houseType,Integer designPlanId
			,Integer planProductId,Integer spaceCommonId,String templateProductId
			,String productTypeValue,String smallTypeValue,String queryType
			,String productModelNumber,LoginUser loginUser,DesignPlan designPlan) {
		Long startTime=System.currentTimeMillis();
	
		ResponseEnvelope<CategoryProductResult>  result = new  	ResponseEnvelope<CategoryProductResult>();
		Map<String, String> map = new HashMap<String, String>();
		/*
		  loginUser.getId()
		    spaceCommonId=1577     - 空间
		    templateProductId=1785 -样板房的产品id
          productTypeValue=3     -选中产品的大类
			smallTypeValue=8     ---选中产品的小类
			categoryCode=qiangm --选中的节点
			planProductId=19140 -- 选中的产品
			productModelNumber  --模型编码
			houseType=3         -适合什么房间
			start=0 -  分页1
			limit=30 - 分页2
			msgId=10 - 标识*/
			
			
		if(loginUser.getId()!=null){
		   map.put("userId", String.valueOf(loginUser.getId()));
		}
		if(spaceCommonId!=null){
		  map.put("spaceCommonId", String.valueOf(spaceCommonId));
		}
		if(templateProductId!=null){
		   map.put("templateProductId",  String.valueOf(templateProductId));
		}
		if(StringUtils.isNotBlank(productTypeValue)){
		   map.put("productTypeValue", productTypeValue);
		}
		if(StringUtils.isNotBlank(smallTypeValue)){
			  map.put("smallTypeValue", smallTypeValue);
		}
		if(productCategoryRel!=null && StringUtils.isNotBlank(productCategoryRel.getCategoryCode())){
			  map.put("categoryCode", productCategoryRel.getCategoryCode());
		}
		if(planProductId != null){
			  map.put("planProductId", String.valueOf(planProductId));
		}
		if(productCategoryRel!= null && productCategoryRel.getStart() != null ){
			  map.put("start", String.valueOf(productCategoryRel.getStart()));
		}
		if(productCategoryRel!= null && productCategoryRel.getLimit() != null ){
			  map.put("limit", String.valueOf(productCategoryRel.getLimit()));
		}
		if(productCategoryRel!= null && StringUtils.isNotBlank(productCategoryRel.getMsgId())){
			  map.put("msgId", productCategoryRel.getMsgId());
		}
		if(productModelNumber!= null && StringUtils.isNotBlank(productModelNumber)){
			  map.put("productModelNumber", productModelNumber);
		}
		if(productCategoryRel!= null && productCategoryRel.getBaseProduct() != null){
			  map.put("baseProduct", productCategoryRel.getBaseProduct().toString());
		}

/*		List<CategoryProductResult> list = new ArrayList<CategoryProductResult>();
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.ProCategory, map);
		if (CacheManager.getInstance().getCacher() != null) {
			ListTranscoder<CategoryProductResult> listTranscoder = new ListTranscoder<CategoryProductResult>();
			list = listTranscoder.deserialize(CacheManager.getInstance().getCacher().getBytes(key));
			if (CustomerListUtils.isEmpty(list)) {
				result =  productCategoryRelService.searchProduct(productCategoryRel, request, houseType, designPlanId
						, planProductId, spaceCommonId
						, templateProductId, productTypeValue
						, smallTypeValue, queryType, productModelNumber, loginUser, designPlan);
				if (!CustomerListUtils.isEmpty(list)) {
					//CacheManager.getInstance().getCacher().setObject(key, listTranscoder.serialize(list), 10);
					CacheManager.getInstance().getCacher().setByte(key, listTranscoder.serialize(list), 10);
				}
			}

		}
	
		return result;
		*/
				
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.ProCategory, map);	
		result = (ResponseEnvelope<CategoryProductResult>)CacheManager.getInstance().getCacher().getObject(key);
		if (result==null) {
			result =productCategoryRelService.searchProduct(productCategoryRel, request, houseType, designPlanId
					, planProductId, spaceCommonId
					, templateProductId, productTypeValue
					, smallTypeValue, queryType, productModelNumber, loginUser, designPlan);
			CacheManager.getInstance().getCacher().setObject(key, result, 0);
		}
		return result;
	}
	/***
	 * 移除缓存
	 * 
	 * @param id
	 */
	public static void remove(int id) {
		String key = KeyGenerator.getIdKey(ModuleType.ProductCategoryRel, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.ProductCategoryRel, key);
	}
	
}
