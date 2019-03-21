package com.nork.designconfig.service;

import java.util.List;
import java.util.Map;

import com.nork.designconfig.model.DesignRules;
import com.nork.designconfig.model.search.DesignRulesSearch;
import com.nork.product.model.BaseProduct;

/**   
 * @Title: DesignRulesService.java 
 * @Package com.nork.designconfig.service
 * @Description:设计配置-设计规则Service
 * @createAuthor pandajun 
 * @CreateDate 2016-03-23 19:56:47
 * @version V1.0   
 */
public interface DesignRulesService {
	/**
	 * 新增数据
	 *
	 * @param designRules
	 * @return  int 
	 */
	public int add(DesignRules designRules);

	/**
	 *    更新数据
	 *
	 * @param designRules
	 * @return  int 
	 */
	public int update(DesignRules designRules);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignRules 
	 */
	public DesignRules get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  designRules
	 * @return   List<DesignRules>
	 */
	public List<DesignRules> getList(DesignRules designRules);

	/**
	 *    获取数据数量
	 *
	 * @param  designRules
	 * @return   int
	 */
	public int getCount(DesignRulesSearch designRulesSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  designRules
	 * @return   List<DesignRules>
	 */
	public List<DesignRules> getPaginatedList(
				DesignRulesSearch designRulestSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 获取规则
	 * @param productIds
	 * @param productTypeCode
	 * @param productSmallTypeCode
	 * @param spaceCommonId
	 * @param designTempletId
	 * @param designRules
	 * @param request
	 * @return
	 */
	public Map<String,String> getRulesSecondaryList(String productIds,String productTypeCode,String productSmallTypeCode,
													Integer spaceCommonId,Integer designTempletId,DesignRules designRules,Map<String,String> productAttributeMap);
	
	/**
	 * 获取规则
	 * @param productIds
	 * @param productCode
	 * @param productTypeCode
	 * @param productSmallTypeCode
	 * @param spaceCommonId
	 * @param designTempletId
	 * @param designRules
	 * @param request
	 * @return
	 */
	public Map<String, String> getRulesSecondaryList(String productIds, String productCode, String productTypeCode, String productSmallTypeCode, 
			Integer spaceCommonId, Integer designTempletId, DesignRules designRules, 
			Map<String,String> productAttributeMap);

	public Map<String, String> getRulesSecondaryListV2(BaseProduct baseProduct,
			Integer spaceCommonId, Integer designTempletId,
			DesignRules designRules, Map<String, String> map);

	public Map<String, String> getRulesSecondaryListV3(BaseProduct baseProduct, Integer spaceCommonId,
			Integer designTempletId, DesignRules designRules, Map<String, String> map);
	
}
