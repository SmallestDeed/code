package com.nork.product.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.product.model.BrandIndustryModel;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.metadata.PageParameter;
import com.nork.common.metadata.QueryParameter;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.ProductAttribute;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.ProductAttributeService;

/***
 * 基本产品缓存层
 * 
 * @author Administrator
 *
 */
public class BaseProductCacher {
	private static BaseProductService baseProductService = SpringContextHolder.getBean(BaseProductService.class);
	private static ProductAttributeService productAttributeService = SpringContextHolder
			.getBean(ProductAttributeService.class);

    private static PageParameter getPageParameter(BaseProduct product){
		PageParameter parameter=new PageParameter();
		List<QueryParameter> lstParameter=Lists.newArrayList();
		QueryParameter qp=null;
		parameter.setPageIndex(product.getStart());
		parameter.setPageSize(product.getLimit());
		if(StringUtils.isNotEmpty(product.getIsInternalUser())){
			parameter.setIsInternalUser(product.getIsInternalUser());
		}
		if(StringUtils.isNoneBlank(product.getProductName())){
			qp=new QueryParameter();
			qp.setName("productName");
			qp.setValue(product.getProductName());
			lstParameter.add(qp);
		}

		qp=new QueryParameter();
		qp.setName("brandId");
		qp.setValue(String.valueOf(product.getBrandId()));
		lstParameter.add(qp);

		if (product.getBrandIndustryModelList() != null && product.getBrandIndustryModelList().size() > 0) {
			StringBuffer sb = new StringBuffer("");
			for (BrandIndustryModel brandIndustryModel : product.getBrandIndustryModelList()) {
				sb.append(brandIndustryModel.getBrandId()+"_");
				sb.append(brandIndustryModel.getIndustryValue()+"_");
				sb.append(brandIndustryModel.getSmallTypeValue()+"_");
				sb.append(brandIndustryModel.getStatusShowWu()+"_");
			}
			qp=new QueryParameter();
			qp.setName("brandIndustryModel");
			qp.setValue(sb.toString());
			lstParameter.add(qp);
		}
		parameter.setLstParameter(lstParameter);
		return parameter;
	}
	
	/***
	 * 获取单个产品的信息
	 * 
	 * @param id
	 * @return BaseProduct
	 */
	public static BaseProduct get(int id) {
		BaseProduct pro = null;
		String key = KeyGenerator.getIdKey(ModuleType.Product, id);
		if (CacheManager.getInstance().getCacher() != null) {
			pro = (BaseProduct) CacheManager.getInstance().getCacher().getObject(key);
			if (pro == null) {
				pro = baseProductService.get(id);
				if (pro != null) {
					CacheManager.getInstance().getCacher().setObject(key, pro, 0);
				}
			}
		}
		return pro;
	}
 
 
	/***
	 * 根据品牌ID获取产品总记录数
	 * @param spaceCommon
	 * @return
	 */
	public static int getTotalProductByBrand(BaseProduct product){
		int total=0;
		PageParameter parameter= getPageParameter(product);
		String key=KeyGenerator.getTotalWithParameter(ModuleType.BaseProduct, parameter);
		String temp=CacheManager.getInstance().getCacher().get(key);
		if(StringUtils.isBlank(temp)){
			total=baseProductService.selectProductByNameAndBrandCount(product);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		}
		else{
			total=Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:"+key);
		}
		return total;
	}

	/***
	 * 根据品牌ID获取产品列表
	 * @param plan
	 * @return
	 */
	public static List<CategoryProductResult> getProductListByBrand(BaseProduct product){
		List<CategoryProductResult> lstCategoryProduct=Lists.newArrayList();
		PageParameter parameter= getPageParameter(product);
		String key=KeyGenerator.getPageQueryKeyParameter(ModuleType.BaseProduct, parameter);
		lstCategoryProduct=CacheManager.getInstance().getCacher().getList(CategoryProductResult.class, key);
		if(CustomerListUtils.isEmpty(lstCategoryProduct)){
			lstCategoryProduct= baseProductService.selectProductByNameAndBrandId(product);
			CacheManager.getInstance().getCacher().setObject(key, lstCategoryProduct, 0);
		}
		else{
			//////System.out.println("get from cacher,key:"+key);
		}
		return lstCategoryProduct;
	}
	
