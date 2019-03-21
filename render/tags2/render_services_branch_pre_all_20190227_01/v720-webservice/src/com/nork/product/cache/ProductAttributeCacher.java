package com.nork.product.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.product.model.ProductAttribute;
import com.nork.product.service.ProductAttributeService;

/***
 * 产品目录缓存层
 * @author qiu.jun
 * @date 2016-05-19
 * 
 */
public class ProductAttributeCacher {
	
   private static ProductAttributeService productAttributeService = SpringContextHolder.getBean(ProductAttributeService.class);
   
	/***
	 * 根据条件获取所有产品目录
	 * 
	 * @param pc
	 * @return
	 */
	public static List<ProductAttribute> getProductAttributeList(ProductAttribute productAttribute) {
		List<ProductAttribute> lstproductAttribute = Lists.newArrayList();
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(productAttribute.getProductId() !=null){
			   map.put("productId", productAttribute.getProductId());
		}
		if(productAttribute.getIsDeleted() != null ){
			   map.put("isDeleted", productAttribute.getIsDeleted());
		}
		
		if(productAttribute.getAttributeValueId()!=null){
			   map.put("attributeValueId", productAttribute.getAttributeValueId());
		}
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.ProductAttribute, map);
		lstproductAttribute = CacheManager.getInstance().getCacher().getList(ProductAttribute.class, key);
		if (CustomerListUtils.isEmpty(lstproductAttribute)) {
			lstproductAttribute = productAttributeService.getList(productAttribute);
			CacheManager.getInstance().getCacher().setObject(key, lstproductAttribute, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstproductAttribute;
	}

}
