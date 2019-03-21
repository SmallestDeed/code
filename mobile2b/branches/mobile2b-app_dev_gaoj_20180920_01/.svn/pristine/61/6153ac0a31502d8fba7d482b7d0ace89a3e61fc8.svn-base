package com.nork.product.cache;

import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.SpringContextHolder;
import com.nork.product.model.GroupProduct;
import com.nork.product.service.GroupProductService;

/**
 * 组合缓存层
 * @author huangsongbo
 *
 */
public class GroupProductCacher {

	private static GroupProductService groupProductService=SpringContextHolder.getBean(GroupProductService.class);
	
	public static GroupProduct get(Integer id){
		GroupProduct groupProduct=null;
		String key = KeyGenerator.getIdKey(ModuleType.GroupProduct, id);
		if(CacheManager.getInstance().getCacher()!=null){
			groupProduct=(GroupProduct) CacheManager.getInstance().getCacher().getObject(key);
			if(groupProduct==null){
				groupProduct=groupProductService.get(id);
				if(groupProduct!=null){
					CacheManager.getInstance().getCacher().setObject(key, groupProduct, 0);
				}
			}
		}
		return groupProduct;
	}
	

}