	/***
	 * 根据品牌ID获取产品总记录数
	 * @param spaceCommon
	 * @return
	 */
	public static int getBrandProductCount(BaseProduct product){
		int total=0;
		PageParameter parameter= getPageParameter(product);
		String key=KeyGenerator.getTotalWithParameter(ModuleType.BaseProduct, parameter);
		String temp=CacheManager.getInstance().getCacher().get(key);
		if(StringUtils.isBlank(temp) || "0".equals(temp)){
			total=baseProductService.getBrandProductsCount(product);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
		} else{
			total=Integer.parseInt(temp);
			//////System.out.println("get from cacher,key:"+key);
		}
		return total;
	}

	/***
	 * 根据品牌ID获取产品列表
	 * @param plan
	 * @return
	 */
	public static List<CategoryProductResult> getBrandProductList(BaseProduct product){
		List<CategoryProductResult> lstCategoryProduct=Lists.newArrayList();
		PageParameter parameter= getPageParameter(product);
		String key=KeyGenerator.getPageQueryKeyParameter(ModuleType.BaseProduct, parameter);
		lstCategoryProduct=CacheManager.getInstance().getCacher().getList(CategoryProductResult.class, key);
		if(CustomerListUtils.isEmpty(lstCategoryProduct)){
			lstCategoryProduct= baseProductService.getBrandProductsList(product);
			CacheManager.getInstance().getCacher().setObject(key, lstCategoryProduct, 0);
		}
		else{
			//////System.out.println("get from cacher,key:"+key);
		}
		return lstCategoryProduct;
	}
 
	
	
    /***
     * 产品详情
     * @param map
     * @return id
     */
    @SuppressWarnings("rawtypes")
	public static Integer getDetailProduct(Map map){
    	Integer id=-1;
    	String key=KeyGenerator.getKeyWithMap(ModuleType.DesignProduct, map);
    	if(CacheManager.getInstance().getCacher()!=null){
    		String idString=CacheManager.getInstance().getCacher().get(key);
    		if(idString!=null&&!"".equals(idString)){
    			id=Integer.valueOf(idString);
    		}else{
    			id=baseProductService.getDetailProduct(map);
    			if(id>0){
    				CacheManager.getInstance().getCacher().set(key, String.valueOf(id), 0);
    			}
    		}
    	}
 		//////System.out.println("********WebBaseProductController-getDetailAttributeProductIdWeb-getDetailProduct-key"+key+"********");
    	return id;
    }
    
