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
import com.nork.product.model.BaseBrand;
import com.nork.product.model.search.BaseBrandSearch;
import com.nork.product.service.BaseBrandService;

/***
 * 品牌缓存层
 * 
 * @author zhao.bing.lin
 * @date 2016-05-011
 */
public class BaseBrandCacher {

	private static BaseBrandService baseBrandService = SpringContextHolder.getBean(BaseBrandService.class);

	/***
	 * 获取单个品牌的信息
	 * 
	 * @param id
	 * @return BaseProduct
	 */
	public static BaseBrand get(int id) {
		BaseBrand brand = null;
		String key = KeyGenerator.getIdKey(ModuleType.BaseBrand, id);
		if (CacheManager.getInstance().getCacher() != null) {
			brand = (BaseBrand) CacheManager.getInstance().getCacher().getObject(key);
			if (brand == null) {
				brand = baseBrandService.get(id);
				if (brand != null) {
					CacheManager.getInstance().getCacher().setObject(key, brand, 0);
				}
			}
		}
		return brand;
	}
 
	
	/***
	 * 获取品牌总记录数
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int getBaseBrandTotal(BaseBrandSearch search) {
		Map map = new HashMap();
		map.put("count", "count");
		if (StringUtils.isNotBlank(search.getSch_BrandName_())) {
			map.put("brandName", search.getSch_BrandName_());
		}
		if (search.getBrandStyleId() != null) {
			map.put("brandStyleId", search.getBrandStyleId());
		}
		// map.put("start", start);
		// map.put("limit", limit);

		int total = 0;

		String key = KeyGenerator.getKeyWithMap(ModuleType.BaseBrand, map);
		if (CacheManager.getInstance().getCacher() != null) {
			String temp = CacheManager.getInstance().getCacher().get(key);
			if (StringUtils.isNoneBlank(temp)) {
				total = Integer.parseInt(temp);
			} else {
				total = baseBrandService.getCount(search);
				if (total > 0) {
					CacheManager.getInstance().getCacher().set(key, String.valueOf(total), 0);
				}
			}
		}
		//////System.out.println("********WebBaseBrandController-listAll-getBaseBrandTotal-key" + key + "********");
		return total;
	}

	/***
	 * 获取品牌数据？
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<BaseBrand> getPaginatedList(BaseBrandSearch search) {
		List<BaseBrand> listBrand = Lists.newArrayList();

		Map map = new HashMap();
		Integer limit = search.getLimit();
		Integer start = search.getStart();
		map.put("list", "page");
		map.put("start", start);
		map.put("limit", limit);
		if (StringUtils.isNotBlank(search.getSch_BrandName_())) {
			map.put("brandName", search.getSch_BrandName_());
		}
		if (search.getBrandStyleId() != null) {
			map.put("brandStyleId", search.getBrandStyleId());
		}
		String key = KeyGenerator.getKeyWithMap(ModuleType.BaseBrand, map);
		if (CacheManager.getInstance().getCacher() != null) {
			listBrand = CacheManager.getInstance().getCacher().getList(BaseBrand.class, key);
			if (CustomerListUtils.isEmpty(listBrand)) {
				listBrand = baseBrandService.getPaginatedList(search);
				if (!CustomerListUtils.isEmpty(listBrand)) {
					CacheManager.getInstance().getCacher().setObject(key, listBrand, 0);
				}
			}
		}
		//////System.out.println("********WebBaseBrandController-listAll-getPaginatedList-key" + key + "********");
		return listBrand;
	}
}
