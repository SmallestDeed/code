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
import com.nork.productprops.model.ProductProps;
import com.nork.productprops.service.ProductPropsService;

/***
 * 产品目录缓存层
 * @author qiu.jun
 * @date 2016-05-19
 * 
 */
public class ProductPropsCacher {
	
   private static ProductPropsService productPropsService = SpringContextHolder.getBean(ProductPropsService.class);
   
	public static ProductProps get(int id) {
		ProductProps productProps = null;
		String key = KeyGenerator.getIdKey(ModuleType.ProductProps, id);
		if (CacheManager.getInstance().getCacher() != null) {
			productProps = (ProductProps) CacheManager.getInstance().getCacher().getObject(key);
			if (productProps == null) {
				productProps = productPropsService.get(id);
				if (productProps != null) {
					CacheManager.getInstance().getCacher().setObject(key, productProps, 0);
				}
			}
		}
		return productProps;
	}

	public static List<ProductProps> getProductPropsMap(Integer productId) {
		List<ProductProps> lstproductProps = Lists.newArrayList();
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(productId !=null){
			   map.put("productId", productId);
		}
		
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.ProductProps, map);
		lstproductProps = CacheManager.getInstance().getCacher().getList(ProductProps.class, key);
		if (CustomerListUtils.isEmpty(lstproductProps)) {
			lstproductProps = productPropsService.getAttributeKeyAndValueByProductId(productId);
			CacheManager.getInstance().getCacher().setObject(key, lstproductProps, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		
		return lstproductProps;
	}
}