    /***
     * 得到当前产品的属性缓存
     * @param map
     * @return currentList
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List<ProductAttribute> getProductAttributeList(ProductAttribute currentPa){
    	Integer productId=currentPa.getProductId();
    	Map map=new HashMap<>();
    	map.put("productId", productId);
    	List<ProductAttribute> currentList = new ArrayList<ProductAttribute>(); 
    	String key=KeyGenerator.getKeyWithMap(ModuleType.ProductAttribute,map);
    	if(CacheManager.getInstance().getCacher()!=null){
    		currentList=CacheManager.getInstance().getCacher().getList(ProductAttribute.class, key);
    		if(CustomerListUtils.isEmpty(currentList)){
    			currentList=productAttributeService.getList(currentPa);
    			if(!CustomerListUtils.isEmpty(currentList)){
    				CacheManager.getInstance().getCacher().setObject(key, currentList, 0);
    			}
    		}
    	}
    	//////System.out.println("********WebBaseProductController-getDetailAttributeProductIdWeb-getProductAttributeList-key"+key+"********");
    	return currentList;
    }
    
    
    /***
     * 得到合并属性
     * @param map
     * @return newList
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List<ProductAttribute> getMergeAttribute(ProductAttribute newPa){
    	
    	List<Integer> pidList = new ArrayList<Integer>();
    	pidList=newPa.getProductIdList();
    	String attributeKey=newPa.getAttributeKey();
    	String attributeValueKey=newPa.getAttributeValueKey();
    	
    	Map map=new HashMap<>();
    	map.put("attributeKey", attributeKey);
    	map.put("attributeValueKey", attributeValueKey);
    	map.put("ProductIdList", pidList);
    	
    	List<ProductAttribute> newList = new ArrayList<ProductAttribute>(); 
    	String key=KeyGenerator.getKeyWithMap(ModuleType.ProductAttribute,map);
    	if(CacheManager.getInstance().getCacher()!=null){
    		newList=CacheManager.getInstance().getCacher().getList(ProductAttribute.class, key);
    		if(CustomerListUtils.isEmpty(newList)){
    			newList=productAttributeService.getMergeAttribute(newPa);
        		if(!CustomerListUtils.isEmpty(newList)){
    				CacheManager.getInstance().getCacher().setObject(key, newList, 0);
    			}
    		}
    	}
    	//////System.out.println("********WebBaseProductController-getDetailAttributeProductIdWeb-getMergeAttribute-key"+key+"********");
    	return newList;
    }
    
    
    
    /***
     * 通过id获得产品基础信息
     * @param map
     * @return BaseProduct
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static BaseProduct getBaseProductServiceById(int id){
    	BaseProduct baseProduct= new BaseProduct();
    	Map map=new HashMap<>();
    	map.put("id", id);
    	String key=KeyGenerator.getKeyWithMap(ModuleType.BaseProduct,map);
    	if(CacheManager.getInstance().getCacher()!=null){
    		baseProduct=(BaseProduct) CacheManager.getInstance().getCacher().getObject(key);
    		if(baseProduct==null){; 					
    			baseProduct=baseProductService.get(id);
        		if(baseProduct!=null){
    				CacheManager.getInstance().getCacher().setObject(key, baseProduct, 0);
    			}
    		}
    	}
    	//////System.out.println("********WebBaseProductController-getDetailAttributeProductIdWeb-getBaseProductServiceById-key"+key+"********");
    	return baseProduct;
    }
    
	/***
	 * 移除单个产品收藏的缓存
	 * 
	 * @param id
	 */
	public static void remove(int id) {
		String key = KeyGenerator.getIdKey(ModuleType.BaseProduct, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.BaseProduct, key);
	}

	public static BaseProduct getDataAndModel(BaseProduct baseProduct_) {
		BaseProduct pro = null;
		Map<String,String>map=new HashMap<String,String>();
		map.put("productId", baseProduct_.getId()+"");
		map.put("MediaType",baseProduct_.getMediaType());
		String key = KeyGenerator.getKeyWithMap(ModuleType.Product, map);
		if (CacheManager.getInstance().getCacher() != null) {
			pro = (BaseProduct) CacheManager.getInstance().getCacher().getObject(key);
			if (pro == null) {
				pro = baseProductService.getDataAndModel(baseProduct_);
				if (pro != null) {
					CacheManager.getInstance().getCacher().setObject(key, pro, 0);
				}
			}
		}
		return pro;
	}

	public static List<BaseProduct> getByPlanIdProductList(Integer planId,String productTypeValue,Integer productSmallTypeValue) {
		List<BaseProduct> proList = new ArrayList<>();
		Map<String,String>map=new HashMap<String,String>();
		map.put("planId", planId+"");
		map.put("productTypeValue",productTypeValue);
		map.put("productSmallTypeValue",productSmallTypeValue+"");
		String key = KeyGenerator.getKeyWithMap(ModuleType.Product, map);
		if (CacheManager.getInstance().getCacher() != null) {
			proList = (List<BaseProduct>) CacheManager.getInstance().getCacher().getObject(key);
			if (proList == null || proList.size() == 0) {
				proList = baseProductService.getByPlanIdProductList(planId,productTypeValue,productSmallTypeValue);
				if (proList != null && proList.size() > 0) {
					CacheManager.getInstance().getCacher().setObject(key, proList, 0);
				}
			}
		}
		return proList;
	}
	

	
}
