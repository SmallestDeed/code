package com.nork.home.cache;

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
import com.nork.home.model.DesignProgramDiy;
import com.nork.home.model.DesignRecommendation;
import com.nork.home.model.search.DesignRecommendationSearch;
import com.nork.home.service.DesignRecommendationService;

/***
 * 设计方案推荐缓存层
 * 
 * @author Administrator
 *
 */
public class DesignRecommendationCacher {
	private static DesignRecommendationService designRecommendationService = SpringContextHolder
			.getBean(DesignRecommendationService.class);

	private static PageParameter getDiyParameter(DesignProgramDiy diy) {
		PageParameter parameter = new PageParameter();
		List<QueryParameter> lstParameter = Lists.newArrayList();
		QueryParameter qp = null;
		parameter.setPageIndex(diy.getStart());
		parameter.setPageSize(diy.getLimit());

		qp = new QueryParameter();
		qp.setName("houseTypeId");
		qp.setValue(String.valueOf(diy.getHouseTypeId()));
		lstParameter.add(qp);
		qp = new QueryParameter();
		qp.setName("designStyleId");
		qp.setValue(String.valueOf(diy.getDesignStyleId()));
		lstParameter.add(qp);

		parameter.setLstParameter(lstParameter);
		return parameter;
	}

	/***
	 * 获取设计方案推荐总记录数
	 * @return
	 */
	public static int getTotal() {
		int count = 0;
		String key = KeyGenerator.getAllCountKey(ModuleType.DesignRecommendation);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isNotBlank(temp)) {
			count = Integer.parseInt(temp);
		} else {
			DesignRecommendationSearch search = new DesignRecommendationSearch();
			count = designRecommendationService.getCount(search);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(count), 0);
		}
		return count;
	}

	/***
	 * 获取设计潮流总记录数
	 * 
	 * @param diy
	 * @return
	 */
	public static int getTotalDiy(DesignProgramDiy diy) {
		int count = 0;
		PageParameter parameter = getDiyParameter(diy);
		String key = KeyGenerator.getTotalWithParameter(ModuleType.DesignRecommendation, parameter);
		String temp = CacheManager.getInstance().getCacher().get(key);
		if (StringUtils.isNotBlank(temp)) {
			count = Integer.parseInt(temp);
		} else {
			DesignRecommendationSearch search = new DesignRecommendationSearch();
			count = designRecommendationService.getCount(search);
			CacheManager.getInstance().getCacher().set(key, String.valueOf(count), 0);
		}
		return count;
	}

	/***
	 * 获取设计潮流
	 * 
	 * @param search
	 * @return
	 */
	public static List<DesignProgramDiy> getDropDownList(DesignProgramDiy diy) {
		List<DesignProgramDiy> lst = Lists.newArrayList();
		PageParameter parameter = getDiyParameter(diy);
		String key = KeyGenerator.getPageQueryKeyParameter(ModuleType.DesignRecommendation, parameter);
		lst = CacheManager.getInstance().getCacher().getList(DesignProgramDiy.class, key);
		if (CustomerListUtils.isEmpty(lst)) {
			lst = designRecommendationService.getDropDownBoxList(diy);
			if (CustomerListUtils.isNotEmpty(lst)) {
				CacheManager.getInstance().getCacher().setObject(key, lst, 0);
			}
		}
		return lst;
	}

	/***
	 * 分页获取设计方案推荐
	 * 
	 * @param search
	 * @return
	 */
	public static List<DesignRecommendation> getPageList(DesignRecommendationSearch search) {
		List<DesignRecommendation> lst = Lists.newArrayList();
		Map<String,String> map=new HashMap<String,String>();
		map.put("limit", String.valueOf(search.getLimit()));
		map.put("start", String.valueOf(search.getStart()));
		String key = KeyGenerator.getKeyWithMap(ModuleType.DesignRecommendation, map);
		lst = CacheManager.getInstance().getCacher().getList(DesignRecommendation.class, key);
		if (CustomerListUtils.isEmpty(lst)) {
			lst = designRecommendationService.getPaginatedList(search);
			if (CustomerListUtils.isNotEmpty(lst)) {
				CacheManager.getInstance().getCacher().setObject(key, lst, 0);
			}
		}
		return lst;
	}

}
