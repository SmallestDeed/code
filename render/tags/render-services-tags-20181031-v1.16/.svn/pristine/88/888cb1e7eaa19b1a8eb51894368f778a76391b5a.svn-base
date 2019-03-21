package com.nork.product.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.product.model.ProCategory;
import com.nork.product.model.search.ProCategorySearch;
import com.nork.product.service.ProCategoryService;

/***
 * 产品目录缓存层
 * @author qiu.jun
 * @date 2016-05-19
 * 
 */
public class ProCategoryCacher {
   private static ProCategoryService proCategoryService=SpringContextHolder.getBean(ProCategoryService.class);
   
	/***
	 * 获取单个产品目录的详情
	 * 
	 * @param id
	 * @return
	 */
	public static ProCategory get(int id) {
		ProCategory proCategory = null;
		String key = KeyGenerator.getIdKey(ModuleType.ProCategory, id);
		if (CacheManager.getInstance().getCacher() != null) {
			proCategory = (ProCategory) CacheManager.getInstance().getCacher().getObject(key);
			if (proCategory == null) {
				proCategory = proCategoryService.get(id);
				if (proCategory != null) {
					CacheManager.getInstance().getCacher().setObject(key, proCategory, 0);
				}
			}
		}
		return proCategory;
	}
   
	/***
	 * 根据条件获取所有产品目录
	 * 
	 * @param pc
	 * @return
	 */
	public static List<ProCategory> getAllList(ProCategory pc) {
		List<ProCategory> lstCategory = Lists.newArrayList();
		Map<String, String> map = new HashMap<String, String>();
		if(pc.getPid()!=null){
			   map.put("pid", String.valueOf(pc.getPid()));
		}
		if(StringUtils.isNotBlank(pc.getLongCode())){
			   map.put("longCode", pc.getLongCode());
		}
		if(StringUtils.isNotBlank(pc.getState())){
		   map.put("state", pc.getState().trim());
		}
		if(StringUtils.isNotBlank(pc.getOrder())){
			   map.put("order", pc.getOrder().trim());
			}
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.ProCategory, map);
		lstCategory = CacheManager.getInstance().getCacher().getList(ProCategory.class, key);
		if (CustomerListUtils.isEmpty(lstCategory)) {
			lstCategory = proCategoryService.getList(pc);
			CacheManager.getInstance().getCacher().setObject(key, lstCategory, 0);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return lstCategory;
	}
	
	/***
	 * 获取所有产品目录
	 * 
	 * @param pc
	 * @return
	 */
	public static ProCategory getAllList() {
		String key = "_allCategory";
		ProCategory proCategory =(ProCategory)CacheManager.getInstance().getCacher().getObject(key);
		if (proCategory==null) {
			proCategory = proCategoryService.getAllCategory();
			CacheManager.getInstance().getCacher().setObject(key, proCategory, -1);
		} else {
			//////System.out.println("get from cacher,key:" + key);
		}
		return proCategory;
	}
	/**
	 * 刷新产品分类缓存
	 * @author huangsongbo
	 */
	public static void updateCache() {
		ProCategorySearch proCategorySearch=new ProCategorySearch();
		proCategorySearch.setLimit(-1);
		proCategorySearch.setStart(-1);
		proCategorySearch.setOrder(" ordering");
		List<ProCategory> list=proCategoryService.getList(proCategorySearch);
		Map<String, String> map = new HashMap<String, String>();
		map.put("order", proCategorySearch.getOrder().trim());
		String key = KeyGenerator.getAllListKeyWithMap(ModuleType.ProCategory, map);
		CacheManager.getInstance().getCacher().setObject(key, list, 0);
	}
	
}
